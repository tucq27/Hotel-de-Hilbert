package com.smarthotel.negocios.exceptions;

public class ERException extends Exception {
    private Object objeto;
    
    public ERException(Object objeto) {
        super("Elemento Repetido");
        this.objeto = objeto;
    }
    
    public Object getObjeto() {
        return objeto;
    }
    
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }
}
