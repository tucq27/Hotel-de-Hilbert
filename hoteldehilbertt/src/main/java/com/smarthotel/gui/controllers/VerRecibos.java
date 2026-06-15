package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Recibo;
import com.smarthotel.negocios.GeradorPDF;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private Button btnGerarFatura;
    @FXML
    private Button btnPagamento;
    @FXML
    private Button btnGerarRecibo;

    @FXML
    protected Label lblResponsavel;
    @FXML
    protected Label lblDadosPagamento;
    @FXML
    protected Label lblSaldoPago;
    @FXML
    protected Label lblSaldoPendente;
    @FXML
    protected Label lblValorDiaria;
    @FXML
    protected ListView<String> listRecibos;

    @FXML
    public void initialize() {
        setHospedagemSelecionada(BuscarHospedagem.getHospedagemSelecionada());

        double dividaPendente = hospedagemSelecionada.getConta().getSaldoPendente();
        ArrayList<String> recibos = new ArrayList<>(); 

        for (Recibo r : hospedagemSelecionada.getConta().getRecibos()) {
            double valor = r.getValor();
            recibos.add(r.getId() + "  |  " + r.getTipo() + "  |  " + String.format("RS %.2f", valor));
        }

        lblResponsavel.setText( hospedagemSelecionada.getConta().getResponsavel().getNome() );
        lblSaldoPendente.setText(String.format("RS %.2f", dividaPendente));
        //lblSaldoPago
        listRecibos.setItems(FXCollections.observableArrayList(recibos));
        lblDadosPagamento.setText("Não informados");
        
        String dadosPagamento = hospedagemSelecionada.getConta().getDadosPagamento();
        if (dadosPagamento != null) {
            lblDadosPagamento.setText(dadosPagamento);
        }
    }

    @FXML
    private void gerarFatura() {
        try {
            GeradorPDF gerador = new GeradorPDF();
            gerador.gerarFaturaPDF(hospedagemSelecionada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Fatura Gerada", "A fatura PDF foi gerada com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao gerar a fatura.");
        }
    }
}
