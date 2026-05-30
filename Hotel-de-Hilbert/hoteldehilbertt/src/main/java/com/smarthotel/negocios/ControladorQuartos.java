package com.smarthotel.negocios;
/*import com.smarthotel.dados.IRepositorio;
import com.smarthotel.dados.Repositorio;
import com.smarthotel.models.Item;
import com.smarthotel.models.Quarto;
import com.smarthotel.dados.RepoQuartos;
import com.smarthotel.dados.RepoItens;

import com.smarthotel.negocios.exceptions.*;

import main.java.com.smarthotel.negocios.exceptions.ENEException;

import java.util.ArrayList;

// falta implementar: 
//  - recibo do frigobar (debitar da conta responsável pela hospedagem)
// - buscarItemRegistrado (verificar se o item está no sistema, depende do ControladorItens)

public class ControladorQuartos {

    static private RepoQuartos quartosHotel;

    public ControladorQuartos() {
        // Se o repositório já existir, não pode criar um novo
        if (quartosHotel == null) {
            quartosHotel = new RepoQuartos();
        }
    }

    // Métodos para gerenciar o repositório de quartos
    public Quarto buscarQuarto(String id) throws ENEException {
        Quarto quarto = quartosHotel.buscar(id);
        if (quarto == null) {
            throw new ENEException(); // exceção de quarto não encontrado
        }
        return quarto;
    }

    public void adicionarQuarto(Quarto quarto) throws ERException {
        if (quartosHotel.buscar(quarto.getId()) != null) {
            throw new ERException(quarto); // exceção de quarto já existente
        }
        quartosHotel.adicionar(quarto);
    }

    public void removerQuarto(String id) throws ENEException {
        if (quartosHotel.buscar(id) == null) {
            throw new ENEException(); // exceção de quarto não encontrado
        }
        quartosHotel.remover(id);
    }
    
    public void atualizarQuarto(Quarto quarto) throws ENEException {
        if (quartosHotel.buscar(quarto.getId()) == null) {
            throw new ENEException(); // exceção de quarto não encontrado
        }
        quartosHotel.atualizar(quarto);
    }

    public boolean estaDisponivel(String id) throws ENEException {
        return (quartosHotel.buscar(id).getStatus() == StatusQuarto.DISPONIVEL);
    }

    public ArrayList<Quarto> listarQuartos() {
        return quartosHotel.getObjetos();
    }

    public ArrayList<Quarto> listarQuartosDisponiveis() {
        ArrayList<Quarto> quartosDisponiveis = new ArrayList<>();
        for (Quarto quarto : listarQuartos()) {
            if (quarto.estaDisponivel()) {
                quartosDisponiveis.add(quarto);
            }
        }
        return quartosDisponiveis;
    }

    public Item buscarItemFrigobar(String idQuarto, String idItem) throws ENEException {
        if (idQuarto != null && idItem != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            
            if (quarto.getFrigobar() != null) {
                ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();

                for (Item item : inventario) {
                    if (item.getId().equals(idItem)) {
                        return item; 
                    }
                }
            }
        }
        throw new ENEException(); // exceção de item não encontrado
    }

    public void pegarItemFrigobar(String idQuarto, String idItem) throws ENEException {
        if (idQuarto != null && idItem != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            
            Item item = buscarItemFrigobar(idQuarto, idItem); // pode lançar ENEException se o item não for encontrado
            
            // gera um recibo frigobar e adiciona
            
            ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();
            inventario.remove(item); // remove o item do inventário do frigobar
        }
    }

    public void adicionarItemFrigobar(String idQuarto, String idItem) throws ENEException {
        if (idQuarto != null && idItem != null && buscarItemRegistrado(idQuarto, idItem) != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            
            if (quarto.getFrigobar() != null) {
                ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();

                Item item = buscarItemRegistrado(idQuarto, idItem); // pode lançar ENEException se o item não for encontrado
                if (inventario.size() < quarto.getFrigobar().getCapacidadeMaxima()) {
                    inventario.add(item);
                }
            }
        }
    }

}
*/
