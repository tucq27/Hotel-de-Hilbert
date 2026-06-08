package com.smarthotel.dados;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.dados.exceptions.ONEException;

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
public abstract class Repositorio<Tipo extends IIdentificavel> implements IRepositorio<Tipo> {
    protected ArrayList<Tipo> objetos; // lista padrao
    // uma nova lista é criada para cada repositorio, com seu tipo especifico (Pessoa, Quarto, etc)

    // construtor
    public Repositorio() {
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
    public void adicionar(Tipo objeto) throws ORException {
        if (buscar(objeto.getChave()) != null) {
            throw new ORException(objeto); // objeto repetido no repositório
        }
        objetos.add(objeto);
    }
       
    @Override
    public void remover(Tipo objeto) throws ONEException {
        Tipo objetoExistente = buscar(objeto.getChave());
        if (objetoExistente != null) {
            objetos.remove(objetoExistente);
        } else {
            throw new ONEException("Objeto não encontrado");
        }
    }

    @Override
    public void atualizar(String id, Tipo objeto) throws ONEException {
        Tipo objetoExistente = buscar(id);
        
        if (objetoExistente != null) {
            objeto.setChave(id); // mantendo a chave antiga (cpf ou id) do objeto
            int indice = objetos.indexOf(objetoExistente);
            objetos.set(indice, objeto);
        } else {
            throw new ONEException("Objeto não encontrado");
        }
    }

    @Override
    public ArrayList<Tipo> getObjetos() {
        return objetos;
    }

}
