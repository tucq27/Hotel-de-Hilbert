package com.smarthotel.negocios;

import com.smarthotel.dados.RepoPessoas;
import com.smarthotel.models.Pessoa;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

public class ControladorPessoas {
    static private RepoPessoas repositorioPessoas;

    public ControladorPessoas() {
        if (repositorioPessoas == null) {
            repositorioPessoas = new RepoPessoas();
        }
    }
    
    // Métodos para gerenciar o repositório de Pessoas
    public Pessoa buscarPessoa(String cpf) throws ONEException {
        Pessoa pessoa = repositorioPessoas.buscar(cpf);
        if (pessoa == null) {
            throw new ONEException("Pessoa com CPF " + cpf + " não encontrada.");
        }
        return pessoa;
    }

    public void adicionarPessoa(Pessoa pessoa) throws ORException {
        if (repositorioPessoas.buscar(pessoa.getCpf()) != null) {
            throw new ORException(pessoa); // pessoa repetida no repositório
        }
        repositorioPessoas.adicionar(pessoa);
    }

    public void removerPessoa(String cpf) throws ONEException {
        Pessoa pessoa = repositorioPessoas.buscar(cpf);
        if (pessoa == null) {
            throw new ONEException("Pessoa não encontrada.");
        }
        repositorioPessoas.remover(repositorioPessoas.buscar(cpf));
    }
    
    public void atualizarPessoa(String cpf, Pessoa Pessoa) throws ONEException {
        if (cpf != null && Pessoa != null) {
            repositorioPessoas.atualizar(cpf, Pessoa);
        }
    }
}
