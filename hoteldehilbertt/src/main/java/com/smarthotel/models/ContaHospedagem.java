package com.smarthotel.models;

import java.util.ArrayList;

public class ContaHospedagem {
    private String id;
    private Responsavel responsavel;
    private double dividaTotal;
    private ArrayList<Recibo> recibos;

    public ContaHospedagem(String id, Responsavel responsavel){
        this.id= id;
        this.responsavel= responsavel;
        this.dividaTotal= 0;
        this.recibos= new ArrayList<Recibo>(10);
    }

    // Getters e Setters
    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
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
