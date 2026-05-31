package com.smarthotel.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaRealizarReservaController {

    @FXML
    private TextField txtCpfHospede;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private Button btnVoltar;

    @FXML
    private void adicionarHospede() {
        String cpf = txtCpfHospede.getText();

        if (cpf != null && !cpf.trim().isEmpty()) {
            listHospedes.getItems().add(cpf);
            txtCpfHospede.clear();
        }
    }

    @FXML
    private void confirmarReserva() {
        System.out.println("Reserva confirmada!");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}