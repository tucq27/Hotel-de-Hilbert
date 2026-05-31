package com.smarthotel.dados.exceptions;

public class ORException extends Exception {
    private Object objeto;
    
    // Objeto Repetido (OR) - quando um objeto já existe no repositório
    public ORException(Object objeto) {
        super("Objeto Repetido");
        this.objeto = objeto;
    }
    
    public Object getObjeto() {
        return objeto;
    }
    
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }
}
