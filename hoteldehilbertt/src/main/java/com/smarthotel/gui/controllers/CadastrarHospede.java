package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Pessoa;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarHospede {

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtNome;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtPreferencias;

    @FXML
    private Button btnVoltar;

    @FXML
    private void verificarCpf() {
        try {
            String cpf = txtCpf.getText();

            if (cpf == null || cpf.isBlank()) {
                mostrarErro("Digite um CPF para verificar.");
                return;
            }

            ControladorPessoas controladorPessoas = ControladorPessoas.getInstance();
            Pessoa pessoa = controladorPessoas.buscarPessoa(cpf);

            txtNome.setText(pessoa.getNome());
            dpDataNascimento.setValue(pessoa.getDataNascimento());
            txtTelefone.setText(pessoa.getTelefone());
            txtEmail.setText(pessoa.getEmail());

            mostrarInfo("Pessoa encontrada. Complete as informações de hóspede.");

        } catch (ONEException e) {
            mostrarErro("Pessoa não encontrada. Preencha os dados manualmente.");
        }
    }

    @FXML
    private void cadastrarHospede() {
        try {
            if (txtNome.getText().isBlank()
                    || txtCpf.getText().isBlank()
                    || dpDataNascimento.getValue() == null) {

                mostrarErro("Preencha nome, CPF e data de nascimento.");
                return;
            }

            Pessoa pessoaBase = new Pessoa(
                    txtNome.getText(),
                    txtCpf.getText(),
                    dpDataNascimento.getValue(),
                    txtTelefone.getText(),
                    txtEmail.getText()
            );

            ControladorPessoas controladorPessoas = ControladorPessoas.getInstance();
            controladorPessoas.adicionarPessoa(pessoaBase);
            try {
                controladorPessoas.adicionarHospede(txtCpf.getText(), txtPreferencias.getText());
            } catch (ONEException ignored) {
            }

            mostrarInfo("Hóspede cadastrado com sucesso!");
            limparCampos();

        } catch (ORException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private void limparCampos() {
        txtCpf.clear();
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtPreferencias.clear();
        dpDataNascimento.setValue(null);
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}