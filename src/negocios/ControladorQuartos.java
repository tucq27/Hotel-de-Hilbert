package negocios;
import dados.IRepositorio;
import dados.RepoQuartos;
import dados.Repositorio;
import models.Quarto;
import dados.IRepositorio;
import dados.RepoQuartos;

import negocios.exceptions.*;

public class ControladorQuartos {

    static private RepoQuartos repositorioQuartos;

    public ControladorQuartos() {

        if (repositorioQuartos == null) {
            repositorioQuartos = new RepoQuartos();
        }
    }

    public void addQuarto(Quarto quarto) throws ERException {
        if (repositorioQuartos.buscar(quarto.getId()) != null) {
            throw new ERException(quarto);
        }
        repositorioQuartos.adicionar(quarto);
    }

    public Quarto buscarQuarto(String id) {
        return repositorioQuartos.buscar(id);
    }

}
