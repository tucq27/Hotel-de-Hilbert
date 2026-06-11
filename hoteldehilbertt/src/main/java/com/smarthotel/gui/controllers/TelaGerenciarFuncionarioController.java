package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Funcionario;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class TelaGerenciarFuncionarioController {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCpf;

    @FXML
    private Label lblNascimento;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblCargo;

    @FXML
    private Label lblOcupado;

    @FXML
    private TextField txtNovoNome;

    @FXML
    private TextField txtNovoTelefone;

    @FXML
    private TextField txtNovoEmail;

    @FXML
    private TextField txtNovoCargo;

    @FXML
    private ComboBox<String> cbOcupado;

    @FXML
    private Button btnVoltar;

    private Funcionario funcionarioSelecionado;

    @FXML
    public void initialize() {

        cbOcupado.getItems().add("SIM");
        cbOcupado.getItems().add("NAO");

        if (TelaBuscarPessoaController.pessoaSelecionada instanceof Funcionario) {
            funcionarioSelecionado =
                    (Funcionario) TelaBuscarPessoaController.pessoaSelecionada;

            preencherTela();
        }
    }

    @FXML
    private void atualizarFuncionario() {

        try {

            if (funcionarioSelecionado == null) {
                mostrarErro("Nenhum funcionário selecionado.");
                return;
            }

            String cpfAntigo = funcionarioSelecionado.getCpf();

            if (!txtNovoNome.getText().isBlank()) {
                funcionarioSelecionado.setNome(txtNovoNome.getText());
            }

            if (!txtNovoTelefone.getText().isBlank()) {
                funcionarioSelecionado.setTelefone(txtNovoTelefone.getText());
            }

            if (!txtNovoEmail.getText().isBlank()) {
                funcionarioSelecionado.setEmail(txtNovoEmail.getText());
            }

            if (!txtNovoCargo.getText().isBlank()) {
                funcionarioSelecionado.setCargo(txtNovoCargo.getText());
            }

            if (cbOcupado.getValue() != null) {
                funcionarioSelecionado.setOcupado(
                        cbOcupado.getValue().equals("SIM")
                );
            }

            ControladorPessoas controlador =
                    new ControladorPessoas();

            controlador.atualizarPessoa(
                    cpfAntigo,
                    funcionarioSelecionado
            );

            TelaBuscarPessoaController.pessoaSelecionada =
                    funcionarioSelecionado;

            preencherTela();
            limparCampos();

            mostrarInfo("Funcionário atualizado com sucesso!");

        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void excluirFuncionario() {

        try {

            if (funcionarioSelecionado == null) {
                mostrarErro("Nenhum funcionário selecionado.");
                return;
            }

            ControladorPessoas controlador =
                    new ControladorPessoas();

            controlador.removerPessoa(
                    funcionarioSelecionado.getCpf()
            );

            TelaBuscarPessoaController.pessoaSelecionada = null;

            mostrarInfo("Funcionário excluído com sucesso!");

            Stage stage =
                    (Stage) btnVoltar.getScene().getWindow();

            stage.close();

        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void voltar() {

        Stage stage =
                (Stage) btnVoltar.getScene().getWindow();

        stage.close();
    }

    private void preencherTela() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblNome.setText(funcionarioSelecionado.getNome());
        lblCpf.setText(funcionarioSelecionado.getCpf());
        lblNascimento.setText(
                funcionarioSelecionado
                        .getDataNascimento()
                        .format(formato)
        );

        lblTelefone.setText(
                String.valueOf(funcionarioSelecionado.getTelefone())
        );

        lblEmail.setText(
                String.valueOf(funcionarioSelecionado.getEmail())
        );

        lblCargo.setText(
                funcionarioSelecionado.getCargo()
        );

        lblOcupado.setText(
                funcionarioSelecionado.isOcupado()
                        ? "SIM"
                        : "NÃO"
        );
    }

    private void limparCampos() {

        txtNovoNome.clear();
        txtNovoTelefone.clear();
        txtNovoEmail.clear();
        txtNovoCargo.clear();

        cbOcupado.setValue(null);
    }

    private void mostrarErro(String mensagem) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensagem) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}