package com.smarthotel.gui.controllers;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Item;
import com.smarthotel.negocios.ControladorItens;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class BuscarItem extends Transitavel {

    private static Item itemSelecionado;

    public static Item getItemSelecionado() {
        return itemSelecionado;
    }

    public static void setItemSelecionado(Item itemSelecionado) {
        BuscarItem.itemSelecionado = itemSelecionado;
    }

    @FXML
    private TextField txtIdItem;

    @FXML
    private ListView<String> listItens;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        carregarItens();
    }

    private void carregarItens() {
        ControladorItens controladorItens = ControladorItens.getInstance();
        ArrayList<String> itens = new ArrayList<>();

        for (Item item : controladorItens.listarItensRegistrados()) {
            itens.add(
                    "ID: " + item.getId()
                            + " | Nome: " + item.getNome()
                            + " | Valor: R$ " + item.getValor()
            );
        }

        listItens.setItems(FXCollections.observableArrayList(itens));
    }

    @FXML
    private void buscarItem() {
        try {
            String id = txtIdItem.getText();

            if (id == null || id.isBlank()) {
                mostrarErro("Digite o ID do item.");
                return;
            }

            ControladorItens controladorItens = ControladorItens.getInstance();
            itemSelecionado = controladorItens.buscarItem(id);

            abrirTela(
                    "/com/smarthotel/gui/telas/TelaGerenciarItem.fxml",
                    "Gerenciar Item"
            );

        } catch (ONEException e) {
            itemSelecionado = null;
            mostrarErro("Item não encontrado.");
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}