package com.smarthotel.models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.models.exceptions.*;
import com.smarthotel.dados.IIdentificavel;

// Classe responsável por representar uma hospedagem no hotel
public class Hospedagem implements IIdentificavel {

    private String id;
    private static int definirId = 1; 
    private StatusHospedagem status; // status da hospedagem (reservada, ativa, encerrada, cancelada)
    private LocalDateTime horarioCheckIn; // Horários de entrada e saída do hospede no hotel
    private LocalDateTime horarioCheckOut;
    private LocalDateTime horarioReserva; // horario que a reserva foi feita (se for NULL, a hospedagem não tem rserva previa)
    private LocalDate horarioEntrada; // data prevista pra Entrada (reserva)
    private LocalDateTime horarioSaida; // hora prevista pra Saída (reserva)
    private ContaHospedagem conta; // associação com a conta de hospedagem
    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // Construtor: caso a hospedagem seja criada sem reserva previa
    public Hospedagem(Quarto quarto, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) throws QIException, PHException, LIMHException, CIFException, CIJRException {
        
        if (!quartoEstaDisponivel(quarto)) {
            throw new QIException(quarto);
        }
        if (!quartoTemEspaco(quarto, hospedes)) {
            throw new LIMHException(quarto);
        }
        for (Hospede hospede : hospedes) {
            if (hospedeTemRestricao(hospede)) {
                throw new PHException(hospede);
            }
        }
        this.status = StatusHospedagem.ATIVA;
        this.quarto = quarto;
        this.horarioEntrada = LocalDate.now(); // não há reserva prévia, então a entrada é a data atual
        this.horarioSaida = horarioSaida;
        this.conta = conta;
        this.hospedes = hospedes;
        checkIn(); // faz o check-in imediatamente, pois não há reserva prévia
        gerarId(); 
    }

    // Construtor: caso a hospedagem seja criada a partir de uma reserva previa
    public Hospedagem(Quarto quarto, LocalDate horarioEntrada, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) throws QIException, PHException, LIMHException{
        
        if (!quartoEstaDisponivel(quarto)) {
            throw new QIException(quarto);
        }
        if (!quartoTemEspaco(quarto, hospedes)) {
            throw new LIMHException(quarto);
        }
        for (Hospede hospede : hospedes) {
            if (hospedeTemRestricao(hospede)) {
                throw new PHException(hospede);
            }
        }
        this.status = StatusHospedagem.RESERVADA;
        this.quarto = quarto;
        this.horarioReserva = LocalDateTime.now();
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.conta = conta;
        this.hospedes = hospedes;
        gerarId();
    }

