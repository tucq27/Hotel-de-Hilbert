package com.smarthotel.negocios;

import java.util.ArrayList;

import com.smarthotel.dados.RepoItens;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Item;

public class ControladorItens {
    private static RepoItens repositorioItens;

    public ControladorItens() {
        if (repositorioItens == null) {
            repositorioItens = new RepoItens();
        }
    }

    public Item buscarItem(String id) throws ONEException {
        Item item = repositorioItens.buscar(id);
        if (item == null) {
            throw new ONEException("Item com id " + id + " não encontrado.");
        }
        return item;
    }

    public void adicionarItem(Item item) throws ORException {
        if (repositorioItens.buscar(item.getId()) != null) {
            throw new ORException(item); // Item repetida no repositório
        }
        repositorioItens.adicionar(item);
    }

    public void removerItem(String id) throws ONEException {
        Item item = repositorioItens.buscar(id);
        if (item == null) {
            throw new ONEException("Item não encontrado.");
        }
        repositorioItens.remover(item);
    }
    
    public void atualizarItem(String id, Item item) throws ONEException {
        if (id != null && item != null) {
            repositorioItens.atualizar(id, item);
        }
    }

    public ArrayList<Item> listarItensRegistrados() {
        return repositorioItens.getObjetos();
    }

    public void alterarValorItem(String id, double novoPreco) throws ONEException {
        Item item = buscarItem(id);

        if (novoPreco >= 0) {
            item.setValor(novoPreco);
        }
    }
}
