package com.smarthotel.negocios;

import com.smarthotel.dados.IRepositorio;
import com.smarthotel.dados.RepoHospedagens;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Quarto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import com.smarthotel.models.StatusQuarto;
import com.smarthotel.models.StatusHospedagem;
import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.RestricaoHospede;
import com.smarthotel.models.exceptions.*;
import java.util.ArrayList;

public class ControladorHospedagens {

    static private IRepositorio<Hospedagem> repositorioHospedagens;
    private ControladorQuartos controladorQuartos;
    //private ControladorPessoas controladorPessoas;
    static private double taxaTemporada = 1.0;

    public ControladorHospedagens(ControladorQuartos controladorQuartos) {

        if (repositorioHospedagens == null) {
            repositorioHospedagens = new RepoHospedagens();
        }
    }


    public void criarHospedagem(Hospedagem hospedagem) throws QIException, CIFException, CIJRException, PHException, LIMHException{

        if (!quartoEstaDisponivel(hospedagem.getQuarto())) {
            throw new QIException(hospedagem.getQuarto());
            }
        if (!quartoTemEspaco(hospedagem.getQuarto(), hospedagem.getHospedes())) {
            throw new LIMHException(hospedagem.getQuarto());
        }
        for (Hospede hospede : hospedagem.getHospedes()) {
            if (hospedeTemRestricao(hospede)) {
                throw new PHException(hospede);
            }
        }
        if (hospedagem.getStatus() == StatusHospedagem.ATIVA) {
            checkIn(hospedagem);
        }
        repositorioHospedagens.adicionar(hospedagem);
    }

    public void checkIn(Hospedagem hospedagem) throws QIException, CIFException, CIJRException {

        // caso o checkin já tenha sido realizado, não permite novo check-in
        if (hospedagem.getHorarioCheckIn() == null) {
            throw new CIJRException();
        }
        // caso o quarto seja nulo ou não esteja disponível, não permite check-in
        if (!quartoEstaDisponivel(hospedagem.getQuarto())) {
            throw new QIException(hospedagem.getQuarto());
        }
        // Verifica se o check-in está sendo feito no dia previsto
        LocalDate hoje = LocalDate.now();
        if (!hoje.isEqual(hospedagem.getHorarioEntrada())) {
            throw new CIFException();
        }

        hospedagem.setHorarioCheckIn(LocalDateTime.now());
        hospedagem.getQuarto().setStatus(StatusQuarto.OCUPADO);

    }

    public void checkOut(Hospedagem hospedagem) throws CINRException, COJRException {

        // Só permite check-out se houver check-in
        if (hospedagem.getHorarioCheckIn() == null) {
            throw new CINRException();
        }

        // Impede múltiplos check-outs
        if (hospedagem.getHorarioCheckOut() != null) {
            throw new COJRException();
        }

        // Registra horário de saída
        hospedagem.setHorarioCheckOut(LocalDateTime.now());
        hospedagem.setStatus(StatusHospedagem.ENCERRADA);

        // Libera o quarto, mas ele fica sujo
        if (hospedagem.getQuarto() != null) {
            hospedagem.getQuarto().setStatus(StatusQuarto.SUJO);
        }
    }

    // só pode cancelar a reserva previa até meio dia do dia anterior à data prevista para entrada
    private boolean podeCancelarReserva(Hospedagem hospedagem) {
        if (hospedagem.getStatus() != StatusHospedagem.RESERVADA) {
            return false; // Não há reserva prévia para cancelar
        }
        if (hospedagem.getHorarioCheckIn() != null) {
            return false; // Check-in já realizado, não pode cancelar
        }

        LocalDateTime agora = LocalDateTime.now();
        if (hospedagem.getHorarioEntrada() != null) {
            if (agora.isBefore(hospedagem.getHorarioEntrada().atTime(12, 0).minusHours(24))) {
                return true;
            }
        }
        return false;
    }

    // cancela a reserva
    public void cancelarReserva(Hospedagem hospedagem) {
        if (!podeCancelarReserva(hospedagem)) {
            ///// Cobrar a multa de cancelamento
        }
        // Libera o quarto, mas ele fica sujo
        if (hospedagem.getQuarto() != null) {
            hospedagem.getQuarto().setStatus(StatusQuarto.SUJO);
            hospedagem.setStatus(StatusHospedagem.CANCELADA);

            // Limpa os dados da reserva
            hospedagem.setHorarioReserva(null);
            hospedagem.setHorarioEntrada(null);
            hospedagem.setHorarioSaida(null);
        }
    }

