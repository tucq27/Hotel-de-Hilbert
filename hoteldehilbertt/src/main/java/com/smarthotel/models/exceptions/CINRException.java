package com.smarthotel.models.exceptions;

public class CINRException extends Exception {
    public CINRException() {
        super("Check-out não realizado, pois não houve check-in!");
    }
}
