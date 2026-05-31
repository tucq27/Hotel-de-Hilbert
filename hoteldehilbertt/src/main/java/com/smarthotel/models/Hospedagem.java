package com.smarthotel.models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import com.smarthotel.models.StatusHospedagem;
import com.smarthotel.models.StatusQuarto;

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
    public Hospedagem(Quarto quarto, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) throws QIException, PHException, LIMHException, CIFException, CIJRException, ENException {
        
        if (quarto == null) {throw new ENException();}
        if (horarioSaida == null) {throw new ENException();}
        if (conta == null) {throw new ENException();}
        if (hospedes == null) {throw new ENException();}

        this.status = StatusHospedagem.ATIVA;
        this.quarto = quarto;
        this.horarioEntrada = LocalDate.now(); // não há reserva prévia, então a entrada é a data atual
        this.horarioSaida = horarioSaida;
        this.conta = conta;
        this.hospedes = hospedes;
        gerarId(); 
    }

    // Construtor: caso a hospedagem seja criada a partir de uma reserva previa
    public Hospedagem(Quarto quarto, LocalDate horarioEntrada, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) throws QIException, PHException, LIMHException, ENException{
        
        if (quarto == null) {throw new ENException();}
        if (horarioEntrada == null) {throw new ENException();}
        if (horarioSaida == null) {throw new ENException();}
        if (conta == null) {throw new ENException();}
        if (hospedes == null) {throw new ENException();}

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
