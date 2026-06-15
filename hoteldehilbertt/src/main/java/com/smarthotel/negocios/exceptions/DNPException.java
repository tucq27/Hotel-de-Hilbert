package com.smarthotel.negocios.exceptions;

public class DNPException extends Exception{
    public DNPException() {
        super("Diária não paga");
    }
}
