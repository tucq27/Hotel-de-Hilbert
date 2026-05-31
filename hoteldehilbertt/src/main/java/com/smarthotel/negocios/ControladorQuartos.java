package com.smarthotel.negocios;

import com.smarthotel.models.Frigobar;
import com.smarthotel.models.ItemFrigobar;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.StatusQuarto;
import com.smarthotel.dados.RepoQuartos;
//import com.smarthotel.dados.RepoItens;

import com.smarthotel.negocios.exceptions.*;

import java.util.ArrayList;

// falta implementar: 
//  - recibo do frigobar (debitar da conta responsável pela hospedagem)
// - buscarItemRegistrado (verificar se o item está no sistema, depende do ControladorItens)

public class ControladorQuartos implements IContQuartos {

    static private RepoQuartos quartosHotel;

    public ControladorQuartos() {
        // Se o repositório já existir, não pode criar um novo
        if (quartosHotel == null) {
            quartosHotel = new RepoQuartos();
        }
    }

    // Métodos para gerenciar o repositório de quartos
    public Quarto buscarQuarto(String id) throws ONEException {
        Quarto quarto = quartosHotel.buscar(id);
        if (quarto == null) {
            throw new ONEException("Quarto não encontrado");
        }
        return quarto;
    }

    public void adicionarQuarto(Quarto quarto) throws ERException {
        if (quartosHotel.buscar(quarto.getId()) != null) {
            throw new ERException(quarto); // quarto repetido no repositório
        }
        quartosHotel.adicionar(quarto);
    }

    public void removerQuarto(String id) throws ONEException {
        Quarto quarto = quartosHotel.buscar(id);
        if (quarto == null) {
            throw new ONEException("Quarto não encontrado");
        }
        quartosHotel.remover(quartosHotel.buscar(id));
    }
    
    public void atualizarQuarto(String id, Quarto quarto) throws ONEException {
        if (id != null && quarto != null) {
            quartosHotel.atualizar(id, quarto);
        }
    }

    public boolean estaDisponivel(Quarto quarto) {
        if (quarto == null) {
            return false;
        }
        String id = quarto.getId();
        return (quartosHotel.buscar(id).getStatus() == StatusQuarto.DISPONIVEL);
    }

    public ArrayList<Quarto> listarQuartos() {
        return quartosHotel.getObjetos();
    }

    public ArrayList<Quarto> listarQuartosDisponiveis() {
        ArrayList<Quarto> quartosDisponiveis = new ArrayList<>();
        for (Quarto quarto : listarQuartos()) {
            if (estaDisponivel(quarto)) {
                quartosDisponiveis.add(quarto);
            }
        }
        return quartosDisponiveis;
    }

    public ItemFrigobar buscarItemFrigobar(String idQuarto, String idItem) throws ONEException {
        ItemFrigobar itemEncontrado = null;

        if (idQuarto != null && idItem != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            Frigobar frigobar = quarto.getFrigobar();

            if (frigobar == null) {
                throw new ONEException("Frigobar não encontrado no quarto");
            }

            ArrayList<ItemFrigobar> inventario = quarto.getFrigobar().getInventarioFrigobar();
            for (ItemFrigobar item : inventario) {
                if (item.getId().equals(idItem)) {
                    itemEncontrado = item;
                    break;
                }
            }
        
            if (itemEncontrado == null) {
                throw new ONEException("Item do frigobar não encontrado");
            }
            
        }
        return itemEncontrado;
    }

    public void pegarItemFrigobar(String idQuarto, String idItem) throws ONEException {
        if (idQuarto != null && idItem != null) {
            ItemFrigobar item = buscarItemFrigobar(idQuarto, idItem); // pode lançar ENEException se o item não for encontrado
            Quarto quarto = quartosHotel.buscar(idQuarto);
            Frigobar frigobar = quarto.getFrigobar();

            if (frigobar == null) {
                throw new ONEException("Frigobar não encontrado no quarto");
            }
            // caso o quarto realmente tenha um frigobar
            ArrayList<ItemFrigobar> inventario = quarto.getFrigobar().getInventarioFrigobar();
            inventario.remove(item);

            //////////// gera um recibo do tipo frigobar e adiciona na conta

        }
    }

    /* 
    public void adicionarItemFrigobar(String idQuarto, String idItem) throws ENEException {
        if (idQuarto != null && idItem != null && buscarItemRegistrado(idQuarto, idItem) != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            
            if (quarto.getFrigobar() != null) {
                ArrayList<ItemFrigobar> inventario = quarto.getFrigobar().getInventarioFrigobar();

                ItemFrigobar item = buscarItemFrigobar(idQuarto, idItem); // pode lançar ENEException se o item não for encontrado
                if (inventario.size() < quarto.getFrigobar().getCapacidadeMaxima()) {
                    inventario.add(item);
                }
            }
        }
    }*/

}

