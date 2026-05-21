package models;

public class QuartoSuite extends Quarto {

    public QuartoSuite(String id, int andar, int capacidade) {
        super(id, andar, capacidade);
        this.multTaxa = 2;
    }
}
