package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Item;
import com.smarthotel.negocios.ControladorItens;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class CadastrarItem extends Transitavel {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtValor;

    @FXML
    private Button btnVoltar;

    @FXML
    private void cadastrarItem() {
        try {
            String nome = txtNome.getText();

            if (nome == null || nome.isBlank() || txtValor.getText() == null || txtValor.getText().isBlank()) {
                mostrarErro("Preencha todos os campos.");
                return;
            }

            double valor = Double.parseDouble(txtValor.getText());

            Item item = new Item("", nome, LocalDate.now(), valor);

            ControladorItens controladorItens = ControladorItens.getInstance();
            controladorItens.adicionarItem(item);

            mostrarInfo("Item cadastrado com sucesso!");

            txtNome.clear();
            txtValor.clear();

        } catch (NumberFormatException e) {
            mostrarErro("Digite um valor válido.");
        } catch (ORException e) {
            mostrarErro(e.getMessage());
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