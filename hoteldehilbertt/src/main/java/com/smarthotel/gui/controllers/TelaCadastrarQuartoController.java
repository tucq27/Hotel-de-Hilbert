package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public class TelaCadastrarQuartoController {
    
    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private ComboBox<String> cbTipoQuarto;

    @FXML
    private Spinner<Integer> spnCapacidade;

    @FXML
    private Spinner<Integer> spnAndar;


    // botões
    @FXML
    private void cadastrar() {

    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

}
