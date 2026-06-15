package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class BuscarReservaUsuario extends Transitavel {

    @FXML
    private ListView<String> listReservas;

    private ArrayList<Hospedagem> reservasDoUsuario = new ArrayList<>();

    @FXML
    public void initialize() {

        Hospede hospedeLogado = LoginUsuario.getHospedeLogado();

        if (hospedeLogado == null) {
            return;
        }

        IContHospedagens contHosp = ControladorHospedagens.getInstance();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ArrayList<String> dadosTela = new ArrayList<>();

        for (Hospedagem h : contHosp.verHospedagensReservadas()) {

            boolean pertenceAoUsuario = false;

            for (Hospede hospede : h.getHospedes()) {

                if (hospede.getCpf().equals(hospedeLogado.getCpf())) {
                    pertenceAoUsuario = true;
                    break;
                }
            }

            if (pertenceAoUsuario) {

                reservasDoUsuario.add(h);

                dadosTela.add(
                        "ID: " + h.getId()
                                + " | Quarto: " + h.getQuarto().getId()
                                + " | Entrada: " + h.getDataEntrada()
                                + " | Saída: " + h.getHorarioSaida().format(formato)
                );
            }
        }

        listReservas.setItems(
                FXCollections.observableArrayList(dadosTela)
        );
    }

    @FXML
    private void gerenciarReserva() {

        int indice = listReservas.getSelectionModel().getSelectedIndex();

        if (indice < 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma reserva.");
            alert.showAndWait();

            return;
        }

        Hospedagem reservaSelecionada =
                reservasDoUsuario.get(indice);

        GerenciarHospedagemUsuario.setHospedagemSelecionada(
                reservaSelecionada
        );

        abrirTela(
                "/com/smarthotel/gui/telas/GerenciarReservaUsuario.fxml",
                "Minha Reserva"
        );
    }
}