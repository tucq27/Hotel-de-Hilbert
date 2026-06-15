package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PrincipalUsuario extends Transitavel {

    @FXML
    private void realizarReserva() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaRealizarReserva.fxml",
                "Realizar Reserva"
        );
    }

    @FXML
    private void buscarReserva() {
        abrirTela(
                "/com/smarthotel/gui/telas/BuscarReservaUsuario.fxml",
                "Minhas Reservas"
        );
    }

    @FXML
    private void verHospedagemAtual() {

        Hospede hospedeLogado = LoginUsuario.getHospedeLogado();

        if (hospedeLogado == null) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Nenhum hóspede está logado."
            );
            return;
        }

        IContHospedagens contHosp =
                ControladorHospedagens.getInstance();

        ArrayList<Hospedagem> hospedagensAtivas =
                contHosp.verHospedagensAtivas();

        Hospedagem hospedagemEncontrada =
                buscarHospedagemPorCpf(
                        hospedagensAtivas,
                        hospedeLogado.getCpf()
                );

        if (hospedagemEncontrada == null) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Hospedagem Atual",
                    "Nenhuma hospedagem ativa encontrada."
            );

            return;
        }

        GerenciarHospedagemUsuario.setHospedagemSelecionada(
                hospedagemEncontrada
        );

        abrirTela(
                "/com/smarthotel/gui/telas/GerenciarHospedagemUsuario.fxml",
                "Minha Hospedagem"
        );
    }

    private Hospedagem buscarHospedagemPorCpf(
            ArrayList<Hospedagem> hospedagens,
            String cpf
    ) {

        for (Hospedagem hospedagem : hospedagens) {

            for (Hospede hospede : hospedagem.getHospedes()) {

                if (hospede.getCpf().equals(cpf)) {
                    return hospedagem;
                }
            }
        }

        return null;
    }
}