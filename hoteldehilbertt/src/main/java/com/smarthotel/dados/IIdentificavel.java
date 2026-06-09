package com.smarthotel.dados;

public interface IIdentificavel {
    String getChave(); // método para obter a chave de identificação do objeto
    void setChave(String id);

    /*  
        Todas as classes basicas que são armazenadas em um repositorio devem implementar
        essa interface, para que o repositorio padrao possa usar o metodo getChave()

        ao usar o metodo getChave() a classe deve retornar um atributo de único do objeto,
        como cpf para pessoa, id para quarto, etc
    */
}
