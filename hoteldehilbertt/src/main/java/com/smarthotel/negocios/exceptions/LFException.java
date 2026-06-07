package com.smarthotel.negocios.exceptions;

public class LFException extends Exception{
    public LFException(int capacidade){
        super("Limite do Frigobar Excedido: " + capacidade);
    }
}
