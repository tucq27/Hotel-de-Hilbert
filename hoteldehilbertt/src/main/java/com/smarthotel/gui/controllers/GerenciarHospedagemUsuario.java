package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.exceptions.CINRException;
import com.smarthotel.negocios.exceptions.COJRException;
import com.smarthotel.negocios.exceptions.DNPException;
import com.smarthotel.negocios.exceptions.SPException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GerenciarHospedagemUsuario extends Transitavel {

    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagem) {
        GerenciarHospedagemUsuario.hospedagemSelecionada = hospedagem;
    }

    @FXML
    private Label lblResponsavel;

    @FXML
    private Label lblQuarto;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblEntrada;

    @FXML
    private Label lblSaida;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private ComboBox<String> cbTipoServico;

    @FXML
    public void initialize() {
        if (hospedagemSelecionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma hospedagem foi selecionada.");
            return;
        }

        if (cbTipoServico != null) {
            cbTipoServico.getItems().clear();
            cbTipoServico.getItems().addAll(
                    "Limpar Quarto",
                    "Levar Comida",
                    "Lavar Roupa"
            );
        }

        ArrayList<String> nomesHospedes = new ArrayList<>();

        for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
            nomesHospedes.add(hospede.getNome());
        }

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
        lblQuarto.setText(hospedagemSelecionada.getQuarto().getId());
        lblStatus.setText(hospedagemSelecionada.getStatus().toString());

        if (hospedagemSelecionada.getDataEntrada() != null) {
            lblEntrada.setText(hospedagemSelecionada.getDataEntrada().format(formatoData));
        } else {
            lblEntrada.setText("Sem data");
        }

        if (hospedagemSelecionada.getHorarioSaida() != null) {
            lblSaida.setText(hospedagemSelecionada.getHorarioSaida().format(formatoDataHora));
        } else {
            lblSaida.setText("Sem saída");
        }

        listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
    }

    @FXML
    private void abrirFrigobarUsuario() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaFrigobarUsuario.fxml",
                "Frigobar"
        );
    }

    @FXML
    private void pedirServico() {
        String servico = cbTipoServico.getValue();

        if (servico == null || servico.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um serviço.");
            return;
        }

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Serviço Solicitado",
                "Serviço solicitado com sucesso:\n" + servico
        );
    }

    @FXML
    private void checkOut() {
        try {
            IContHospedagens contHosp = ControladorHospedagens.getInstance();
            contHosp.checkOut(hospedagemSelecionada);

            lblStatus.setText(hospedagemSelecionada.getStatus().toString());

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Check-out Realizado",
                    "Check-out realizado com sucesso."
            );

        } catch (CINRException | COJRException | DNPException | SPException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    @FXML
    private void verRecibos() {
        VerRecibos.setHospedagemSelecionada(hospedagemSelecionada);

        abrirTela(
                "/com/smarthotel/gui/telas/TelaVerRecibos.fxml",
                "Ver Recibos"
        );
    }

    @FXML
    private void alterarEstadia() {
        AlterarEstadia.setHospedagemSelecionada(hospedagemSelecionada);

        abrirTela(
                "/com/smarthotel/gui/telas/TelaAlterarEstadia.fxml",
                "Alterar Estadia"
        );
    }
}