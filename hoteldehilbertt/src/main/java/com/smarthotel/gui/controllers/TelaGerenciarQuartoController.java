package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Quarto;
import com.smarthotel.negocios.ControladorQuartos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaGerenciarQuartoController {

    @FXML
    private Label lblIdQuarto;

    @FXML
    private Label lblNumeroQuarto;

    @FXML
    private Label lblAndarQuarto;

    @FXML
    private Label lblCapacidadeQuarto;

    @FXML
    private Label lblStatusQuarto;

    @FXML
    private Label lblTipoQuarto;

    @FXML
    private TextField txtNovoNumero;

    @FXML
    private TextField txtNovoAndar;

    @FXML
    private TextField txtNovaCapacidade;

    @FXML
    private ComboBox<String> cbTipoQuarto;

    @FXML
    private Button btnVoltar;

    private Quarto quartoSelecionado;

    @FXML
    public void initialize() {
        cbTipoQuarto.getItems().add("Padrão");
        cbTipoQuarto.getItems().add("Suíte");
        cbTipoQuarto.getItems().add("Presidencial");

        quartoSelecionado = TelaBuscarQuartoController.quartoSelecionado;

        if (quartoSelecionado != null) {
            preencherTela();
        }
    }

    @FXML
    private void atualizarQuarto() {
        try {
            if (quartoSelecionado == null) {
                mostrarErro("Nenhum quarto selecionado.");
                return;
            }

            String id = quartoSelecionado.getId();

            int novoNumero = txtNovoNumero.getText().isBlank()
                    ? quartoSelecionado.getNumero()
                    : Integer.parseInt(txtNovoNumero.getText());

            int novoAndar = txtNovoAndar.getText().isBlank()
                    ? quartoSelecionado.getAndar()
                    : Integer.parseInt(txtNovoAndar.getText());

            int novaCapacidade = txtNovaCapacidade.getText().isBlank()
                    ? quartoSelecionado.getCapacidade()
                    : Integer.parseInt(txtNovaCapacidade.getText());

            String tipo = cbTipoQuarto.getValue();

            if (tipo == null || tipo.isBlank()) {
                tipo = descobrirTipo(quartoSelecionado);
            }

            Quarto novoQuarto;

            if (tipo.equals("Padrão")) {
                novoQuarto = new QuartoPadrao(novoNumero, novoAndar, novaCapacidade);
            } else if (tipo.equals("Suíte")) {
                novoQuarto = new QuartoSuite(novoNumero, novoAndar, novaCapacidade);
            } else {
                novoQuarto = new QuartoPresidencial(novoNumero, novoAndar, novaCapacidade);
            }

            novoQuarto.setId(id);
            novoQuarto.setStatus(quartoSelecionado.getStatus());
            novoQuarto.setFrigobar(quartoSelecionado.getFrigobar());

            ControladorQuartos controladorQuartos = new ControladorQuartos();
            controladorQuartos.atualizarQuarto(id, novoQuarto);

            quartoSelecionado = novoQuarto;
            TelaBuscarQuartoController.quartoSelecionado = novoQuarto;

            preencherTela();

            txtNovoNumero.clear();
            txtNovoAndar.clear();
            txtNovaCapacidade.clear();
            cbTipoQuarto.setValue(null);

            mostrarInfo("Quarto atualizado com sucesso!");

        } catch (NumberFormatException e) {
            mostrarErro("Número, andar e capacidade precisam ser valores numéricos.");
        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void excluirQuarto() {
        try {
            if (quartoSelecionado == null) {
                mostrarErro("Nenhum quarto selecionado.");
                return;
            }

            ControladorQuartos controladorQuartos = new ControladorQuartos();
            controladorQuartos.removerQuarto(quartoSelecionado.getId());

            TelaBuscarQuartoController.quartoSelecionado = null;

            mostrarInfo("Quarto excluído com sucesso!");

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
        lblIdQuarto.setText(quartoSelecionado.getId());
        lblNumeroQuarto.setText(String.valueOf(quartoSelecionado.getNumero()));
        lblAndarQuarto.setText(String.valueOf(quartoSelecionado.getAndar()));
        lblCapacidadeQuarto.setText(String.valueOf(quartoSelecionado.getCapacidade()));
        lblStatusQuarto.setText(String.valueOf(quartoSelecionado.getStatus()));
        lblTipoQuarto.setText(descobrirTipo(quartoSelecionado));
    }

    private String descobrirTipo(Quarto quarto) {
        if (quarto instanceof QuartoPresidencial) {
            return "Presidencial";
        } else if (quarto instanceof QuartoSuite) {
            return "Suíte";
        } else {
            return "Padrão";
        }
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