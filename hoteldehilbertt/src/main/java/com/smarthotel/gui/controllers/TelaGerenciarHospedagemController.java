package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TelaGerenciarHospedagemController {

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox<String> cbTipoServico;

    @FXML
    public void initialize() {
        if (cbTipoServico != null) {
            cbTipoServico.getItems().add("Limpar Quarto");
            cbTipoServico.getItems().add("Levar Comida");
            cbTipoServico.getItems().add("Lavar Roupa");
        }
    }

    @FXML
    private void checkIn() {
        System.out.println("Check-in");
    }

    @FXML
    private void checkOut() {
        System.out.println("Check-out");
    }

    @FXML
    private void aumentarHospedagem() {
        System.out.println("Aumentar hospedagem");
    }

    @FXML
    private void reduzirHospedagem() {
        System.out.println("Reduzir hospedagem");
    }

    @FXML
    private void pegarItem() {
        System.out.println("Pegar item");
    }

    @FXML
    private void adicionarItem() {
        System.out.println("Adicionar item");
    }

    @FXML
    private void pedirServico() {
        String servico = cbTipoServico.getValue();
        System.out.println("Serviço escolhido: " + servico);
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}