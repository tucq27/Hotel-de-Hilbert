package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class FrigobarUsuario extends Transitavel {

    @FXML
    private ListView<String> listItensFrigobar;

    @FXML
    private ComboBox<String> cbItensFrigobar;

    private Hospedagem hospedagem;

    @FXML
    public void initialize() {

        hospedagem = GerenciarHospedagemUsuario.getHospedagemSelecionada();

        if (hospedagem == null) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Nenhuma hospedagem selecionada."
            );
            return;
        }

        /*
         * TEMPORÁRIO
         * Depois ligamos ao frigobar real do projeto.
         */

        ArrayList<String> itens = new ArrayList<>();

        itens.add("Água");
        itens.add("Refrigerante");
        itens.add("Chocolate");
        itens.add("Salgadinho");

        listItensFrigobar.setItems(
                FXCollections.observableArrayList(itens)
        );

        cbItensFrigobar.setItems(
                FXCollections.observableArrayList(itens)
        );
    }

    @FXML
    private void pegarItem() {

        String item = cbItensFrigobar.getValue();

        if (item == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Atenção",
                    "Selecione um item."
            );

            return;
        }

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Item Retirado",
                "Item retirado do frigobar:\n" + item
        );

        /*
         * Depois vamos integrar:
         *
         * - remover do frigobar
         * - gerar recibo
         * - adicionar na conta
         */
    }
}