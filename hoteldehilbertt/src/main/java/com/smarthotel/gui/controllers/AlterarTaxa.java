package com.smarthotel.gui.controllers;

import com.smarthotel.models.Quarto;
import com.smarthotel.negocios.ControladorPagamentos;
import com.smarthotel.negocios.IContPagamentos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlterarTaxa extends Transitavel{

    @FXML
    private Button btnAtualizar;
    
    @FXML
    private Label lblQPadrao;
    @FXML
    private Label lblQSuite;
    @FXML
    private Label lblQPres;
    @FXML
    private Label lblTaxaTemp;
    @FXML
    private Label lblDiaria;
    @FXML
    private Label lblServico;
    @FXML
    private Label lblServicoNoturno;

    @FXML
    private TextField txtNovoPadrao;
    @FXML
    private TextField txtNovoSuite;
    @FXML
    private TextField txtNovoPres;
    @FXML
    private TextField txtNovaTaxaTemp;
    @FXML
    private TextField txtNovaDiaria;
    @FXML
    private TextField txtNovoServico;
    @FXML
    private TextField txtNovoServicoNoturno;

    @FXML
    private void initialize() {
        lblQPadrao.setText(String.valueOf(Quarto.getTaxaPadrao()));
        lblQSuite.setText(String.valueOf(Quarto.getTaxaSuite()));
        lblQPres.setText(String.valueOf(Quarto.getTaxaPresidencial()));
        lblTaxaTemp.setText(String.valueOf(Quarto.getTaxaTemporada()));
        lblDiaria.setText(String.valueOf(Quarto.getValorDiaria()));

        lblServico.setText(String.valueOf(Quarto.getValorServico()));
        lblServicoNoturno.setText(String.valueOf(Quarto.getTaxaNoturna()));
    }

    @FXML
    private void atualizar() {
        IContPagamentos contP = ControladorPagamentos.getInstance();
        
        double padrao = converter(txtNovoPadrao.getText());
        double suite = converter(txtNovoSuite.getText());
        double presidencial = converter(txtNovoPres.getText());
        double temporada = converter(txtNovaTaxaTemp.getText());
        double noturna = converter(txtNovoServicoNoturno.getText());

        double diaria = converter(txtNovaDiaria.getText());
        double servico = converter(txtNovoServico.getText());

        contP.alterarValores(diaria, servico);
        contP.alterarTaxas(padrao, suite, presidencial, temporada, noturna);
        initialize();
    }

    private double converter(String numero) {
        String numeroLimpo = numero.replace(",",".");

        if (numeroLimpo.isEmpty()) {
            return 0.0;
        }
        double numeroFinal = Double.parseDouble(numeroLimpo);
        double numArredondado = Math.round(numeroFinal * 100.0)/ 100.0;

        return numArredondado;
    }
}
