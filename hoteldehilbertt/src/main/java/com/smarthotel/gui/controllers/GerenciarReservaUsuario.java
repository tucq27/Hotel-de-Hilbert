package com.smarthotel.gui.controllers;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.StatusHospedagem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class GerenciarReservaUsuario extends GerenciarHospedagemUsuario {

    @FXML
    private void checkIn() {
        Hospedagem reserva = GerenciarHospedagemUsuario.getHospedagemSelecionada();

        if (reserva == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma reserva foi selecionada.");
            return;
        }

        ConfirmarCheckin.setHospedagemSelecionada(reserva);

        abrirTela(
                "/com/smarthotel/gui/telas/TelaConfirmarCheckin.fxml",
                "Confirmar Check-in"
        );
    }

    @FXML
    private void cancelarReserva() {
        Hospedagem reserva = GerenciarHospedagemUsuario.getHospedagemSelecionada();

        if (reserva == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma reserva foi selecionada.");
            return;
        }

        if (reserva.getStatus() != StatusHospedagem.RESERVADA) {
            mostrarAlerta(Alert.AlertType.WARNING, "Cancelar Reserva", "Essa hospedagem não está reservada.");
            return;
        }

        reserva.setStatus(StatusHospedagem.CANCELADA);

        mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Cancelada", "Reserva cancelada com sucesso.");
    }
}