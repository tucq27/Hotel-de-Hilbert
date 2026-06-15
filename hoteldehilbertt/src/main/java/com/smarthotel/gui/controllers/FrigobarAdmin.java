package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Item;
import com.smarthotel.models.Quarto;
import com.smarthotel.negocios.ControladorItens;
import com.smarthotel.negocios.ControladorQuartos;
import com.smarthotel.negocios.IContItens;
import com.smarthotel.negocios.IContQuartos;
import com.smarthotel.negocios.exceptions.LFException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FrigobarAdmin extends Transitavel {

    private Hospedagem hospedagemSelecionada;

    @FXML
    private ListView<String> listItensFrigobar;

    @FXML
    private ListView<String> listItensRegistrados;

    @FXML
    private ComboBox<String> cbItensFrigobar;

    @FXML
    private TextField txtIdItem;

    @FXML
    public void initialize() {
        hospedagemSelecionada = GerenciarHospedagem.getHospedagemSelecionada();

        if (hospedagemSelecionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma hospedagem selecionada.");
            return;
        }

        atualizarListas();
    }

    private void atualizarListas() {
        ArrayList<String> itensFrigobar = new ArrayList<>();
        ArrayList<String> itensRegistrados = new ArrayList<>();

        Quarto quarto = hospedagemSelecionada.getQuarto();

        if (quarto != null && quarto.getFrigobar() != null) {
            for (Item item : quarto.getFrigobar().getInventarioFrigobar()) {
                itensFrigobar.add(formatarItem(item));
            }
        }

        IContItens contItens = ControladorItens.getInstance();

        for (Item item : contItens.listarItensRegistrados()) {
            itensRegistrados.add(formatarItem(item));
        }

        listItensFrigobar.setItems(FXCollections.observableArrayList(itensFrigobar));
        listItensRegistrados.setItems(FXCollections.observableArrayList(itensRegistrados));
        cbItensFrigobar.setItems(FXCollections.observableArrayList(itensFrigobar));
        cbItensFrigobar.setValue(null);
    }

    @FXML
    private void pegarItem() {
        String itemSelecionado = cbItensFrigobar.getValue();

        if (itemSelecionado == null || itemSelecionado.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um item do frigobar.");
            return;
        }

        String idItem = pegarIdDoTexto(itemSelecionado);
        String idQuarto = hospedagemSelecionada.getQuarto().getId();

        try {
            IContQuartos contQuartos = ControladorQuartos.getInstance();

            contQuartos.pegarItemFrigobar(
                    hospedagemSelecionada,
                    idQuarto,
                    idItem
            );

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Item retirado do frigobar.");
            atualizarListas();

        } catch (ONEException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    @FXML
    private void reporItem() {
        String idItem = txtIdItem.getText();

        if (idItem == null || idItem.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite o ID do item.");
            return;
        }

        String idQuarto = hospedagemSelecionada.getQuarto().getId();

        try {
            IContQuartos contQuartos = ControladorQuartos.getInstance();

            contQuartos.reporItemFrigobar(
                    idQuarto,
                    idItem.trim()
            );

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Item reposto no frigobar.");

            txtIdItem.clear();
            atualizarListas();

        } catch (ONEException | LFException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private String formatarItem(Item item) {
        return item.getId()
                + " - "
                + item.getNome()
                + " - R$ "
                + String.format("%.2f", item.getValor());
    }

    private String pegarIdDoTexto(String texto) {
        return texto.split(" - ")[0];
    }
}