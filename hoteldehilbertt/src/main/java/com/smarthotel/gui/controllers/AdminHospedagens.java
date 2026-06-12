package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminHospedagens {

    @FXML
    private Button btnVoltar;

    private void abrirTela(String caminho, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(caminho)
            );

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirHospedagemAgora() {
        abrirTela("/com/smarthotel/gui/telas/TelaRealizarHospedagem.fxml", "Realizar Hospedagem");
    }

    @FXML
    private void abrirHospedagemPrevia() {
        abrirTela("/com/smarthotel/gui/telas/TelaRealizarReserva.fxml", "Realizar Reserva");
    }

    @FXML
    private void abrirBuscarHospedagem() {
        abrirTela("/com/smarthotel/gui/telas/TelaBuscarHospedagem.fxml", "Buscar Hospedagem");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}