package com.smarthotel.models;

import java.time.LocalDate;

import com.smarthotel.dados.IIdentificavel;

public class Item implements IIdentificavel {

    private String id;
    private static int definirId = 1;
    private String nome;
    private LocalDate validade;
    private double valor;

    public Item(String id, String nome, LocalDate validade, double valor) {
        this.id = id;
        this.nome = nome;
        this.validade = validade;
        this.valor = valor;
    }

    // getters e setters
    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public LocalDate getValidade() {
        return validade;
    }
    public double getValor() {
        return valor;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public static int getDefinirId() {
        return definirId;
    }
    public static void setDefinirId(int definirId) {
        Item.definirId = definirId;
    }

    public String getChave() {
        return this.id;
    }
    public void setChave(String id) {
        this.id = id;
    }
}
