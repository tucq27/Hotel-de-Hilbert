package com.smarthotel.negocios;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;

public interface IContRelatorios {

    ArrayList<Hospedagem> gerarOcupacaoDiaria();

    ArrayList<Hospedagem> gerarOcupacaoMensal();

    ArrayList<Hospedagem> gerarRelatorioSaidas();

    ArrayList<Hospedagem> alertarSaidaPendente();

    void exportarRelatorioGeralCSV() throws Exception;
}
