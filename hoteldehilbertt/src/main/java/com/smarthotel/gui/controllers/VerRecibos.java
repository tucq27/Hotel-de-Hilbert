package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Recibo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class VerRecibos extends Transitavel {
    
    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        VerRecibos.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Button btnVoltar;

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
        setHospedagemSelecionada(BuscarHospedagem.getHospedagemSelecionada());

        double divida = hospedagemSelecionada.getConta().getSaldoPendente();
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
