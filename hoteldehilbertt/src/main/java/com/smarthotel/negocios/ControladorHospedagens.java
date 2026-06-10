package com.smarthotel.negocios;

import com.smarthotel.dados.RepoHospedagens;
import com.smarthotel.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.smarthotel.negocios.exceptions.*;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

public class ControladorHospedagens implements IContHospedagens {

    private static ControladorHospedagens instance;
    private static RepoHospedagens repositorioHospedagens;

    private ControladorHospedagens() {

    }

    // o metodo getInstance() será usado no projeto no lugar do construtor da classe
    // isso garante que somente uma instancia (objeto) dessa classe será criada, e garante a existencia de
    // um único repositório de hospedagens em todo o projeto
    public static ControladorHospedagens getInstance(){
        if (instance == null) {
            instance = new ControladorHospedagens();
            repositorioHospedagens = new RepoHospedagens();
        }

        return instance;
    }

    private int gerarId() {
        int idAtual = Hospedagem.getDefinirId();
        int novoId = idAtual + 1;
        Hospedagem.setDefinirId(novoId);
        return idAtual;
    }

    public boolean hospedagmJaExiste(LocalDate dataSaida, Quarto quarto) {
        
        if (repositorioHospedagens.getObjetos() == null || repositorioHospedagens.getObjetos().isEmpty()) {
            return false;
        }

        for (Hospedagem hospedagem : repositorioHospedagens.getObjetos()) {
            if (hospedagem.getQuarto().equals(quarto) && hospedagem.getHorarioSaida().isEqual(dataSaida.atTime(12,0))) {
                return true;
            }
        }
        return false;
    }

    public Hospedagem buscarHospedagem(String id) throws ONEException {
        Hospedagem hospedagem = repositorioHospedagens.buscar(id);
        if (hospedagem == null) {
            throw new ONEException("Hospedagem de " + id + " não encontrada.");
        }
        return hospedagem;
    }

    public String hospedarAgora(Quarto quarto, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) 
      throws QIException, CIFException, CIJRException, HPException, QLException, ORException {
        
        if (!quartoEstaDisponivel(quarto)) {
            throw new QIException(quarto); // quarto não disponível
        }
        if (!quartoTemEspaco(quarto, hospedes)) {
            throw new QLException(quarto); // limite de hospedes para o quarto excedido
        }
        for (Hospede hospede : hospedes) {
            if (hospedeTemRestricao(hospede)) {
                throw new HPException(hospede); // um dos hospedes tem restrição para se hospedar
            }
        }
        Hospedagem hospedagem = new Hospedagem(quarto, horarioSaida, conta, hospedes);

        // definindo o id da hospedagem
        int idHosp = gerarId();
        hospedagem.setId(String.valueOf(idHosp));

        checkIn(hospedagem); // realiza o check-in imediato, registra o quarto como ocupado
        repositorioHospedagens.adicionar(hospedagem); // adicionano repositorio
        return hospedagem.getId();
    }

    public String reservarHospedagem(Quarto quarto, LocalDate dataEntrada, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) 
      throws QIException, CIFException, CIJRException, HPException, QLException, ORException {
        
        if (!quartoEstaDisponivel(quarto)) {
            throw new QIException(quarto); // quarto não disponível
        }
        if (!quartoTemEspaco(quarto, hospedes)) {
            throw new QLException(quarto); // limite de hospedes para o quarto excedido
        }
        for (Hospede hospede : hospedes) {
            if (hospedeTemRestricao(hospede)) {
                throw new HPException(hospede); // um dos hospedes tem restrição para se hospedar
            }
        }
        Hospedagem hospedagem = new Hospedagem(quarto, dataEntrada, horarioSaida, conta, hospedes);
        
        // definindo o id da hospedagem
        int idHosp = gerarId();
        hospedagem.setId(String.valueOf(idHosp));

        repositorioHospedagens.adicionar(hospedagem); // adicionano repositorio
        return hospedagem.getId();
    }

