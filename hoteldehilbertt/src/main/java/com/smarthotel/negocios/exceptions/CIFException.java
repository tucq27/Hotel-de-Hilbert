package com.smarthotel.negocios.exceptions;

public class CIFException extends Exception {
    public CIFException() {
        super("Check-in realizado fora do dia previsto!");
    }
    
}
