package models;

import java.time.LocalDateTime;
//import java.time.LocalDate;
import java.time.Duration;
import java.util.ArrayList;

import dados.IIdentificavel;

// Classe responsável por representar uma hospedagem no hotel
public class Hospedagem implements IIdentificavel {

    // MIGRAR HOSPEDAGEM PARA RESERVA
    /* 
    
    // Armazena o horário em que a reserva foi feita
    private LocalDateTime horarioDaReserva;

    // Horários de entrada e saída
    private LocalDate inicio;
    private LocalDate fim;

    // void confirmarReserva() != checkIn()
    //  ( quarto != null && status == StatusQuarto.DISPONIVEL)

    boolean verificarHospede (String nome, String cpf, LocalDate dataNascimento) {}
    */
    private String id;
    private LocalDateTime horarioChegada; // Horários de entrada e saída
    private LocalDateTime horarioSaida;
    private Duration periodoDeEstadia; // Tempo total da hospedagem
    private Responsavel responsavel; 
    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // Construtor vazio usado por subclasses, como Reserva
    protected Hospedagem() {
        
    }

    // Construtor principal da hospedagem
    public Hospedagem(LocalDateTime horarioChegada,
                      Responsavel responsavel,
                      ArrayList<Hospede> hospedes,
                      Quarto quarto) {

        // Inicializa os dados da hospedagem
        this.horarioChegada = horarioChegada;
        this.responsavel = responsavel;
        this.hospedes = hospedes;
        this.quarto = quarto;

        // Ocupa o quarto ao iniciar hospedagem
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.OCUPADO);
        }
    }

    // Realiza o check-in
    public void checkIn() {

        // Impede check-in duplicado
        if (horarioChegada != null) {
            return;
        }

        // Registra horário atual de entrada
        horarioChegada = LocalDateTime.now();

        // Marca o quarto como ocupado
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.OCUPADO);
        }
    }

    // Realiza o check-out
    public void checkOut() {

        // Só permite check-out se houver check-in
        if (horarioChegada == null) {
            return;
        }

        // Impede múltiplos check-outs
        if (horarioSaida != null) {
            return;
        }

        // Registra horário de saída
        horarioSaida = LocalDateTime.now();

        // Calcula duração da hospedagem
        periodoDeEstadia =
            Duration.between(horarioChegada, horarioSaida);

        // Libera o quarto, mas ele fica sujo
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.SUJO);
        }
    }

    // Verifica se o check-in foi realizado
    public boolean verCheckIn() {
        return horarioChegada != null;
    }

    // Verifica se o check-out foi realizado
    public boolean verCheckOut() {
        return horarioSaida != null;
    }

    // Aumenta o tempo da estadia
    public void aumentarEstadia(long horas) {

        // Cria ou adiciona horas ao período
        if (periodoDeEstadia == null) {
            periodoDeEstadia = Duration.ofHours(horas);
        } else {
            periodoDeEstadia = periodoDeEstadia.plusHours(horas);
        }
    }

    // Diminui o tempo da estadia
    public void diminuirEstadia(long horas) {

        // Remove horas do período
        if (periodoDeEstadia != null) {
            periodoDeEstadia = periodoDeEstadia.minusHours(horas);
        }
    }

    // Retorna o ID da hospedagem
    public String getId() {
        return id;
    }

    // Retorna horário de chegada
    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }

    // Retorna horário de saída
    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    // Retorna duração da hospedagem
    public Duration getPeriodoDeEstadia() {
        return periodoDeEstadia;
    }

    // Retorna responsável pela hospedagem
    public Responsavel getResponsavel() {
        return responsavel;
    }

    // Retorna lista de hóspedes
    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }

    // Retorna quarto associado
    public Quarto getQuarto() {
        return quarto;
    }

    // Métodos protegidos para alteração interna dos dados
    protected void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    protected void setHospedes(ArrayList<Hospede> hospedes) {
        this.hospedes = hospedes;
    }

    protected void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    protected void setHorarioChegada(LocalDateTime horarioChegada) {
        this.horarioChegada = horarioChegada;
    }

    // Método exigido pela interface
    // Retorna a chave identificadora da hospedagem
    public String getChave() {
        return id;
    }
}