package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPessoas {

    @FXML
    private Button btnCadastrarHospede;

    @FXML
    private Button btnCadastrarFuncionario;

    @FXML
    private Button btnCadastrarPessoa;

    @FXML
    private Button btnBuscarPessoa;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarHospede() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarHospede.fxml",
                "Cadastrar Hóspede"
        );
    }

    @FXML
    private void abrirCadastrarFuncionario() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarFuncionario.fxml",
                "Cadastrar Funcionário"
        );
    }

    @FXML
    private void abrirCadastrarPessoa() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarPessoa.fxml",
                "Cadastrar Pessoa"
        );
    }

    @FXML
    private void abrirBuscarPessoa() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarPessoa.fxml",
                "Buscar Pessoa"
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