package com.smarthotel.negocios;

import com.smarthotel.models.Quarto;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

public interface IContQuartos {

    // Gerenciamento do repositório de quartos
    Quarto buscarQuarto(String id) throws ONEException;
    void adicionarQuarto(Quarto quarto) throws ORException;
    void removerQuarto(String id) throws ONEException;
    void atualizarQuarto(String id, Quarto quarto) throws ONEException;

    ArrayList<Quarto> listarQuartos();
    ArrayList<Quarto> listarQuartosDisponiveis();

    // Gerenciamento do frigobar
    void pegarItemFrigobar(String idQuarto, String idItem) throws ONEException;
    void reporItemFrigobar(String idQuarto, String idItem) throws ONEException;
}
