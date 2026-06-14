package com.smarthotel.models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.dados.IIdentificavel;

// Classe responsável por representar uma hospedagem no hotel
public class Hospedagem implements IIdentificavel {

    private String id;
    private static int definirId = 1; 
    private StatusHospedagem status; // status da hospedagem (reservada, ativa, encerrada, cancelada)
    
    private LocalDateTime horarioCheckIn; // Horários de entrada e saída do hospede no hotel
    private LocalDateTime horarioCheckOut;

    private LocalDateTime horarioReserva; // horario que a reserva foi feita (se for NULL, a hospedagem não tem rserva previa)
    private LocalDate dataEntrada; // data prevista pra Entrada (reserva)
    private LocalDateTime horarioSaida; // hora prevista pra Saída (reserva)

    private ContaHospedagem conta; // associação com a conta de hospedagem
    private boolean diariaPaga = false;

    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // Construtor: caso a hospedagem seja criada sem reserva previa
    public Hospedagem(Quarto quarto, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) {
        
        if (quarto != null && horarioSaida != null && conta != null && hospedes != null) {
            this.status = StatusHospedagem.ATIVA;
            this.quarto = quarto;
            this.dataEntrada = LocalDate.now(); // não há reserva prévia, então a entrada é a data atual
            this.horarioSaida = horarioSaida;
            this.conta = conta;
            this.hospedes = hospedes;
        }
    }

    // Construtor: caso a hospedagem seja criada a partir de uma reserva previa
    public Hospedagem(Quarto quarto, LocalDate dataEntrada, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) {
        this(quarto, horarioSaida, conta, hospedes); // reutiliza o construtor anterior
        this.dataEntrada = dataEntrada;
        this.horarioReserva = LocalDateTime.now();
        this.status = StatusHospedagem.RESERVADA;
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
    public LocalDate getDataEntrada() {
        return dataEntrada;
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
    public static int getDefinirId() {
        return definirId;
    }
    public boolean isDiariaPaga() {
        return diariaPaga;
    }

    // Setters
    public static void setDefinirId(int novo) {
        definirId = novo;
    }

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

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
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

    public void setDiariaPaga(boolean diariaPaga) {
        this.diariaPaga = diariaPaga;
    }
    // Método exigido pela interface
    public String getChave() {
        return id;
    }
    public void setChave(String id) {
        this.id = id;
    }
}
