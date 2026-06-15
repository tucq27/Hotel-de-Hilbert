package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.negocios.ControladorPessoas;
import com.smarthotel.negocios.IContPessoas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginUsuario extends Transitavel {

    private static Hospede hospedeLogado;

    @FXML
    private TextField txtCpf;

    public static Hospede getHospedeLogado() {
        return hospedeLogado;
    }

    @FXML
    private void entrar() {

        String cpf = txtCpf.getText();

        if (cpf == null || cpf.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Informe um CPF.");
            alert.showAndWait();
            return;
        }

        try {

            IContPessoas contPessoas = ControladorPessoas.getInstance();

            Pessoa pessoa = contPessoas.buscarPessoa(cpf);

            if (!(pessoa instanceof Hospede)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("O CPF informado não pertence a um hóspede.");
                alert.showAndWait();

                return;
            }

            hospedeLogado = (Hospede) pessoa;

            abrirTela(
                    "/com/smarthotel/gui/telas/PrincipalUsuario.fxml",
                    "Área do Usuário"
            );

        } catch (ONEException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("CPF não encontrado.");
            alert.showAndWait();
        }
    }
}