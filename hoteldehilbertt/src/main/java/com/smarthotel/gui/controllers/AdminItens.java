package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminItens extends Transitavel {

    @FXML
    private Button btnCadastrarItem;

    @FXML
    private Button btnBuscarItem;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarItem() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarItem.fxml",
                "Cadastrar Item"
        );
    }

    @FXML
    private void abrirBuscarItem() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarItem.fxml",
                "Buscar Item"
        );
    }
}