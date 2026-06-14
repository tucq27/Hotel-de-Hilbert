package com.smarthotel.negocios.exceptions;

public class SPException extends Exception{
    public SPException(double valor) {
        super("Saldo pendente: " + String.format("%.2f", valor));
    }
}
