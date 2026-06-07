package com.smarthotel.negocios;

import java.util.ArrayList;

import com.smarthotel.dados.RepoHospedagens;
import com.smarthotel.models.Hospedagem;

public interface IContRelatorios {
    ArrayList<Hospedagem> gerarOcupacaoDiaria(RepoHospedagens repositorioHospedagens);
    ArrayList<Hospedagem> gerarOcupacaoMensal(RepoHospedagens repositorioHospedagens);
    ArrayList<Hospedagem> gerarRelatorioSaidas(RepoHospedagens repositorioHospedagens);
    ArrayList<Hospedagem> alertarSaidaPendente(RepoHospedagens repositorioHospedagens);
}
