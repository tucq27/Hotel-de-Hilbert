package com.smarthotel.models;

public class QuartoSuite extends Quarto {

    public QuartoSuite(int numero, int andar, int capacidade) {
        super(numero, andar, capacidade);
        this.multTaxa = 2;
    }
}