    public void checkIn(Hospedagem hospedagem) throws QIException, CIFException, CIJRException {
        // caso o checkin já tenha sido realizado, não permite novo check-in
        if (hospedagem.getHorarioCheckIn() != null) {
            throw new CIJRException();
        }
        // caso o quarto seja nulo ou não esteja disponível, não permite check-in
        if (!quartoEstaDisponivel(hospedagem.getQuarto())) {
            throw new QIException(hospedagem.getQuarto());
        }
        // Verifica se o check-in está sendo feito no dia previsto
        LocalDate hoje = LocalDate.now();
        if (!hoje.isEqual(hospedagem.getDataEntrada())) {
            throw new CIFException();
        }

        hospedagem.setHorarioCheckIn(LocalDateTime.now());
        hospedagem.setStatus(StatusHospedagem.ATIVA);
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

    // cancela a reserva, antes de se hospedar no hotel
    public void cancelarReserva(Hospedagem hospedagem) {
        if (!podeCancelarReserva(hospedagem)) {
            ///// Cobrar a multa de cancelamento
        }
        if (hospedagem.getQuarto() != null) {
            hospedagem.setStatus(StatusHospedagem.CANCELADA);

            // Limpa os dados da reserva
            hospedagem.setHorarioReserva(null);
            hospedagem.setDataEntrada(null);
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
    // só pode cancelar a reserva previa até meio dia do dia anterior à data prevista para entrada
    private boolean podeCancelarReserva(Hospedagem hospedagem) {
        if (hospedagem.getStatus() != StatusHospedagem.RESERVADA) {
            return false; // Não há reserva prévia para cancelar
        }
        if (hospedagem.getHorarioCheckIn() != null) {
            return false; // Check-in já realizado, não pode cancelar
        }

        LocalDateTime agora = LocalDateTime.now();
        if (hospedagem.getDataEntrada() != null) {
            if (agora.isBefore(hospedagem.getDataEntrada().atTime(12, 0).minusHours(24))) {
                return true;
            }
        }
        return false;
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

    public void adicionarHospede(Hospedagem hospedagem, Hospede hospede) throws QLException {
        if (hospede != null) {
            if (!quartoTemEspaco(hospedagem.getQuarto(), hospedagem.getHospedes())) {
                throw new QLException(hospedagem.getQuarto());
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

    public void trocarQuarto(Hospedagem hospedagem, Quarto novoQuarto) throws QIException, QLException {
        if (novoQuarto != null) {
            if (!quartoEstaDisponivel(novoQuarto)) {
                throw new QIException(novoQuarto);
            }
            if (!quartoTemEspaco(novoQuarto, hospedagem.getHospedes())) {
                throw new QLException(novoQuarto);
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

    public ArrayList<Hospedagem> verHospedagensAtivas() {
        ArrayList<Hospedagem> total = repositorioHospedagens.getObjetos();
        ArrayList<Hospedagem> ativas = new ArrayList<>();

        for (Hospedagem h : total) {
            if (h.getStatus() == StatusHospedagem.ATIVA) {
                ativas.add(h);
            }
        }
        return ativas;
    }

   public ArrayList<Hospedagem> verHospedagensReservadas() {
    ArrayList<Hospedagem> total = repositorioHospedagens.getObjetos();
    ArrayList<Hospedagem> reservadas = new ArrayList<>();

    for (Hospedagem h : total) {
        if (h.getStatus() == StatusHospedagem.RESERVADA) {
            reservadas.add(h);
        }
    }
    return reservadas;
}

public void removerHospedagem(String id) throws ONEException{
    Hospedagem hosp = repositorioHospedagens.buscar(id);
        if (hosp == null) {
            throw new ONEException("Hospedagem não encontrada.");
        }
        repositorioHospedagens.remover(hosp);
}

public RepoHospedagens getRepositorioHospedagens() {
    return repositorioHospedagens;
}
}
