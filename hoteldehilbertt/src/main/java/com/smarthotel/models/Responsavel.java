package com.smarthotel.models;

public class Responsavel extends Pessoa {
    private String dadosDePagamento;

    // Construtor
    public Responsavel(Pessoa p, String dadosDePagamento) {
        super( p.getNome(), p.getCpf(), p.getDataNascimento(), p.getTelefone(), p.getEmail());
        this.dadosDePagamento = dadosDePagamento;
    }

    public String getDadosDePagamento() {
        return dadosDePagamento;
    }
    public void setDadosDePagamento(String dadosDePagamento) {
        this.dadosDePagamento = dadosDePagamento;
    }

}
