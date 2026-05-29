package com.smarthotel.models.exceptions;

import com.smarthotel.models.Quarto;
import com.smarthotel.models.StatusQuarto;

public class QIException extends Exception {
    private Quarto quarto;
    private StatusQuarto status;
    public QIException(Quarto quarto) {
        super("Quarto indisponível");
        this.quarto = quarto;
        this.status = quarto.getStatus();
    }
    
    public Quarto getQuarto() {
        return quarto;
    }

    public StatusQuarto getStatus() {
        return status;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
    
    public void setStatus(StatusQuarto status) {
        this.status = status;
    }
}
