package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TelaAlterarEstadiaController extends TelaGerenciarHospedagemController{
    @FXML
    private Label lblDataEntrada;
    @FXML
    private Label lblHoraCheckin;
    @FXML
    private Label lblHoraSaida;
    @FXML
    private Spinner<Integer> spnHoras;

    @FXML
    private Button btnReduzirEstadia;
    @FXML
    private Button btnExtenderEstadia;
    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valores = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);
            spnHoras.setValueFactory(valores);
    }

    @FXML
    public void reduzirEstadia() {

    }

    @FXML
    public void extenderEstadia() {
        
    }
}
