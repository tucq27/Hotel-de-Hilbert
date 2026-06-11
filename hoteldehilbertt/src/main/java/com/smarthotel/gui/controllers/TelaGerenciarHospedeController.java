package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.RestricaoHospede;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class TelaGerenciarHospedeController {

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
    private Label lblRestricao;

    @FXML
    private TextArea txtPreferenciasAtual;

    @FXML
    private TextField txtNovoNome;

    @FXML
    private TextField txtNovoTelefone;

    @FXML
    private TextField txtNovoEmail;

    @FXML
    private TextArea txtNovaPreferencia;

    @FXML
    private ComboBox<String> cbRestricao;

    @FXML
    private Button btnVoltar;

    private Hospede hospedeSelecionado;

    @FXML
    public void initialize() {
        cbRestricao.getItems().add("DISPONIVEL");
        cbRestricao.getItems().add("PROIBIDO");

        if (TelaBuscarPessoaController.pessoaSelecionada instanceof Hospede) {
            hospedeSelecionado = (Hospede) TelaBuscarPessoaController.pessoaSelecionada;
            preencherTela();
        }
    }

    @FXML
    private void atualizarHospede() {
        try {
            if (hospedeSelecionado == null) {
                mostrarErro("Nenhum hóspede selecionado.");
                return;
            }

            String cpf = hospedeSelecionado.getCpf();

            String novoNome = txtNovoNome.getText().isBlank() ? null : txtNovoNome.getText();
            String novoTelefone = txtNovoTelefone.getText().isBlank() ? null : txtNovoTelefone.getText();
            String novoEmail = txtNovoEmail.getText().isBlank() ? null : txtNovoEmail.getText();
            String novaPreferencia = txtNovaPreferencia.getText().isBlank() ? null : txtNovaPreferencia.getText();

            RestricaoHospede novaRestricao = null;
            if (cbRestricao.getValue() != null) {
                novaRestricao = RestricaoHospede.valueOf(cbRestricao.getValue());
            }

            ControladorPessoas controlador = ControladorPessoas.getInstance();

            controlador.atualizarPessoa(cpf, novoNome, null, novoTelefone, novoEmail);
            controlador.atualizarHospede(cpf, novaPreferencia, null, novaRestricao);

            hospedeSelecionado = (Hospede) controlador.buscarPessoa(cpf);
            TelaBuscarPessoaController.pessoaSelecionada = hospedeSelecionado;

            preencherTela();
            limparCampos();

            mostrarInfo("Hóspede atualizado com sucesso!");

        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void excluirHospede() {
        try {
            if (hospedeSelecionado == null) {
                mostrarErro("Nenhum hóspede selecionado.");
                return;
            }

            ControladorPessoas controlador = ControladorPessoas.getInstance();
            controlador.removerPessoa(hospedeSelecionado.getCpf());

            TelaBuscarPessoaController.pessoaSelecionada = null;

            mostrarInfo("Hóspede excluído com sucesso!");

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();

        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private void preencherTela() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblNome.setText(hospedeSelecionado.getNome());
        lblCpf.setText(hospedeSelecionado.getCpf());
        lblNascimento.setText(hospedeSelecionado.getDataNascimento().format(formato));
        lblTelefone.setText(String.valueOf(hospedeSelecionado.getTelefone()));
        lblEmail.setText(String.valueOf(hospedeSelecionado.getEmail()));
        lblRestricao.setText(String.valueOf(hospedeSelecionado.getRestricao()));

        if (hospedeSelecionado.getPreferencias() == null || hospedeSelecionado.getPreferencias().isBlank()) {
            txtPreferenciasAtual.setText("-");
        } else {
            txtPreferenciasAtual.setText(hospedeSelecionado.getPreferencias());
        }
    }

    private void limparCampos() {
        txtNovoNome.clear();
        txtNovoTelefone.clear();
        txtNovoEmail.clear();
        txtNovaPreferencia.clear();
        cbRestricao.setValue(null);
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