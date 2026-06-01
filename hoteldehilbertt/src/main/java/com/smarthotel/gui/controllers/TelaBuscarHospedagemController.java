package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaBuscarHospedagemController {

    protected static Hospedagem hospedagemSelecionada;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txtIdHospedagem;

    // informações da hospedagem selecionada que serão exibidas
    @FXML
    private Label lblResponsavel;
    @FXML
    private ListView<String> listHospedes;
    @FXML
    private Label lblQuarto;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblEntrada;
    @FXML
    private Label lblSaida;

    @FXML
    private void buscarHospedagem() throws ONEException {
        try{
            ControladorHospedagens controladorHospedagens = new ControladorHospedagens();
            String idHospedagem = txtIdHospedagem.getText();
            hospedagemSelecionada = controladorHospedagens.buscarHospedagem(idHospedagem);

            ArrayList<String> nomesHospedes = new ArrayList<>();
            for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
                nomesHospedes.add(hospede.getNome());
            }
            lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
            listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
            lblQuarto.setText(hospedagemSelecionada.getQuarto().getId());
            lblStatus.setText(hospedagemSelecionada.getStatus().toString());
            lblEntrada.setText(hospedagemSelecionada.getDataEntrada().toString());
            lblSaida.setText(hospedagemSelecionada.getHorarioSaida().toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hospedagem Encontrada");
            alert.setContentText("Hospedagem de id " + idHospedagem + " encontrada.");
            alert.showAndWait();
        } catch (ONEException e) {
            hospedagemSelecionada = null;
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Hospedagem de id " + txtIdHospedagem.getText() + " não encontrada.");
            alert.showAndWait();
            return;
        }
        
        System.out.println("Buscando hospedagem...");
    }

    @FXML
    private void abrirGerenciarHospedagem() {
        if (hospedagemSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma Hospedagem Selecionada");
            alert.setContentText("Por favor, busque e selecione uma hospedagem antes de abrir o gerenciador.");
            alert.showAndWait();
            return;
        } else {
            abrirTela("/com/smarthotel/gui/telas/TelaGerenciarHospedagem.fxml", "Gerenciar Hospedagem");
        }
    }



    private void abrirTela(String caminho, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(caminho)
            );

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}