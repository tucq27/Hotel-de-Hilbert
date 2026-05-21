package models;
import dados.RepositorioItensFrigobar;

//Temos que lembrar de criar uma função pra reconhecer quando um item foi comprado
public class Frigobar {
    private RepositorioItensFrigobar inventarioFrigobar;
    private final int capacidadeMaxima; // a capacidade nao pode ser alterada

    // construtores
    public Frigobar(int capacidadeMaxima) {
        this.inventarioFrigobar = new RepositorioItensFrigobar(capacidadeMaxima);
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Getters e Setters
    public RepositorioItensFrigobar getInventarioFrigobar() {
        return inventarioFrigobar;
    }  
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setInventarioFrigobar(RepositorioItensFrigobar inventarioFrigobar) {
        this.inventarioFrigobar = inventarioFrigobar;
    }
    
    // Métodos para adicionar e remover itens do frigobar
    public void adicionarItem(ItemFrigobar item) {
        inventarioFrigobar.adicionar(item);
    }

    public void removerItem(ItemFrigobar item) {
        inventarioFrigobar.remover(item);
        // caso o item seja removido, precisamos debitar da conta responsavel
    }

}
