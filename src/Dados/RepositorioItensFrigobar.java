package Dados;

import ClassesBasicas.ItemFrigobar;
import java.util.ArrayList;

/*
    Repositorio que registra todos os itens de um frigobar especifico
    Não herda de RepositorioPadrao porque seu construtor precisa indicar a capacidade maxima do frigobar
*/
public class RepositorioItensFrigobar implements InterfaceRepositorio<ItemFrigobar> {
    private ArrayList<ItemFrigobar> inventario;
    private int capacidadeMaxima;

    // construtor
    public RepositorioItensFrigobar(int capacidadeMaxima) {
        this.inventario = new ArrayList<ItemFrigobar>(capacidadeMaxima);
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public ItemFrigobar buscar(String id) {
        for (ItemFrigobar item : inventario) {
            if (item.getChave().equals(id)) {
                return item;
            }
        }
        return null; // Retorna null se o item não for encontrado
    }

    @Override
    public void adicionar(ItemFrigobar item) {
        if (inventario.size() < capacidadeMaxima) {
            inventario.add(item);
        } else {
            // exceção: inventário cheio
        }
    }
       
    @Override
    public void remover(ItemFrigobar item) {
        ItemFrigobar itemExistente = buscar(item.getChave());
        if (itemExistente != null) {
            inventario.remove(itemExistente);
        } else {
            // exceção: item não encontrado
        }
        
    }

    @Override
    public void atualizar(String id, ItemFrigobar item) {
        ItemFrigobar itemExistente = buscar(id);
        if (itemExistente != null) {
            int indice = inventario.indexOf(itemExistente);
            inventario.set(indice, item);
        } else {
            // exceção: item não encontrado
        }
    }

}
