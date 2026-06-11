package com.smarthotel.negocios;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.StatusQuarto;
import com.smarthotel.models.TipoQuarto;
import com.smarthotel.negocios.exceptions.LFException;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

public interface IContQuartos {

    // Gerenciamento do repositório de quartos
    Quarto buscarQuarto(String id) throws ONEException;
    void adicionarQuarto(Quarto quarto) throws ORException;
    void removerQuarto(String id) throws ONEException;
    void atualizarQuarto(String id, int numero, int andar, int capacidade, 
                            StatusQuarto status, TipoQuarto tipo) throws ONEException;

    ArrayList<Quarto> listarQuartos();
    ArrayList<Quarto> listarQuartosDisponiveis();
    void alterarTaxaQuarto(TipoQuarto tipo, double taxa);
    void alterarTaxaTemporada(double taxa);

    // Gerenciamento do frigobar
    void pegarItemFrigobar(Hospedagem hosp, String idQuarto, String idItem) throws ONEException;
    void reporItemFrigobar(String idQuarto, String idItem) throws ONEException, LFException;
}
