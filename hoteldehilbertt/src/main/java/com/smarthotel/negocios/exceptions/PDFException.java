package com.smarthotel.negocios.exceptions;

public class PDFException extends Exception{
    public PDFException() {
        super("Erro ao gerar fatura em PDF");
    }
    
}
