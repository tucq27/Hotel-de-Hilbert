package ClassesBasicas;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

public class Hospedagem {

    private String id;
    private static int definirId = 0;

    private LocalDateTime horarioChegada;
    private LocalDateTime horarioSaida;
    private Duration periodoDeEstadia;

    private Responsavel responsavel;
    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // Construtor vazio (usado por Reserva)
    protected Hospedagem() {
        definirId++;
        this.id = String.valueOf(definirId);
    }

    // Construtor principal
    public Hospedagem(LocalDateTime horarioChegada,
                      Responsavel responsavel,
                      ArrayList<Hospede> hospedes,
                      Quarto quarto) {

        definirId++;
        this.id = String.valueOf(definirId);

        this.horarioChegada = horarioChegada;
        this.responsavel = responsavel;
        this.hospedes = hospedes;
        this.quarto = quarto;

        // ocupa o quarto ao iniciar hospedagem
        if (quarto != null) {
            quarto.setLivre(false);
        }
    }

    public void checkIn() {
        if (horarioChegada != null) {
            return;
        }

        horarioChegada = LocalDateTime.now();

        if (quarto != null) {
            quarto.setLivre(false);
        }
    }

    public void checkOut() {
        if (horarioChegada == null) {
            return;
        }

        if (horarioSaida != null) {
            return;
        }

        horarioSaida = LocalDateTime.now();

        periodoDeEstadia = Duration.between(horarioChegada, horarioSaida);

        if (quarto != null) {
            quarto.setLivre(true);
        }
    }

    // Getters e Setters
    public boolean verCheckIn() {
        return horarioChegada != null;
    }

    public boolean verCheckOut() {
        return horarioSaida != null;
    }

    public void aumentarEstadia(long horas) {
        if (periodoDeEstadia == null) {
            periodoDeEstadia = Duration.ofHours(horas);
        } else {
            periodoDeEstadia = periodoDeEstadia.plusHours(horas);
        }
    }

    public void diminuirEstadia(long horas) {
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

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
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
}
