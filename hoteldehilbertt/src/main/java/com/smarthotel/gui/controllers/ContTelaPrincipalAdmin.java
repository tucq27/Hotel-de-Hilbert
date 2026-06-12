package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContTelaPrincipalAdmin {

    @FXML
    private void abrirAdministrarHospedagens() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmHospedagens.fxml",
                "Administrar Hospedagens"
        );
    }

    @FXML
    private void abrirAdministrarPessoas() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmPessoas.fxml",
                "Administrar Pessoas"
        );
    }

    @FXML
    private void abrirAdministrarQuartos() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmQuarto.fxml",
                "Administrar Quartos"
        );
    }

    @FXML
    private void abrirAdministrarItens() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmItens.fxml",
                "Administrar Itens"
        );
    }

    @FXML
    private void abrirAlertas() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaVerAlertas.fxml",
                "Ver Alertas"
        );
    }

    private void abrirTela(String caminhoFXML, String titulo) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(caminhoFXML)
            );

            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}