    ////// o metodo final deverá ser mais complexo (baixa prioridade)
    private void gerarId() {
        definirId++;
        this.id = String.valueOf(definirId);
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

    // só pode cancelar a reserva previa até meio dia do dia anterior à data prevista para entrada
    private boolean podeCancelarReserva() {
        if (status != StatusHospedagem.RESERVADA) {
            return false; // Não há reserva prévia para cancelar
        }
        if (horarioCheckIn != null) {
            return false; // Check-in já realizado, não pode cancelar
        }

        LocalDateTime agora = LocalDateTime.now();
        if (horarioEntrada != null) {
            if (agora.isBefore(horarioEntrada.atTime(12, 0).minusHours(24))) {
                return true;
            }
        }
        return false;
    }

    // Realiza o check-in após reserva previa
    public void checkIn() throws QIException, CIFException, CIJRException {

        // caso o checkin já tenha sido realizado, não permite novo check-in
        if (horarioCheckIn == null) {
            throw new CIJRException();
        }
        // caso o quarto seja nulo ou não esteja disponível, não permite check-in
        if (!quartoEstaDisponivel(quarto)) {
            throw new QIException(quarto);
        }
        // Verifica se o check-in está sendo feito no dia previsto
        LocalDate hoje = LocalDate.now();
        if (!hoje.isEqual(horarioEntrada)) {
            throw new CIFException();
        }
        
        horarioCheckIn = LocalDateTime.now();  
        quarto.setStatus(StatusQuarto.OCUPADO);
        
    }

    // Realiza o check-out
    public void checkOut() throws CINRException, COJRException {

        // Só permite check-out se houver check-in
        if (horarioCheckIn == null) {
            throw new CINRException();
        }

        // Impede múltiplos check-outs
        if (horarioCheckOut != null) {
            throw new COJRException();
        }

        // Registra horário de saída
        horarioCheckOut = LocalDateTime.now();
        this.status = StatusHospedagem.ENCERRADA;

        // Libera o quarto, mas ele fica sujo
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.SUJO);
        }
    }

    // Verifica se o check-in foi realizado
    public boolean verCheckIn() {
        return (horarioCheckIn != null);
    }

    // Verifica se o check-out foi realizado
    public boolean verCheckOut() {
        return (horarioCheckOut != null);
    }

    // cancela a reserva
    public void cancelarReserva() {
        if (!podeCancelarReserva()) {
            ///// Cobrar a multa de cancelamento
        }
        // Libera o quarto, mas ele fica sujo
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.SUJO);
            this.status = StatusHospedagem.CANCELADA;

            // Limpa os dados da reserva
            horarioReserva = null;
            horarioEntrada = null;
            horarioSaida = null;
        }
        
    }

    // Aumenta o tempo da estadia
    public void aumentarEstadia(int horas) {
        if (horarioSaida != null) {
            horarioSaida = horarioSaida.plusHours(horas);
        }
    }

    // Diminui o tempo da estadia
    public void diminuirEstadia(int horas) {
        if (horarioSaida != null) {
            horarioSaida = horarioSaida.minusHours(horas);
        }
    }

    public void adicionarHospede(Hospede hospede) throws LIMHException {
        if (hospede != null) {
            if (!quartoTemEspaco(quarto, hospedes)) {
                throw new LIMHException(quarto);
            }
            hospedes.add(hospede);
        }
       
    }

    public void removerHospede(Hospede hospede) throws HNEException {
        if (hospede != null) {
            if (!hospedes.contains(hospede)) {
                throw new HNEException();
            }
            hospedes.remove(hospede);
        }
    }

    public void trocarQuarto(Quarto novoQuarto) throws QIException, LIMHException {
        if (novoQuarto != null) {
            if (!quartoEstaDisponivel(novoQuarto)) {
                throw new QIException(novoQuarto);
            }
            if (!quartoTemEspaco(novoQuarto, hospedes)) {
                throw new LIMHException(novoQuarto);
            }
            // Libera o quarto antigo, mas ele fica sujo
            if (quarto != null) {
                quarto.setStatus(StatusQuarto.SUJO);
            }
            novoQuarto.setStatus(StatusQuarto.OCUPADO);
            this.quarto = novoQuarto;
        }
    }

    public void trocarConta(ContaHospedagem novaConta) {
        if (novaConta != null) {
            // passando divida e recibos da conta antiga para a nova conta
            novaConta.setDividaTotal(conta.getDividaTotal());
            novaConta.setRecibos(conta.getRecibos());
            // deletando as dividas da conta antiga
            conta.setDividaTotal(0);
            conta.setRecibos(null);

            this.conta = novaConta;
        }
    }

    // verifica se hospede qualquer está nessa hospedagem
    public boolean verificarHospede(String nome, String cpf, LocalDate dataNascimento) {
        for (Hospede hospede : hospedes) {
            if (hospede.getNome().equals(nome) && hospede.getCpf().equals(cpf) && hospede.getDataNascimento().equals(dataNascimento)) {
                return true;
            }
        }
        return false;
    }

    
    // Getters
    public String getId() {
        return id;
    }
    public LocalDateTime getHorarioCheckIn() {
        return horarioCheckIn;
    }
    public LocalDateTime getHorarioCheckOut() {
        return horarioCheckOut;
    }
    public LocalDateTime getHorarioReserva() {
        return horarioReserva;
    }
    public LocalDate getHorarioEntrada() {
        return horarioEntrada;
    }
    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }
    public ContaHospedagem getConta() {
        return conta;
    }
    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }
    public Quarto getQuarto() {
        return quarto;
    }
    public StatusHospedagem getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(StatusHospedagem status) {
        this.status = status;
    }

    public void setHorarioCheckIn(LocalDateTime horarioCheckIn) {
        this.horarioCheckIn = horarioCheckIn;
    }

    public void setHorarioCheckOut(LocalDateTime horarioCheckOut) {
        this.horarioCheckOut = horarioCheckOut;
    }

    public void setHorarioReserva(LocalDateTime horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public void setHorarioEntrada(LocalDate horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public void setConta(ContaHospedagem conta) {
        this.conta = conta;
    }

    public void setHospedes(ArrayList<Hospede> hospedes) {
        this.hospedes = hospedes;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    // Método exigido pela interface
    public String getChave() {
        return id;
    }
}