package negocios;

import java.util.ArrayList;

import dados.RepoQuartos;
import dados.RepoRelatorios;
import models.RelatorioOcupacao;
import models.Quarto;
import models.StatusQuarto;

public class ControladorRelatorios {
    private RepoRelatorios relatorioOcupacao;

    public ControladorRelatorios() {
        this.relatorioOcupacao = new RepoRelatorios();
    }

    public void gerarRelatorioOcupacao(RepoQuartos repositorioQuartos) {
        ArrayList<Quarto> quartosOcupados = new ArrayList<>();
        
        for (Quarto quarto : repositorioQuartos.getObjetos()) {
            if (quarto.getStatus().equals(StatusQuarto.OCUPADO)) {
                quartosOcupados.add(quarto);
            }
        }
        relatorioOcupacao.adicionar(new RelatorioOcupacao("idTemporario", quartosOcupados));
    }
}
