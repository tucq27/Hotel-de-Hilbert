package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class AdminQuarto extends Transitavel {

    @FXML
    private Button btnCadastrarQuarto;
    @FXML
    private Button btnBuscarQuarto;
    @FXML
    private Button btnAlterarTaxa;
    @FXML
    private Button btnRelatorioFaturamento;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarQuarto.fxml",
                "Cadastrar Quarto"
        );
    }

    @FXML
    private void abrirBuscarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarQuarto.fxml",
                "Buscar Quarto"
        );
    }

    @FXML
    private void abrirAlterarTaxa() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAlterarTaxa.fxml",
                "Alterar Taxas"
        );
    }

    @FXML
    private void gerarRelatorioFaturamento() {
        // aviso, ainda implementando
       mostrarAlerta(AlertType.WARNING, "Implementar", "Gerar Relatorio CSV");
    } 
        
}