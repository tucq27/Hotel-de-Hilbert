package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;

import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class AlterarEstadia extends GerenciarHospedagem{
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
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblDataEntrada.setText(hospedagemSelecionada.getDataEntrada().format(formato2));
        lblHoraCheckin.setText(hospedagemSelecionada.getHorarioCheckIn().format(formato1));
        lblHoraSaida.setText(hospedagemSelecionada.getHorarioSaida().format(formato1));
    }

    @FXML
    public void reduzirEstadia() {
        int horas = spnHoras.getValue();
        IContHospedagens contHosp = ControladorHospedagens.getInstance();

        contHosp.diminuirEstadia(hospedagemSelecionada, horas);
        initialize();
    }

    @FXML
    public void extenderEstadia() {
        int horas = spnHoras.getValue();
        IContHospedagens contHosp = ControladorHospedagens.getInstance();
        
        contHosp.aumentarEstadia(hospedagemSelecionada, horas);
        initialize();
    }
}
