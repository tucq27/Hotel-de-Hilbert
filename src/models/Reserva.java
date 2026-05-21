package models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

// Classe Reserva herda características da classe Hospedagem
public class Reserva extends Hospedagem {

    // Armazena o horário em que a reserva foi feita
    private LocalDateTime horarioDaReserva;

    // Construtor da reserva
    public Reserva(LocalDateTime horarioDaReserva,
                   Responsavel responsavel,
                   ArrayList<Hospede> hospedes,
                   Quarto quarto) {

        // Chama o construtor vazio da classe Hospedagem
        super();

        // Define o horário da reserva
        this.horarioDaReserva = horarioDaReserva;

        // Verifica se o quarto existe e está disponível
        if (quarto == null || !quarto.isLivre()) {
            throw new IllegalStateException("Quarto indisponível para reserva!");
        }

        // Define os dados da reserva
        this.setResponsavel(responsavel);
        this.setHospedes(hospedes);
        this.setQuarto(quarto);
    }

    // Verifica se um hóspede específico está na reserva
    public boolean verificarHospede(String nome, String cpf, LocalDate dataNascimento) {

        // Se não houver hóspedes cadastrados, retorna false
        if (getHospedes() == null) return false;

        // Percorre a lista de hóspedes
        for (Hospede h : getHospedes()) {

            // Compara nome, CPF e data de nascimento
            if (h.getNome().equals(nome) &&
                h.getCpf().equals(cpf) &&
                h.getDataNascimento().equals(dataNascimento)) {

                // Retorna true se encontrar o hóspede
                return true;
            }
        }

        // Retorna false caso não encontre
        return false;
    }

    // Confirma a reserva e realiza o check-in
    public void confirmarReserva() {

        // Verifica se existe quarto associado
        if (getQuarto() == null) {
            throw new IllegalStateException("Reserva sem quarto!");
        }

        // Verifica se o quarto ainda está livre
        if (!getQuarto().isLivre()) {
            throw new IllegalStateException("Quarto ocupado!");
        }

        // Define o horário atual como horário de chegada
        setHorarioChegada(LocalDateTime.now());

        // Marca o quarto como ocupado
        getQuarto().setLivre(false);

        // Mensagem de confirmação
        System.out.println("Reserva confirmada e check-in realizado!");
    }

    // Retorna o horário da reserva
    public LocalDateTime getHorarioDaReserva() {
        return horarioDaReserva;
    }

    // Altera o horário da reserva
    public void setHorarioDaReserva(LocalDateTime horarioDaReserva) {
        this.horarioDaReserva = horarioDaReserva;
    }

    // Retorna a chave identificadora da reserva
    public String getChave() {
        return this.getId();
    }
}
