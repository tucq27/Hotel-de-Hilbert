package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaBuscarQuartoController {

    @FXML
    private TextField txtIdQuartos;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVoltar;

    @FXML
    private ListView<String> listQuartosDisp;

    @FXML
    private ListView<String> listQuartosOcu;

    @FXML
    public void initialize() {
        listQuartosDisp.getItems().add("Quarto 101 - Padrão");
        listQuartosDisp.getItems().add("Quarto 102 - Suíte");

        listQuartosOcu.getItems().add("Quarto 201 - Ocupado");
        listQuartosOcu.getItems().add("Quarto 202 - Ocupado");
    }

    @FXML
    private void buscarQuarto() {
        String idQuarto = txtIdQuartos.getText();

        if (idQuarto.isEmpty()) {
            System.out.println("Digite o ID do quarto.");
            return;
        }

        System.out.println("Buscar quarto ID: " + idQuarto);
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}