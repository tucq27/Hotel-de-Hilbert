package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Pessoa;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastrarPessoa extends Transitavel {

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private void cadastrar() {
        try {
            if (txtNome.getText().isBlank()
                    || txtCpf.getText().isBlank()
                    || dpDataNascimento.getValue() == null) {

                mostrarErro("Preencha nome, CPF e data de nascimento.");
                return;
            }

            Pessoa pessoa = new Pessoa(
                    txtNome.getText(),
                    txtCpf.getText(),
                    dpDataNascimento.getValue(),
                    txtTelefone.getText(),
                    txtEmail.getText()
            );

            ControladorPessoas controladorPessoas = ControladorPessoas.getInstance();
            controladorPessoas.adicionarPessoa(pessoa);

            mostrarInfo("Pessoa cadastrada com sucesso!");

            limparCampos();

        } catch (ORException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        dpDataNascimento.setValue(null);
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}