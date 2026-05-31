package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaRealizarHospedagemController {

    @FXML
    private TextField txtCpfHospede;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private void adicionarHospede() {
        String cpf = txtCpfHospede.getText();

        if (cpf != null && !cpf.trim().isEmpty()) {
            listHospedes.getItems().add(cpf);
            txtCpfHospede.clear();
        }
    }

    @FXML
    private void confirmarHospedagem() {
        System.out.println("Hospedagem confirmada!");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) listHospedes.getScene().getWindow();
        stage.close();
    }
}