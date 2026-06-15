package com.smarthotel.negocios.exceptions;

public class RNCException extends Exception{
    public RNCException() {
        super("Reserva não cancelável, Multa exigida");
    }
}
