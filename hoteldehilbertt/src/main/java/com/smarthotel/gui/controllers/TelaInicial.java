package com.smarthotel.gui.controllers;

import java.io.IOException;

import javafx.fxml.FXML;

public class TelaInicial extends Transitavel {

    @FXML
    private void abrirAdministracao() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaPrincipalAdministrador.fxml",
                "Painel Administrativo"
        );
    }

    @FXML
    private void abrirUsuario() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaLoginUsuario.fxml",
                "Área do Usuário"
        );
    }
}