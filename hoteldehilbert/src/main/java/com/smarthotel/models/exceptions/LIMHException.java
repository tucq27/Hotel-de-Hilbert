package com.smarthotel.models.exceptions;

import com.smarthotel.models.Quarto;

public class LIMHException extends Exception {
    private Quarto quarto;
    private int limite;

    public LIMHException(Quarto quarto) {
        super("Limite de hóspedes excedido para o quarto!");
        this.quarto = quarto;
        this.limite = quarto.getCapacidade();
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }
    
}
