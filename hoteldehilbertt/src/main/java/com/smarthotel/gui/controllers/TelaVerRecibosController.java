package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Recibo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TelaVerRecibosController extends TelaGerenciarHospedagemController{
    
    @FXML
    protected Label lblResponsavel;
    @FXML
    protected Label lblDadosPagamento;
    @FXML
    protected Label lblDivida;
    @FXML
    protected ListView<String> listRecibos;

    @FXML
    public void initialize() {
        double divida = hospedagemSelecionada.getConta().getDividaTotal();
        ArrayList<String> recibos = new ArrayList<>(); 

        for (Recibo r : hospedagemSelecionada.getConta().getRecibos()) {
            double valor = r.getValor();
            recibos.add(r.getId() + "  |  " + r.getTipo() + "  |  " + String.format("RS %.2f", valor));
        }

        lblResponsavel.setText( hospedagemSelecionada.getConta().getResponsavel().getNome() );
        lblDivida.setText(String.format("RS %.2f", divida));
        listRecibos.setItems(FXCollections.observableArrayList(recibos));
        lblDadosPagamento.setText("Não informados");
        
        String dadosPagamento = hospedagemSelecionada.getConta().getDadosPagamento();
        if (dadosPagamento != null) {
            lblDadosPagamento.setText(dadosPagamento);
        }
    }

}
