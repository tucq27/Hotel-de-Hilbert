package models;

import java.time.LocalDate;

public class Responsavel extends Pessoa {
    private String dadosDePagamento;

    // Construtor
    public Responsavel(String nome, String cpf, LocalDate dataNascimento, String dadosDePagamento) {
        super(nome, cpf, dataNascimento);
        this.dadosDePagamento = dadosDePagamento;
    }
    public Responsavel(String nome, String cpf, LocalDate dataNascimento, String telefone, String email, String dadosDePagamento) {
        super(nome, cpf, dataNascimento, telefone, email);
        this.dadosDePagamento = dadosDePagamento;
    }

    public String getDadosDePagamento() {
        return dadosDePagamento;
    }
    public void setDadosDePagamento(String dadosDePagamento) {
        this.dadosDePagamento = dadosDePagamento;
    }

}
