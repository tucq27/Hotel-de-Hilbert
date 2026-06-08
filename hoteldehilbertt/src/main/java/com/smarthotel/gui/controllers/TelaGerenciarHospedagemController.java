package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.exceptions.CIFException;
import com.smarthotel.negocios.exceptions.CIJRException;
import com.smarthotel.negocios.exceptions.CINRException;
import com.smarthotel.negocios.exceptions.COJRException;
import com.smarthotel.negocios.exceptions.QIException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TelaGerenciarHospedagemController extends TelaBuscarHospedagemController{

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblResponsavel;
    @FXML
    private Label lblQuarto;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblEntrada;
    @FXML
    private Label lblSaida;
    @FXML
    private ListView<String> listHospedes;

    @FXML
    private ComboBox<String> cbTipoServico;

    @FXML
    public void initialize() {
        if (cbTipoServico != null) {
            cbTipoServico.getItems().add("Limpar Quarto");
            cbTipoServico.getItems().add("Levar Comida");
            cbTipoServico.getItems().add("Lavar Roupa");
        }

        ArrayList<String> nomesHospedes = new ArrayList<>();
        for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
            nomesHospedes.add(hospede.getNome());
        }
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
        listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
        lblQuarto.setText(hospedagemSelecionada.getQuarto().getId());
        lblStatus.setText(hospedagemSelecionada.getStatus().toString());
        lblEntrada.setText(hospedagemSelecionada.getDataEntrada().format(formato2));
        lblSaida.setText(hospedagemSelecionada.getHorarioSaida().format(formato1));
    }

    @FXML
    private void checkIn() {
        try{
            ControladorHospedagens contHosp = new ControladorHospedagens();
            contHosp.checkIn(hospedagemSelecionada);

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
            ControladorHospedagens contHosp = new ControladorHospedagens();
            contHosp.checkOut(hospedagemSelecionada);

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
    private void alterarEstadia() {
        abrirTela("/com/smarthotel/gui/telas/TelaAlterarEstadia.fxml", "Alterar Estadia");
    }

    @FXML
    private void verRecibos() {
        abrirTela("/com/smarthotel/gui/telas/TelaVerRecibos.fxml", "Ver Recibos");
    }

    @FXML
    private void excluir() {
        
    }

    @FXML
    private void atualizar() {
        
    }


    @FXML
    private void pegarItem() {
        System.out.println("Pegar item");
    }

    @FXML
    private void reporItem() {
        System.out.println("Adicionar item");
    }

    @FXML
    private void pedirServico() {
        String servico = cbTipoServico.getValue();
        System.out.println("Serviço escolhido: " + servico);
    }
}