package ClassesBasicas;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva extends Hospedagem {
    private LocalDateTime horarioDaReserva;

    public Reserva(LocalDateTime horarioDaReserva,
                   Responsavel responsavel,
                   ArrayList<Hospede> hospedes,
                   Quarto quarto) {

        super(); 
        this.horarioDaReserva = horarioDaReserva;

        if (quarto == null || !quarto.isLivre()) {
            throw new IllegalStateException("Quarto indisponível para reserva!");
        }

        this.setResponsavel(responsavel);
        this.setHospedes(hospedes);
        this.setQuarto(quarto);
    }

    public boolean verificarHospede(String nome, String cpf, LocalDate dataNascimento) {
        if (getHospedes() == null) return false;

        for (Hospede h : getHospedes()) {
            if (h.getNome().equals(nome) &&
                h.getCpf().equals(cpf) &&
                h.getDataNascimento().equals(dataNascimento)) {
                return true;
            }
        }
        return false;
    }

    public void confirmarReserva() {
        if (getQuarto() == null) {
            throw new IllegalStateException("Reserva sem quarto!");
        }

        if (!getQuarto().isLivre()) {
            throw new IllegalStateException("Quarto ocupado!");
        }

        setHorarioChegada(LocalDateTime.now());

        getQuarto().setLivre(false);

        System.out.println("Reserva confirmada e check-in realizado!");
    }

    public LocalDateTime getHorarioDaReserva() {
        return horarioDaReserva;
    }

    public void setHorarioDaReserva(LocalDateTime horarioDaReserva) {
        this.horarioDaReserva = horarioDaReserva;
    }

    public String getChave() {
        return this.getId();
    }
}
