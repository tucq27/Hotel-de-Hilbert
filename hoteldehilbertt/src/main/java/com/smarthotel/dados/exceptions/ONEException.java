package com.smarthotel.dados.exceptions;

public class ONEException extends Exception {
    
    // Objeto Não Encontrado (ONE) - quando um objeto não é encontrado no repositório
    public ONEException(String mensagem) {
        super(mensagem);
    }
}
