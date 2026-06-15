package com.smarthotel.models;

import java.util.ArrayList;

public class ContaHospedagem {
    private String id;
    private Pessoa responsavel;
    private String dadosPagamento;
    private double saldoPago = 0;
    private double saldoPendente;
    private ArrayList<Recibo> recibos;

    public ContaHospedagem(String id, Pessoa responsavel, String dadosPagamento) {
        this.id = id;
        this.responsavel = responsavel;
        this.dadosPagamento = dadosPagamento;
        this.saldoPendente = 0;
        this.recibos = new ArrayList<Recibo>(10);
    }

    // Getters e Setters
    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public String getDadosPagamento() {
        return dadosPagamento;
    }

    public void setDadosPagamento(String dadosPagamento) {
        this.dadosPagamento = dadosPagamento;
    }

    public double getSaldoPago() {
        return saldoPago;
    }

    public void setSaldoPago(double saldoPago) {
        this.saldoPago = saldoPago;
    }

    public double getSaldoPendente() {
        return saldoPendente;
    }

    public void setSaldoPendente(double saldoPendente) {
        this.saldoPendente = saldoPendente;
    }

    public ArrayList<Recibo> getRecibos() {
        return recibos;
    }

    public void setRecibos(ArrayList<Recibo> recibos) {
        this.recibos = recibos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // MÉTODOS ADICIONADOS PARA COMPATIBILIDADE

    public double getDividaTotal() {
        return saldoPendente;
    }

    public void setDividaTotal(double dividaTotal) {
        this.saldoPendente = dividaTotal;
    }
}