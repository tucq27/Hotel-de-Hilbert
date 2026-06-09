package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class TelaVerAlertasController {

    @FXML
    private ListView<String> listCheckPen;

    @FXML
    private ListView<String> listCheckRe;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {

        // Exemplos para teste
        listCheckPen.getItems().add("Hospedagem #101 - Checkout pendente");
        listCheckPen.getItems().add("Hospedagem #102 - Checkout pendente");

        listCheckRe.getItems().add("Hospedagem #085 - Checkout realizado");
        listCheckRe.getItems().add("Hospedagem #090 - Checkout realizado");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}