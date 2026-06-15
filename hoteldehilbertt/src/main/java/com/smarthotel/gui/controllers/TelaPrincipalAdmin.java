package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;

public class TelaPrincipalAdmin extends Transitavel {

    @FXML
    private void abrirAdministrarHospedagens() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmHospedagens.fxml",
                "Administrar Hospedagens"
        );
    }

    @FXML
    private void abrirAdministrarPessoas() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmPessoas.fxml",
                "Administrar Pessoas"
        );
    }

    @FXML
    private void abrirAdministrarQuartos() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmQuarto.fxml",
                "Administrar Quartos"
        );
    }

    @FXML
    private void abrirAdministrarItens() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAdmItens.fxml",
                "Administrar Itens"
        );
    }

    @FXML
    private void abrirAlertas() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaVerAlertas.fxml",
                "Ver Alertas"
        );
    }
}