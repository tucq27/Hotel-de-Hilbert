package com.smarthotel.negocios;

import com.smarthotel.dados.RepoPessoas;
import com.smarthotel.models.Pessoa;
import com.smarthotel.dados.exceptions.ONEException;

public class ControladorPessoas {
    static private RepoPessoas repositorioPessoas;

    public ControladorPessoas() {
        if (repositorioPessoas == null) {
            repositorioPessoas = new RepoPessoas();
        }
    }

    public Pessoa buscarPessoa(String cpf) throws ONEException {
        Pessoa pessoa = repositorioPessoas.buscar(cpf);
        if (pessoa == null) {
            throw new ONEException("Pessoa com CPF " + cpf + " não encontrada.");
        }
        return pessoa;
    }
}
