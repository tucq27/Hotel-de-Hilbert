package com.smarthotel.negocios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.smarthotel.models.*;
import com.smarthotel.negocios.exceptions.*;
import com.smarthotel.dados.exceptions.ORException;

public interface IContHospedagens {
    void hospedarAgora(Quarto quarto, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) 
        throws QIException, CIFException, CIJRException, HPException, QLException, ORException;

    void reservarHospedagem(Quarto quarto, LocalDate dataEntrada, LocalDateTime horarioSaida, ContaHospedagem conta, ArrayList<Hospede> hospedes) 
        throws QIException, CIFException, CIJRException, HPException, QLException, ORException;

    void checkIn(Hospedagem hospedagem) throws QIException, CIFException, CIJRException;
    void checkOut(Hospedagem hospedagem) throws CINRException, COJRException;
    void cancelarReserva(Hospedagem hospedagem);
    void aumentarEstadia(Hospedagem hospedagem, int horas);
    void diminuirEstadia(Hospedagem hospedagem, int horas);
    void adicionarHospede(Hospedagem hospedagem, Hospede hospede) throws QLException;
    void removerHospede(Hospedagem hospedagem, Hospede hospede) throws HNEException;
    void trocarQuarto(Hospedagem hospedagem, Quarto novoQuarto) throws QIException, QLException;
    void trocarConta(Hospedagem hospedagem, ContaHospedagem novaConta);
    boolean verificarHospede(Hospedagem hospedagem, String nome, String cpf, LocalDate dataNascimento);

}
