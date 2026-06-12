package com.smarthotel.gui.controllers;

import java.time.LocalDate;

import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.exceptions.CIFException;
import com.smarthotel.negocios.exceptions.CIJRException;
import com.smarthotel.negocios.exceptions.QIException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConfirmarCheckin extends GerenciarHospedagem {
    
    @FXML
    private Label lblResponsavel;

    @FXML
    private Label lblId;

    @FXML
    private Label lblDataNascimento;

    @FXML
    private TextField txtNome; 

    @FXML
    private TextField txtCpf;
    
    @FXML
    private DatePicker dpDataNascimento; 

    @FXML
    private Button btnVerificarHospede; 

    @FXML
    private Button btnCancelarReserva; 

    @FXML
    private Button btnConfirmar;

    @FXML
    public void initialize() {
        lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
        lblId.setText(hospedagemSelecionada.getId());
    }

    @FXML
    public void verificarHospede(){
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        LocalDate data = dpDataNascimento.getValue();
        IContHospedagens contH = ControladorHospedagens.getInstance();

        boolean esta = contH.verificarHospede(hospedagemSelecionada, nome, cpf, data);

        if (!esta) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Hospede não identificado(a).");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK");
            alert.setContentText("Hospede identificado(a).");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelarReserva(){
        IContHospedagens contH = ControladorHospedagens.getInstance();
        contH.cancelarReserva(hospedagemSelecionada);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OK");
        alert.setContentText("Reserva cancelada.");
        alert.showAndWait();
    }

    @FXML
    public void confirmarCheckin() {
        try{
            IContHospedagens contHosp = ControladorHospedagens.getInstance();
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
}
