package com.smarthotel.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaBuscarHospedagemController {

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
    private void abrirGerenciarHospedagem() {
        abrirTela("/com/smarthotel/gui/TelaGerenciarHospedagem.fxml", "Gerenciar Hospedagem");
    }

    @FXML
    private void buscarHospedagem() {
        System.out.println("Buscando hospedagem...");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}