package com.smarthotel.models;

public class QuartoPadrao extends Quarto {

    public QuartoPadrao(String id, int andar, int capacidade) {
        super(id, andar, capacidade);
        this.multTaxa = 1;
    }
}
