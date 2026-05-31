package com.smarthotel.negocios;

import com.smarthotel.models.Quarto;
import java.util.ArrayList;
import com.smarthotel.negocios.exceptions.*;

public interface IContQuartos {

    // Gerenciamento do repositório de quartos
    Quarto buscarQuarto(String id) throws ONEException;
    void adicionarQuarto(Quarto quarto) throws ERException;
    void removerQuarto(String id) throws ONEException;
    void atualizarQuarto(String id, Quarto quarto) throws ONEException;

    ArrayList<Quarto> listarQuartos();
    ArrayList<Quarto> listarQuartosDisponiveis();

    // Gerenciamento do frigobar
    void pegarItemFrigobar(String idQuarto, String idItem) throws ONEException;
    // void adicionarItemFrigobar(String idQuarto, String idItem, int quantidade) throws ONEException;
}
