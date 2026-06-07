package com.smarthotel.negocios;

import com.smarthotel.dados.RepoItens;

public class ControladorItens {
    private static RepoItens repositorioItens;

    public ControladorItens() {
        if (repositorioItens == null) {
            repositorioItens = new RepoItens();
        }
    }

    
}
