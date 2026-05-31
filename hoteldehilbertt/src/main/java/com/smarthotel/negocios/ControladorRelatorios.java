package com.smarthotel.negocios;

import java.util.ArrayList;

import com.smarthotel.dados.RepoQuartos;
import com.smarthotel.dados.RepoRelatorios;
import com.smarthotel.models.RelatorioOcupacao;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.StatusQuarto;

import com.smarthotel.dados.exceptions.ORException;

public class ControladorRelatorios {
    private RepoRelatorios relatorioOcupacao;

    public ControladorRelatorios() {
        this.relatorioOcupacao = new RepoRelatorios();
    }

    // varre o repoQuartos e gera um relatório de ocupação, ou seja, quais quartos estão ocupados
    public void gerarRelatorioOcupacao(RepoQuartos repositorioQuartos) throws ORException{
        ArrayList<Quarto> quartosOcupados = new ArrayList<>();
        
        for (Quarto quarto : repositorioQuartos.getObjetos()) {
            if (quarto.getStatus() == StatusQuarto.OCUPADO) {
                quartosOcupados.add(quarto);
            }
        }
        relatorioOcupacao.adicionar(new RelatorioOcupacao("idTemporario", quartosOcupados));
    }
}
