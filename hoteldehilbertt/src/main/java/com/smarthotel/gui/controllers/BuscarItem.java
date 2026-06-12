package com.smarthotel.gui.controllers;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Item;
import com.smarthotel.negocios.ControladorItens;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BuscarItem {

    protected static Item itemSelecionado;

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

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private void abrirTela(String caminhoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
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