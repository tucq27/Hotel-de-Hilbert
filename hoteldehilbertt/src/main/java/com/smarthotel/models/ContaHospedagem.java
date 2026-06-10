package com.smarthotel.models;

import java.util.ArrayList;

public class ContaHospedagem {
    private String id;
    private Pessoa responsavel;
    private String dadosPagamento;
    private double dividaTotal;
    private ArrayList<Recibo> recibos;

    public ContaHospedagem(String id, Pessoa responsavel, String dados){
        this.id= id;
        this.responsavel= responsavel;
        this.dadosPagamento = dados;
        this.dividaTotal= 0;
        this.recibos= new ArrayList<Recibo>(10);
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

    public double getDividaTotal() {
        return dividaTotal;
    }

    public void setDividaTotal(double dividaTotal) {
        this.dividaTotal = dividaTotal;
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

    // metodos adicionais
    public double CalcularDivida(){
        return dividaTotal;
    }

}
