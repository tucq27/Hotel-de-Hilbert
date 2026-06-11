package com.smarthotel.negocios;

import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.dados.RepoItens;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Item;
import com.smarthotel.models.Recibo;

public class ControladorItens implements IContItens{

    private static ControladorItens instance;
    private static RepoItens repositorioItens;

    private ControladorItens() { }

    public static ControladorItens getInstance() {
        if (instance == null) {
            instance = new ControladorItens();
            repositorioItens = new RepoItens();
        }
        return instance;
    }

    private int gerarId() {
        int idAtual = Recibo.getDefinirId();
        int novoId = idAtual + 1;
        Recibo.setDefinirId(novoId);
        return idAtual;
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

        String novoId = String.valueOf(gerarId());
        item.setId(novoId);
        repositorioItens.adicionar(item);
    }

    public void removerItem(String id) throws ONEException {
        Item item = repositorioItens.buscar(id);
        if (item == null) {
            throw new ONEException("Item não encontrado.");
        }
        repositorioItens.remover(item);
    }
    
    public void atualizarItem(String id, String nome, LocalDate validade, double valor) throws ONEException {
        if (id != null) {
            Item item = buscarItem(id);

            if (nome != null) {
                item.setNome(nome);
            }
            if (validade != null) {
                item.setValidade(validade);
            }
            if (valor > 0) {
                item.setValor(valor);
            }
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
