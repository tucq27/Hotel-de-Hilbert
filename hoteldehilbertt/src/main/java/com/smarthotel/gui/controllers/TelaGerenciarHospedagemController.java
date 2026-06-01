package com.smarthotel.gui.controllers;

import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.exceptions.*;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TelaGerenciarHospedagemController extends TelaBuscarHospedagemController {

    ControladorHospedagens controladorHospedagens = new ControladorHospedagens();

    @FXML
    private Button btnVoltar;

    @FXML
    private void checkIn() {
        try{
            controladorHospedagens.checkIn(hospedagemSelecionada);

            System.out.println("Check-in");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check-in Realizado");
            alert.setContentText("Check-in realizado com sucesso para a hospedagem selecionada.");
            alert.showAndWait();
        } catch(CIJRException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (CIFException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (QIException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage() +" Quarto: " + e.getQuarto().getId() + ", Status: " + e.getStatus());
            alert.showAndWait();
        }
    }

    @FXML
    private void checkOut() {

        try {
            controladorHospedagens.checkOut(hospedagemSelecionada);

            System.out.println("Check-out");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check-out Realizado");
            alert.setContentText("Check-out realizado com sucesso para a hospedagem selecionada.");
            alert.showAndWait();
        } catch (CINRException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (COJRException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private ComboBox<String> cbTipoServico;

    @FXML
    public void initialize() {
        cbTipoServico.getItems().add("Limpar Quarto");
        cbTipoServico.getItems().add("Levar Comida");
        cbTipoServico.getItems().add("Lavar Roupa");
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