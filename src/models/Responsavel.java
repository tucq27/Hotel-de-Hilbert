package models;

import java.time.LocalDate;

public class Responsavel extends Pessoa {
    private String dadosDePagamento;
    private double saldo=0; // valor total de custos que responsavel precisará pagar ao fim da estadia

    // Construtor
    public Responsavel(String nome, String cpf, LocalDate dataNascimento, String telefone, String email, String dadosDePagamento) {
        super(nome, cpf, dataNascimento, telefone, email);
        this.dadosDePagamento = dadosDePagamento;
    }

    // metodo para adicionar custos ao saldo de responsavel
    public void adcionarSaldo(double saldo) {
        this.saldo += saldo;
    }
    // metodo para subtrair custos do saldo de responsavel
    public void subtrairSaldo(double saldo) {
        this.saldo -= saldo;
    }

    // Getters
    public String getDadosDePagamento() {
        return dadosDePagamento;
    }
    public double getSaldo() {
        return saldo;
    }

    // Setters
    public void setDadosDePagamento(String dadosDePagamento) {
        this.dadosDePagamento = dadosDePagamento;
    }

}
