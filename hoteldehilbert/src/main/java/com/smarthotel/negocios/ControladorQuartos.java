package com.smarthotel.negocios;
import com.smarthotel.dados.IRepositorio;
import com.smarthotel.dados.RepoQuartos;
import com.smarthotel.dados.Repositorio;
import com.smarthotel.models.Quarto;
import com.smarthotel.dados.IRepositorio;
import com.smarthotel.dados.RepoQuartos;

import com.smarthotel.negocios.exceptions.*;

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
