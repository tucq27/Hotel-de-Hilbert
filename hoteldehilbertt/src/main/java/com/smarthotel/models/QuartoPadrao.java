package com.smarthotel.models;

public class QuartoPadrao extends Quarto {

    public QuartoPadrao(int numero, int andar, int capacidade) {
        super(numero, andar, capacidade);
        this.multTaxa = 1;
    }
}
