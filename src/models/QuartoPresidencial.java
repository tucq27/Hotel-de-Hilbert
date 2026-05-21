package models;

public class QuartoPresidencial extends Quarto {

    public QuartoPresidencial(String id, int andar, int capacidade) {
        super(id, andar, capacidade);
        this.multTaxa = 5;
    }

}
