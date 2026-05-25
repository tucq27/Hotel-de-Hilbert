package models;

import Dados.InterfaceIdentificavel;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Duration;
import java.util.ArrayList;

// Classe responsável por representar uma hospedagem no hotel
public class Hospedagem implements InterfaceIdentificavel {

    // MIGRAR HOSPEDAGEM PARA RESERVA

        // Armazena o horário em que a reserva foi feita
    //private LocalDateTime horarioDaReserva;

    private LocalDate horarioEntrada;
    private LocalDate horarioSaida;

    private LocalDateTime horarioCheckIn;
    private LocalDateTime horarioCheckOut;
    private String id;

    // Contador usado para gerar IDs automaticamente
    private static int definirId = 0;

    private Duration periodoDeEstadia;

    private Responsavel responsavel;
    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // método privado para apenas gerar ID único
    private gerarId() {
        definirId++;
        this.id = String.valueOf(definirId);
    }

    //Talvez seja importante jogar mais erros!
    public Hospedagem(Responsavel responsavel,
                      ArrayList<Hospede> hospedes,
                      Quarto quarto,
                      LocalDate horarioEntrada,
                      LocalDate horarioSaida) {


        if (quarto == null || !quarto.isLivre() || !quarto.isLimpo()) {
            throw new IllegalStateException("Quarto indisponível para reserva!");
        }

        if (hospedes.size() > quarto.capacidade()) {
            throw new IllegalStateException("Limite de hóspedes excedido para o quarto!");
        }

        gerarId();
        this.responsavel = responsavel;
        this.hospedes = hospedes;
        this.quarto = quarto;
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;

        quarto.setLivre(false);

    }

    public void checkIn() {

        // Impede check-in duplicado
        if (horarioCheckIn != null) {
            return;
        }

        // Registra horário atual de entrada
        horarioChegada = LocalDateTime.now();

        // Marca o quarto como ocupado
        if (quarto != null) {
            quarto.setLivre(false);
        }
    }

    public void checkOut() {

        // Só permite check-out se houver check-in
        if (horarioChegada == null) {
            return;
        }

        // Impede múltiplos check-outs
        if (horarioCheckOut != null) {
            return;
        }

        // Registra horário de saída
        horarioCheckOut = LocalDateTime.now();

        // Calcula duração da hospedagem
        periodoDeEstadia =
            Duration.between(horarioChegada, horarioCheckOut);

        // Libera o quarto
        if (quarto != null) {
            quarto.setLivre(true);
        }
    }

    public boolean verCheckIn() {
        return horarioChegada != null;
    }

    public boolean verCheckOut() {
        return horarioCheckOut != null;
    }

    public void aumentarEstadia(long horas) {

        // Cria ou adiciona horas ao período
        if (periodoDeEstadia == null) {
            periodoDeEstadia = Duration.ofHours(horas);
        } else {
            periodoDeEstadia = periodoDeEstadia.plusHours(horas);
        }
    }

    public void diminuirEstadia(long horas) {

        // Remove horas do período
        if (periodoDeEstadia != null) {
            periodoDeEstadia = periodoDeEstadia.minusHours(horas);
        }
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }

    public LocalDateTime getHorarioCheckOut() {
        return horarioCheckOut;
    }

    public Duration getPeriodoDeEstadia() {
        return periodoDeEstadia;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    // Métodos protegidos para alteração interna dos dados
    private void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    private void setHospedes(ArrayList<Hospede> hospedes) {
        this.hospedes = hospedes;
    }

    private void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    private void setHorarioCheckIn(LocalDateTime horarioCheckIn) {
        this.horarioCheckIn = horarioCheckIn;
    }

    // Método exigido pela interface
    // Retorna a chave identificadora da hospedagem
    public String getChave() {
        return id;
    }
}
