package models;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import dados.IIdentificavel;

// Classe responsável por representar uma hospedagem no hotel
public class Hospedagem implements IIdentificavel {

    private String id;
    private LocalDateTime horarioChegada; // Horários de entrada e saída do hospede no hotel
    private LocalDateTime horarioSaida;
    private LocalDateTime horarioReserva; // horario que a reserva foi feita
    private LocalDate inicio; // data prevista pra inicio e fim da hospedagem (para Reservas)
    private LocalDateTime fim;
    private Duration periodoDeEstadia; // vale a pena manter?
    private Responsavel responsavel; 
    private ArrayList<Hospede> hospedes;
    private Quarto quarto;

    // Construtor: caso a hospedagem seja criada sem reserva previa
    public Hospedagem(Quarto quarto, LocalDateTime horarioChegada, LocalDateTime fim, Responsavel responsavel, ArrayList<Hospede> hospedes) {
        this.quarto = quarto;
        this.horarioChegada = horarioChegada;
        this.fim = fim;
        this.responsavel = responsavel;
        this.hospedes = hospedes;

        // Ocupa o quarto ao iniciar hospedagem
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.OCUPADO);
        }
    }

    // Construtor: caso a hospedagem seja criada a partir de uma reserva previa
    public Hospedagem(Quarto quarto, LocalDate inicio, LocalDateTime fim, Responsavel responsavel, ArrayList<Hospede> hospedes) {
        this.quarto = quarto;
        this.horarioReserva = LocalDateTime.now();
        this.inicio = inicio;
        this.fim = fim;
        this.responsavel = responsavel;
        this.hospedes = hospedes;
    }

    // Realiza o check-in após reserva previa
    public void checkIn() {

        // caso o checkin já tenha sido realizado, não permite novo check-in
        if (horarioChegada == null) {
            //// EXEÇÃO: check-IN já realizado
        }

        // caso o quarto seja nulo ou não esteja disponível, não permite check-in
        if (quarto == null || quarto.getStatus() != StatusQuarto.DISPONIVEL) {
            //// EXEÇÃO: quarto indisponível
        }

        // caso a reserva não tenha sido realizada, não permite check-in
        if (horarioReserva == null) {
            //// EXEÇÃO: reserva não realizada
        }
        else{
            LocalDate hoje = LocalDate.now();

            // Verifica se o check-in está sendo feito no dia previsto
            if (!hoje.isEqual(inicio)) {
                //// EXEÇÃO: check-IN fora do dia previsto
            }
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
            //// EXEÇÃO: check-IN não realizado
        }

        // Impede múltiplos check-outs
        if (horarioSaida != null) {
            //// EXEÇÃO: check-OUT já realizado
        }

        // Registra horário de saída
        horarioSaida = LocalDateTime.now();

        // Calcula duração da hospedagem
        periodoDeEstadia = Duration.between(horarioChegada, horarioSaida);

        // Libera o quarto, mas ele fica sujo
        if (quarto != null) {
            quarto.setStatus(StatusQuarto.SUJO);
        }
    }

    // Verifica se o check-in foi realizado
    public boolean verCheckIn() {
        return (horarioChegada != null);
    }

    // Verifica se o check-out foi realizado
    public boolean verCheckOut() {
        return (horarioSaida != null);
    }

    // Aumenta o tempo da estadia
    public void aumentarEstadia(int horas) {
        if (fim != null) {
            fim = fim.plusHours(horas);
        }
    }

    // Diminui o tempo da estadia
    public void diminuirEstadia(int horas) {
        if (fim != null) {
            fim = fim.minusHours(horas);
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

    // Getters e Setters
    public String getId() {
        return id;
    }
    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }
    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }
    public LocalDateTime getHorarioReserva() {
        return horarioReserva;
    }
    public LocalDate getInicio() {
        return inicio;
    }
    public LocalDateTime getFim() {
        return fim;
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

    public void setId(String id) {
        this.id = id;
    }
    public void setHorarioChegada(LocalDateTime horarioChegada) {
        this.horarioChegada = horarioChegada;
    }
    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
    public void setHorarioReserva(LocalDateTime horarioReserva) {
        this.horarioReserva = horarioReserva;
    }
    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }
    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }
    public void setPeriodoDeEstadia(Duration periodoDeEstadia) {
        this.periodoDeEstadia = periodoDeEstadia;
    }
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
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