package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
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

            String cpfAntigo = hospedeSelecionado.getCpf();

            if (txtNovoNome.getText() != null && !txtNovoNome.getText().isBlank()) {
                hospedeSelecionado.setNome(txtNovoNome.getText());
            }

            if (txtNovoTelefone.getText() != null && !txtNovoTelefone.getText().isBlank()) {
                hospedeSelecionado.setTelefone(txtNovoTelefone.getText());
            }

            if (txtNovoEmail.getText() != null && !txtNovoEmail.getText().isBlank()) {
                hospedeSelecionado.setEmail(txtNovoEmail.getText());
            }

            if (txtNovaPreferencia.getText() != null && !txtNovaPreferencia.getText().isBlank()) {
                hospedeSelecionado.setPreferencias(txtNovaPreferencia.getText());
            }

            if (cbRestricao.getValue() != null) {
                hospedeSelecionado.setRestricao(RestricaoHospede.valueOf(cbRestricao.getValue()));
            }

            ControladorPessoas controladorPessoas = new ControladorPessoas();
            controladorPessoas.atualizarPessoa(cpfAntigo, hospedeSelecionado);

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

            ControladorPessoas controladorPessoas = new ControladorPessoas();
            controladorPessoas.removerPessoa(hospedeSelecionado.getCpf());

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
        lblTelefone.setText(hospedeSelecionado.getTelefone());
        lblEmail.setText(hospedeSelecionado.getEmail());
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