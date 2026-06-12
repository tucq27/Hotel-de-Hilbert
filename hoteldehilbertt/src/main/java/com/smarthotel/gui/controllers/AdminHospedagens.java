package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminHospedagens extends Transitavel {

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirHospedagemAgora() {
        abrirTela("/com/smarthotel/gui/telas/TelaRealizarHospedagem.fxml", "Realizar Hospedagem");
    }

    @FXML
    private void abrirHospedagemPrevia() {
        abrirTela("/com/smarthotel/gui/telas/TelaRealizarReserva.fxml", "Realizar Reserva");
    }

    @FXML
    private void abrirBuscarHospedagem() {
        abrirTela("/com/smarthotel/gui/telas/TelaBuscarHospedagem.fxml", "Buscar Hospedagem");
    }
}