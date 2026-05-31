package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaPrincipalController {

    @FXML
    private void abrirAdministracao() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/smarthotel/gui/telas/TelaAdministrarHospedagens.fxml")
            );

            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Administrar Hospedagens");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}