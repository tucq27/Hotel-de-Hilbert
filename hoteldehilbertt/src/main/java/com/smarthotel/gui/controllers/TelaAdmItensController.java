package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaAdmItensController {

    @FXML
    private Button btnCadastrarItem;

    @FXML
    private Button btnBuscarItem;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarItem() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarItem.fxml",
                "Cadastrar Item"
        );
    }

    @FXML
    private void abrirBuscarItem() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarItem.fxml",
                "Buscar Item"
        );
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
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