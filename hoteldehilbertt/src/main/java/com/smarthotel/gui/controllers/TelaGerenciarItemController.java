package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Item;
import com.smarthotel.negocios.ControladorItens;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaGerenciarItemController {

    @FXML
    private Label lblIdItem;

    @FXML
    private Label lblNomeItem;

    @FXML
    private Label lblValorItem;

    @FXML
    private TextField txtNovoNome;

    @FXML
    private TextField txtNovoValor;

    @FXML
    private Button btnVoltar;

    private Item itemSelecionado;

    @FXML
    public void initialize() {
        itemSelecionado = TelaBuscarItemController.itemSelecionado;

        if (itemSelecionado != null) {
            atualizarLabels();
        }
    }

    @FXML
    private void atualizarItem() {
        try {
            if (itemSelecionado == null) {
                mostrarErro("Nenhum item selecionado.");
                return;
            }

            String novoNome = txtNovoNome.getText();
            String novoValorTexto = txtNovoValor.getText();

            if (novoNome == null || novoNome.isBlank()) {
                mostrarErro("Digite o novo nome.");
                return;
            }

            if (novoValorTexto == null || novoValorTexto.isBlank()) {
                mostrarErro("Digite o novo valor.");
                return;
            }

            double novoValor = Double.parseDouble(novoValorTexto);

            itemSelecionado.setNome(novoNome);
            itemSelecionado.setValor(novoValor);

            ControladorItens controladorItens = new ControladorItens();
            controladorItens.atualizarItem(itemSelecionado.getId(), itemSelecionado);

            atualizarLabels();

            txtNovoNome.clear();
            txtNovoValor.clear();

            mostrarInfo("Item atualizado com sucesso!");

        } catch (NumberFormatException e) {
            mostrarErro("Digite um valor válido.");
        } catch (ONEException e) {
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void excluirItem() {
        try {
            if (itemSelecionado == null) {
                mostrarErro("Nenhum item selecionado.");
                return;
            }

            ControladorItens controladorItens = new ControladorItens();
            controladorItens.removerItem(itemSelecionado.getId());

            mostrarInfo("Item excluído com sucesso!");

            TelaBuscarItemController.itemSelecionado = null;

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

    private void atualizarLabels() {
        lblIdItem.setText(itemSelecionado.getId());
        lblNomeItem.setText(itemSelecionado.getNome());
        lblValorItem.setText("R$ " + itemSelecionado.getValor());
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