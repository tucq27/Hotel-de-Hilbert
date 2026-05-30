package com.smarthotel.models;

import java.util.ArrayList;
import java.util.Date;

import com.smarthotel.dados.IIdentificavel;

public class RelatorioOcupacao implements IIdentificavel{
    private String id;
    private ArrayList<Quarto> quartosOcupados;
    private Date dataRelatorio;

    public RelatorioOcupacao(String id,ArrayList<Quarto> quartos) {
        this.id = id;
        this.quartosOcupados = quartos;
        this.dataRelatorio = new Date();
    }

    public ArrayList<Quarto> getQuartosOcupados() {
        return quartosOcupados;
    }

    public void setQuartosOcupados(ArrayList<Quarto> quartosOcupados) {
        this.quartosOcupados = quartosOcupados;
    }

    public Date getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(Date dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getChave() {
        return id;
    }
    
}
