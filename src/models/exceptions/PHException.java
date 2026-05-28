package models.exceptions;

import models.Hospede;

public class PHException extends Exception {
    private Hospede hospede;
    
    public PHException(Hospede hospede) {
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
