package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminPessoas extends Transitavel {

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
}