package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaAdmQuartoController {

    @FXML
    private Button btnCadastrarQuarto;

    @FXML
    private Button btnBuscarQuarto;

    @FXML
    private Button btnPdfMensal;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarQuarto.fxml",
                "Cadastrar Quarto"
        );
    }

    @FXML
    private void abrirBuscarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarQuarto.fxml",
                "Buscar Quarto"
        );
    }

    @FXML
    private void gerarPdfMensal() {
        // gera pdf csv
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