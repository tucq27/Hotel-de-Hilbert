package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaAdmQuartoController {

    @FXML
    private Button btnCadastrarQuarto;

    @FXML
    private Button btnBuscarQuarto;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarQuarto() {
        System.out.println("Abrir tela Cadastrar Quarto");
    }

    @FXML
    private void abrirBuscarQuarto() {
        System.out.println("Abrir tela Buscar Quarto");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}