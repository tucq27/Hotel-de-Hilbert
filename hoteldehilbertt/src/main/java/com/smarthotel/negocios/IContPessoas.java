package com.smarthotel.negocios;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Pessoa;

public interface IContPessoas {
    Pessoa buscarPessoa(String cpf) throws ONEException;
    void adicionarPessoa(Pessoa pessoa) throws ORException;
    void removerPessoa(String cpf) throws ONEException;
    void atualizarPessoa(String cpf, Pessoa Pessoa) throws ONEException;

    ArrayList<Pessoa> listarPessoasRegistradas();
    ArrayList<Pessoa> listarHospedes();
    ArrayList<Pessoa> listarResponsaveis();
    ArrayList<Pessoa> listarFuncionarios();
}