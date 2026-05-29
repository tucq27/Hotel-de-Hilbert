package com.smarthotel.models;

import java.util.ArrayList;

//Temos que lembrar de criar uma função pra reconhecer quando um item foi comprado
public class Frigobar {
    private ArrayList<ItemFrigobar> inventarioFrigobar;
    private final int capacidadeMaxima; // a capacidade nao pode ser alterada

    // construtores
    public Frigobar(int capacidadeMaxima) {
        this.inventarioFrigobar = new ArrayList<>(capacidadeMaxima);
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Getters e Setters
    public ArrayList<ItemFrigobar> getInventarioFrigobar() {
        return inventarioFrigobar;
    }  
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setInventarioFrigobar(ArrayList<ItemFrigobar> inventarioFrigobar) {
        this.inventarioFrigobar = inventarioFrigobar;
    }
    
}
