package com.smarthotel.models;

import java.time.LocalDateTime;

public class Recibo {
    private String id;
    private TipoRecibo tipo;
    private final LocalDateTime horario;
    private String desricaoAdicional;
    private double valor;

    public Recibo(TipoRecibo tipo, double valor){
        this.tipo= tipo;
        this.valor= valor;
        this.desricaoAdicional= "nenhuma";
        this.horario= LocalDateTime.now();
    }

    public Recibo(TipoRecibo tipo, double valor, String descricao){
        this.tipo= tipo;
        this.valor= valor;
        this.desricaoAdicional= descricao;
        this.horario= LocalDateTime.now();
    }

    // Getters e Setters
    public TipoRecibo getTipo(){
        return tipo;
    }

    public void setTipo(TipoRecibo tipo) {
        this.tipo = tipo;
    }

    public String getDesricaoAdicional() {
        return desricaoAdicional;
    }

    public void setDesricaoAdicional(String desricaoAdicional) {
        this.desricaoAdicional = desricaoAdicional;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    // nao havera setter para horario pois ele nao pode ser alterado
}
