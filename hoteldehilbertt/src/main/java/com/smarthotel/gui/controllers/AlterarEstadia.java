package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class AlterarEstadia extends Transitavel {

    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        AlterarEstadia.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Label lblDataEntrada;

    @FXML
    private Label lblHoraCheckin;

    @FXML
    private Label lblHoraSaida;

    @FXML
    private Spinner<Integer> spnHoras;

    @FXML
    public void initialize() {
        Hospedagem hospedagemDaBusca = BuscarHospedagem.getHospedagemSelecionada();

        if (hospedagemDaBusca != null) {
            setHospedagemSelecionada(hospedagemDaBusca);
        }

        SpinnerValueFactory<Integer> valores =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);
        spnHoras.setValueFactory(valores);

        if (hospedagemSelecionada == null) {
            lblDataEntrada.setText("Sem hospedagem");
            lblHoraCheckin.setText("Sem hospedagem");
            lblHoraSaida.setText("Sem hospedagem");
            return;
        }

        DateTimeFormatter formatoDataHora =
                DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        DateTimeFormatter formatoData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (hospedagemSelecionada.getDataEntrada() != null) {
            lblDataEntrada.setText(
                    hospedagemSelecionada.getDataEntrada().format(formatoData)
            );
        } else {
            lblDataEntrada.setText("Sem data de entrada");
        }

        if (hospedagemSelecionada.getHorarioCheckIn() != null) {
            lblHoraCheckin.setText(
                    hospedagemSelecionada.getHorarioCheckIn().format(formatoDataHora)
            );
        } else {
            lblHoraCheckin.setText("Check-in não realizado");
        }

        if (hospedagemSelecionada.getHorarioSaida() != null) {
            lblHoraSaida.setText(
                    hospedagemSelecionada.getHorarioSaida().format(formatoDataHora)
            );
        } else {
            lblHoraSaida.setText("Sem saída definida");
        }
    }

    @FXML
    public void reduzirEstadia() {
        int horas = spnHoras.getValue();

        IContHospedagens contHosp =
                ControladorHospedagens.getInstance();

        contHosp.diminuirEstadia(
                hospedagemSelecionada,
                horas
        );

        initialize();
    }

    @FXML
    public void extenderEstadia() {
        int horas = spnHoras.getValue();

        IContHospedagens contHosp =
                ControladorHospedagens.getInstance();

        contHosp.aumentarEstadia(
                hospedagemSelecionada,
                horas
        );

        initialize();
    }
}