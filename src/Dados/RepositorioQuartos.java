package Dados;
import ClassesBasicas.Quarto;

public class RepositorioQuartos extends RepositorioPadrao<Quarto> {
    /* 
    O array list de objetos (generico) instanciado no repositorioPadrao é do tipo Quarto,
    então não precisamos criar um array list especifico para a classe Quarto
    */  

    // construtor
    public RepositorioQuartos() {
        super();
    }

    // metodos de atualizar
    public void atualizarAndar(String id, int novoAndar) {
        Quarto quarto = buscar(id);
        if (quarto != null) {
            quarto.setAndar(novoAndar);
        }
    }
    public void atualizarLimpeza(String id, boolean limpo) {
        Quarto quarto = buscar(id);
        if (quarto != null) {
            quarto.setLimpo(limpo);
        }
    }
    public void atualizarDisponibilidade(String id, boolean livre) {
        Quarto quarto = buscar(id);
        if (quarto != null) {
            quarto.setLivre(livre);
        }
    }
    public void atualizarMultTaxa(String id, double multTaxa) {
        Quarto quarto = buscar(id);
        if (quarto != null) {
            quarto.setMultTaxa(multTaxa);
        }
    }
    public void atualizarCapacidade(String id, int capacidade) {
        Quarto quarto = buscar(id);
        if (quarto != null) {
            quarto.setCapacidade(capacidade);
        }
    }
    // id é fixo, não atualizavel
    // frigobar é atualizado por meio de um repositorio proprio
}
