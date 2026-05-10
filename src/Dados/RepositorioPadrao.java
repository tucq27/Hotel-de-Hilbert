package Dados;
import java.util.ArrayList;

/* 
    A classe RepositorioPadrao é uma classe abstrata que será herdada por todos os repositorios,
    pois todos tem um arraylist e os metodos adicionar, remover e buscar

    Sobre os diamantes <Tipo extends InterfaceIdentificavel>, Tipo expressa uma classe generica (pode ser
    Pessoa, Quarto, Reserva, etc) que precisa obrigatoriamente implementar a InterfaceIdentificael

    InterfaceIdentificavel é uma interface que tem o metodo getChave(), que retorna uma chave de
    identificaçao do objeto (como Cpf de uma pessoa, ou Id de um quarto). 

    Por fim, o trecho implements InterfaceRepositorio<Tipo> indica que a classe reositorioPadrao
    implementa a interface InterfaceRepositorio, que tem os metodos buscar, adicionar e remover.
*/ 
public abstract class RepositorioPadrao<Tipo extends InterfaceIdentificavel> implements InterfaceRepositorio<Tipo> {
    protected ArrayList<Tipo> objetos; // lista padrao

    // construtor
    public RepositorioPadrao() {
        this.objetos = new ArrayList<Tipo>(10);
    }

    @Override
    public Tipo buscar(String id) {
        for (Tipo objeto : objetos) {
            if (objeto.getChave().equals(id)) {
                return objeto;
            }
        }
        return null; // Retorna null se o objeto não for encontrado
    }

    @Override
    public void adicionar(Tipo objeto) {
        objetos.add(objeto);
    }
       
    @Override
    public void remover(Tipo objeto) {
        objetos.remove(objeto);
    }

}
