package com.smarthotel.negocios;

import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Pessoa;
import com.smarthotel.models.RestricaoHospede;

public interface IContPessoas {
    Pessoa buscarPessoa(String cpf) throws ONEException;
    void adicionarPessoa(Pessoa pessoa) throws ORException;
    void removerPessoa(String cpf) throws ONEException;
    void atualizarPessoa(String cpf, String nome, LocalDate data, String telefone, String email) throws ONEException;

    void adicionarHospede(String cpf, String preferencias) throws ONEException, ORException;
    void adicionarFuncionario(String cpf, String cargo) throws ONEException, ORException;

    void atualizarHospede(String cpf, String pref, String historico, RestricaoHospede restricao) throws ONEException;
    void atualizarFuncionario(String cpf, String cargo, Boolean ocupado) throws ONEException;
    
    ArrayList<Pessoa> listarPessoasRegistradas();
    ArrayList<Pessoa> listarFuncionarios();
    ArrayList<Pessoa> listarHospedes();
}