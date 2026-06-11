package com.smarthotel.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.negocios.*;


public class TelaVerAlertasController {

    @FXML
    private ListView<String> listCheckPen;

    @FXML
    private ListView<String> listCheckRe;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {

        IContRelatorios relatorios = ControladorRelatorios.getInstance();

        ArrayList<String> pendente = new ArrayList<>();
        ArrayList<String> saidasRealizadas = new ArrayList<>();
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        if (!(relatorios.alertarSaidaPendente() == null || relatorios.alertarSaidaPendente().isEmpty())){
            for (Hospedagem h : relatorios.alertarSaidaPendente()) {
                String saida = h.getHorarioSaida().format(formato1);
                pendente.add(" Id: " + h.getId() + "  |  Quarto: " + h.getQuarto().getId() + "  |  Saída: " + saida);
            }
        } 
        
        if (! (relatorios.gerarRelatorioSaidas() == null || relatorios.gerarRelatorioSaidas().isEmpty())) {
            for (Hospedagem h : relatorios.gerarRelatorioSaidas()) {
                String saida = h.getHorarioSaida().format(formato1);
                saidasRealizadas.add(" Id: " + h.getId() + "  |  Quarto: " + h.getQuarto().getId() + "  |  Saída: " + saida);
            }
        }
        
        
        listCheckPen.setItems(FXCollections.observableArrayList(pendente));
        listCheckRe.setItems(FXCollections.observableArrayList(saidasRealizadas));
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}