package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.TipoQuarto;
import com.smarthotel.negocios.ControladorQuartos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class TelaCadastrarQuartoController {

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private ComboBox<String> cbTipoQuarto;

    @FXML
    private Spinner<Integer> spnCapacidade;

    @FXML
    private Spinner<Integer> spnAndar;

    @FXML
    public void initialize() {
        cbTipoQuarto.getItems().clear();
        cbTipoQuarto.getItems().add("PADRAO");
        cbTipoQuarto.getItems().add("SUITE");
        cbTipoQuarto.getItems().add("PRESIDENCIAL");

        cbTipoQuarto.setValue("PADRAO");

        spnCapacidade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1)
        );

        spnAndar.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0)
        );
    }

    @FXML
    private void cadastrar() {
        try {
            System.out.println("Clicou em cadastrar quarto");

            String tipoSelecionado = cbTipoQuarto.getValue();

            if (tipoSelecionado == null || tipoSelecionado.isBlank()) {
                mostrarErro("Selecione o tipo do quarto.");
                return;
            }

            int capacidade = spnCapacidade.getValue();
            int andar = spnAndar.getValue();

            TipoQuarto tipo = TipoQuarto.valueOf(tipoSelecionado);

            int numero = gerarNumeroAutomatico();

            Quarto quarto = new Quarto(tipo, numero, andar, capacidade);

            ControladorQuartos controladorQuartos = ControladorQuartos.getInstance();
            controladorQuartos.adicionarQuarto(quarto);

            mostrarInfo("Quarto cadastrado com sucesso!\nID: " + quarto.getId());

            cbTipoQuarto.setValue("PADRAO");
            spnCapacidade.getValueFactory().setValue(1);
            spnAndar.getValueFactory().setValue(0);

        } catch (ORException e) {
            mostrarErro(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao cadastrar quarto: " + e.getMessage());
        }
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private int gerarNumeroAutomatico() {
        ControladorQuartos controladorQuartos = ControladorQuartos.getInstance();
        return controladorQuartos.listarQuartos().size() + 1;
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