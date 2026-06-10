package com.smarthotel.negocios;

import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.dados.RepoPessoas;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Pessoa;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

public class ControladorPessoas implements IContPessoas {
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

    public void adicionarFuncionario(String cpf, String cargo) throws ONEException, ORException{
        Pessoa pessoa = buscarPessoa(cpf);

        if (pessoa instanceof Funcionario) {
            throw new ORException(pessoa);
        }

        Pessoa novoFuncionario = new Funcionario(pessoa, cargo);
        removerPessoa(cpf);
        adicionarPessoa(novoFuncionario);

    }

    public void removerPessoa(String cpf) throws ONEException {
        Pessoa pessoa = repositorioPessoas.buscar(cpf);
        if (pessoa == null) {
            throw new ONEException("Pessoa não encontrada.");
        }
        repositorioPessoas.remover(repositorioPessoas.buscar(cpf));
    }
    
    public void atualizarPessoa(String cpf, String nome, LocalDate data, String telefone, String email) throws ONEException {
        if (cpf != null) {
            Pessoa pessoa = buscarPessoa(cpf);

            if (nome != null) {
                pessoa.setNome(nome);
            }
            if (data != null) {
                pessoa.setDataNascimento(data);
            }
            if (telefone != null) {
                pessoa.setTelefone(telefone);
            }
            if (email != null) {
                pessoa.setEmail(email);
            }
        }
    }

    public ArrayList<Pessoa> listarPessoasRegistradas() {
        ArrayList<Pessoa> pessoasTotal = repositorioPessoas.getObjetos();
        ArrayList<Pessoa> pessoasRegistradas = new ArrayList<>();

        for (Pessoa p : pessoasTotal) {
            if (p.getClass() == Pessoa.class) {
                pessoasRegistradas.add(p);
            }
        }

        return pessoasRegistradas;
    }

    public ArrayList<Pessoa> listarFuncionarios() {
        ArrayList<Pessoa> pessoasTotal = repositorioPessoas.getObjetos();
        ArrayList<Pessoa> funcionarios = new ArrayList<>();

        for (Pessoa p : pessoasTotal) {
            if (p instanceof Funcionario) {
                funcionarios.add(p);
            }
        }

        return funcionarios;
    }
}
