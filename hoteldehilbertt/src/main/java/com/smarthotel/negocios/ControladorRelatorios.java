package com.smarthotel.negocios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.smarthotel.dados.RepoHospedagens;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.StatusHospedagem;

public class ControladorRelatorios implements IContRelatorios{

    public ControladorRelatorios() {
        
    }

    // verifica todas as hospedagens ativas agora
    public ArrayList<Hospedagem> gerarOcupacaoDiaria(RepoHospedagens repositorioHospedagens) {
        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> ocupacao = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (Hospedagem hosp : hospedagensHotel) {
            LocalDateTime checkout = hosp.getHorarioCheckOut();

            // verifica se a hospedagem esta ativa ou se o checkout ocorreu hoje
            if ( (hosp.getStatus() == StatusHospedagem.ATIVA) || ((checkout != null) && (checkout.toLocalDate().isEqual(hoje)) ) ) {
                ocupacao.add(hosp);
            }
        }

        return ocupacao;
    }

    public ArrayList<Hospedagem> gerarOcupacaoMensal(RepoHospedagens repositorioHospedagens) {
        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> ocupacao = new ArrayList<>();

        LocalDate ultimoMes = LocalDate.now().minusMonths(1); // data atual -1 mes
        int diasUltimoMes = ultimoMes.lengthOfMonth();

        LocalDateTime inicioMes = ultimoMes.withDayOfMonth(1).atTime(0, 0, 0);
        LocalDateTime fimMes = ultimoMes.withDayOfMonth(diasUltimoMes).atTime(23, 59, 59);
        

        for (Hospedagem hosp : hospedagensHotel) {
            LocalDateTime checkout = hosp.getHorarioCheckOut();
            LocalDateTime checkin = hosp.getHorarioCheckIn();

            if (checkout == null) {
                checkout = LocalDateTime.now();
            }

            // caso exista chekin, o quarto está sendo ocupado
            if (checkin != null && checkout != null) {

                // checkin feito até o fim do mes
                // checkout feito a partir do inicio do mes
                if ( !(checkin.isAfter(fimMes)) && !(checkout.isBefore(inicioMes)) ) {
                    ocupacao.add(hosp);
                }
            }
            
            
        }

        return ocupacao;
    }

    public ArrayList<Hospedagem> gerarRelatorioSaidas(RepoHospedagens repositorioHospedagens) {
        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> hospEncerradas = new ArrayList<>();

        for (Hospedagem hosp : hospedagensHotel) {
            if (hosp.getHorarioCheckOut() != null) {
                hospEncerradas.add(hosp);
            }
        }

        return hospEncerradas;
    }

    public ArrayList<Hospedagem> alertarSaidaPendente(RepoHospedagens repositorioHospedagens) {
        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> hospPendente = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();

        for (Hospedagem hosp : hospedagensHotel) {
            LocalDateTime horarioSaida = hosp.getHorarioSaida();
            LocalDateTime checkout = hosp.getHorarioCheckOut();

            // se já passou do horario de saida e o chekout não foi feito, adiciona na lista
            if (agora.isAfter(horarioSaida) && checkout == null) {
                hospPendente.add(hosp);
            }
        }

        return hospPendente;
    }
}
