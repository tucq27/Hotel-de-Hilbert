package ClassesBasicas;

import java.util.ArrayList;

public class Frigobar {
    //Temos que lembrar de criar uma função pra reconhecer quando um item foi comprado
    private ArrayList<Item> itemFrigobar;

    // construtores
    public Frigobar() {
        this.itemFrigobar = new ArrayList<>();
    }
    public Frigobar(ArrayList<Item> itens) {
        this.itemFrigobar = itens;
    }

}
