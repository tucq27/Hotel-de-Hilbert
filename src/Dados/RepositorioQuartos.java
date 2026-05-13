package Dados;
import ClassesBasicas.Quarto;

public class RepositorioQuartos extends RepositorioPadrao<Quarto> {
    /* 
    O array list de objetos (generico) instanciado no repositorioPadrao é do tipo Quarto,
    então não precisamos criar um array list especifico para a classe Quarto
    O mesmo vale para todos os repositorios
    */  

    // construtor
    public RepositorioQuartos() {
        super();
    }

}