    // métodos auxiliares para validação de regras de negócio
    private boolean quartoEstaDisponivel(Quarto quarto) {
        return ((quarto != null) && (quarto.getStatus() == StatusQuarto.DISPONIVEL));
    }

    private boolean quartoTemEspaco(Quarto quarto, ArrayList<Hospede> hospedes) {
        return (hospedes.size() < quarto.getCapacidade());
    }

    private boolean hospedeTemRestricao(Hospede hospede) {
        return (hospede.getRestricao() == RestricaoHospede.PROIBIDO);
    }


    // Aumenta o tempo da estadia
    public void aumentarEstadia(Hospedagem hospedagem, int horas) {
        if (hospedagem.getHorarioSaida() != null) {
            hospedagem.setHorarioSaida(hospedagem.getHorarioSaida().plusHours(horas));
        }
    }

    // Diminui o tempo da estadia
    public void diminuirEstadia(Hospedagem hospedagem, int horas) {
        if (hospedagem.getHorarioSaida() != null) {
            hospedagem.setHorarioSaida(hospedagem.getHorarioSaida().minusHours(horas));
        }
    }

    public void adicionarHospede(Hospedagem hospedagem, Hospede hospede) throws LIMHException {
        if (hospede != null) {
            if (!quartoTemEspaco(hospedagem.getQuarto(), hospedagem.getHospedes())) {
                throw new LIMHException(hospedagem.getQuarto());
            }
            hospedagem.getHospedes().add(hospede);
        }

    }

    public void removerHospede(Hospedagem hospedagem, Hospede hospede) throws HNEException {
        if (hospede != null) {
            if (!hospedagem.getHospedes().contains(hospede)) {
                throw new HNEException();
            }
            hospedagem.getHospedes().remove(hospede);
        }
    }

    public void trocarQuarto(Hospedagem hospedagem, Quarto novoQuarto) throws QIException, LIMHException {
        if (novoQuarto != null) {
            if (!quartoEstaDisponivel(novoQuarto)) {
                throw new QIException(novoQuarto);
            }
            if (!quartoTemEspaco(novoQuarto, hospedagem.getHospedes())) {
                throw new LIMHException(novoQuarto);
            }
            // Libera o quarto antigo, mas ele fica sujo
            if (hospedagem.getQuarto() != null) {
                hospedagem.getQuarto().setStatus(StatusQuarto.SUJO);
            }
            novoQuarto.setStatus(StatusQuarto.OCUPADO);
            hospedagem.setQuarto(novoQuarto);
        }
    }

    public void trocarConta(Hospedagem hospedagem, ContaHospedagem novaConta) {
        if (novaConta != null) {
            // passando divida e recibos da conta antiga para a nova conta
            novaConta.setDividaTotal(hospedagem.getConta().getDividaTotal());
            novaConta.setRecibos(hospedagem.getConta().getRecibos());
            // deletando as dividas da conta antiga
            hospedagem.getConta().setDividaTotal(0);
            hospedagem.getConta().setRecibos(null);

            hospedagem.setConta(novaConta);
        }
    }

    // verifica se hospede qualquer está nessa hospedagem
    public boolean verificarHospede(Hospedagem hospedagem, String nome, String cpf, LocalDate dataNascimento) {
        for (Hospede hospede : hospedagem.getHospedes()) {
            if (hospede.getNome().equals(nome) && hospede.getCpf().equals(cpf) && hospede.getDataNascimento().equals(dataNascimento)) {
                return true;
            }
        }
        return false;
    }


    private void setTaxaTemporada() {
        Month mesAtual = LocalDate.now().getMonth();
        if (mesAtual == Month.JANUARY ||
            mesAtual == Month.DECEMBER ||
            mesAtual == Month.JULY ||
            mesAtual == Month.JUNE) {
                ControladorHospedagens.taxaTemporada = 2.0;
                //Ainda temos que implementar realmente a taxa no pagamento
            }
    }
}
