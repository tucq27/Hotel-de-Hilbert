package com.smarthotel.negocios.exceptions;

import com.smarthotel.models.Hospede;

public class HPException extends Exception {
    private Hospede hospede;
    
    public HPException(Hospede hospede) {
        super("Hóspede possui restrição para hospedagem!");
        this.hospede = hospede;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }
    
}
