package com.smarthotel.negocios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.smarthotel.dados.RepoHospedagens;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.StatusHospedagem;

public class ControladorRelatorios implements IContRelatorios {

    private GeradorPDF geradorPDF = new GeradorPDF();

    public ControladorRelatorios() {}

    // verifica todas as hospedagens ativas agora
    public ArrayList<Hospedagem> gerarOcupacaoDiaria(RepoHospedagens repositorioHospedagens) {

        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> ocupacao = new ArrayList<>();

        LocalDate hoje = LocalDate.now();

        for (Hospedagem hosp : hospedagensHotel) {

            LocalDateTime checkout = hosp.getHorarioCheckOut();

            if ((hosp.getStatus() == StatusHospedagem.ATIVA)
                    || ((checkout != null)
                    && (checkout.toLocalDate().isEqual(hoje)))) {

                ocupacao.add(hosp);
            }
        }

        return ocupacao;
    }

    public ArrayList<Hospedagem> gerarOcupacaoMensal(RepoHospedagens repositorioHospedagens) {

        ArrayList<Hospedagem> hospedagensHotel = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> ocupacao = new ArrayList<>();

        LocalDate ultimoMes = LocalDate.now().minusMonths(1);
        int diasUltimoMes = ultimoMes.lengthOfMonth();

        LocalDateTime inicioMes = ultimoMes.withDayOfMonth(1).atTime(0, 0, 0);
        LocalDateTime fimMes = ultimoMes.withDayOfMonth(diasUltimoMes).atTime(23, 59, 59);

        for (Hospedagem hosp : hospedagensHotel) {

            LocalDateTime checkout = hosp.getHorarioCheckOut();
            LocalDateTime checkin = hosp.getHorarioCheckIn();

            if (checkout == null) {
                checkout = LocalDateTime.now();
            }

            if (checkin != null && checkout != null) {

                if (!(checkin.isAfter(fimMes))
                        && !(checkout.isBefore(inicioMes))) {

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

            if (agora.isAfter(horarioSaida) && checkout == null) {
                hospPendente.add(hosp);
            }
        }

        return hospPendente;
    }

    // EXPORTA O RELATÓRIO DE SAÍDAS PARA PDF
    public void exportarRelatorioSaidasPDF(RepoHospedagens repositorioHospedagens) throws Exception {

        ArrayList<Hospedagem> saidas =
                gerarRelatorioSaidas(repositorioHospedagens);

        geradorPDF.gerarRelatorioSaidasPDF(saidas);
    }
}