package com.smarthotel.models;

public class QuartoPresidencial extends Quarto {

    public QuartoPresidencial(int numero, int andar, int capacidade) {
        super(numero, andar, capacidade);
        this.multTaxa = 5;
    }

}
