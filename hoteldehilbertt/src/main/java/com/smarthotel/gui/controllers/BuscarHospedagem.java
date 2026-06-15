package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BuscarHospedagem extends Transitavel {

    protected static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        BuscarHospedagem.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txtIdHospedagem;

    // informações da hospedagem selecionada que serão exibidas
    @FXML
    private ListView<String> listHospAtivas;
    @FXML
    private ListView<String> listHospReservadas;
    
    @FXML
    public void initialize() {
        IContHospedagens contHosp = ControladorHospedagens.getInstance();
        ArrayList<String> hospAtivas = new ArrayList<>();
        ArrayList<String> hospReservadas = new ArrayList<>();
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Hospedagem h : contHosp.verHospedagensAtivas()) {
            String saida = h.getHorarioSaida().format(formato1);
            hospAtivas.add(" Id: " + h.getId() + "  |  Quarto: " + h.getQuarto().getId() + "  |  Saída: " + saida);
        }
        for (Hospedagem h : contHosp.verHospedagensReservadas()) {
            String entrada = h.getDataEntrada().format(formato2);
            hospReservadas.add(" Id: " + h.getId() + "  |  Quarto: " + h.getQuarto() + "  |  Entrada: " + entrada);
        }

        listHospAtivas.setItems(FXCollections.observableArrayList(hospAtivas));
        listHospReservadas.setItems(FXCollections.observableArrayList(hospReservadas));
    }

    @FXML
    private void buscarHospedagem() throws ONEException {
        try{
            IContHospedagens controladorHospedagens = ControladorHospedagens.getInstance();
            String idHospedagem = txtIdHospedagem.getText();
            hospedagemSelecionada = controladorHospedagens.buscarHospedagem(idHospedagem);
            
            abrirTela("/com/smarthotel/gui/telas/TelaGerenciarHospedagem.fxml", "Gerenciar Hospedagem");
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
}