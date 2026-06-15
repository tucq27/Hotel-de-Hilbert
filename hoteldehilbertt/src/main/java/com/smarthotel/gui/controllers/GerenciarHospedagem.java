package com.smarthotel.gui.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.models.Recibo;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.ControladorPagamentos;
import com.smarthotel.negocios.GeradorPDF;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.IContPagamentos;
import com.smarthotel.negocios.exceptions.CINRException;
import com.smarthotel.negocios.exceptions.COJRException;
import com.smarthotel.negocios.exceptions.DNPException;
import com.smarthotel.negocios.exceptions.SPException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GerenciarHospedagem extends Transitavel {

    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        GerenciarHospedagem.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Button btnVoltar;
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
        setHospedagemSelecionada(BuscarHospedagem.getHospedagemSelecionada());

        if (cbTipoServico != null) {
            cbTipoServico.getItems().clear();
            cbTipoServico.getItems().add("Limpar Quarto");
            cbTipoServico.getItems().add("Levar Comida");
            cbTipoServico.getItems().add("Lavar Roupa");
        }

        if (hospedagemSelecionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma hospedagem foi selecionada.");
            return;
        }

        ArrayList<String> nomesHospedes = new ArrayList<>();

        for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
            nomesHospedes.add(hospede.getNome());
        }

        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
        listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
        lblQuarto.setText(hospedagemSelecionada.getQuarto().getId());
        lblStatus.setText(hospedagemSelecionada.getStatus().toString());
        lblEntrada.setText(hospedagemSelecionada.getDataEntrada().format(formatoData));
        lblSaida.setText(hospedagemSelecionada.getHorarioSaida().format(formatoDataHora));
    }

    @FXML
    private void checkIn() {
        if (hospedagemSelecionada.getHorarioCheckIn() != null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Check-in já realizado.");
        } else {
            abrirTela("/com/smarthotel/gui/telas/TelaConfirmarCheckin.fxml", "Confirmar Check-in");
        }
    }

    @FXML
    private void checkOut() {
        try {
            IContHospedagens contHosp = ControladorHospedagens.getInstance();
            contHosp.checkOut(hospedagemSelecionada);

            GeradorPDF gerador = new GeradorPDF();
            gerador.gerarFaturaPDF(hospedagemSelecionada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Check-out Realizado",
                    "Check-out realizado com sucesso.\nA fatura em PDF foi gerada na pasta relatórios.");

        } catch (CINRException | COJRException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());

        } catch (DNPException | SPException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao realizar check-out ou gerar fatura PDF.");
        }
    }

    @FXML
    private void alterarEstadia() {
        abrirTela("/com/smarthotel/gui/telas/TelaAlterarEstadia.fxml", "Alterar Estadia");
    }

    @FXML
    private void verRecibos() {
        abrirTela("/com/smarthotel/gui/telas/TelaVerRecibos.fxml", "Ver Recibos");
    }

    @FXML
    private void excluir() {
        IContHospedagens contH = ControladorHospedagens.getInstance();

        try {
            contH.removerHospedagem(hospedagemSelecionada.getId());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Hospedagem excluída com sucesso.");
        } catch (ONEException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Hospedagem não encontrada.");
        }
    }

    @FXML
    private void pedirServico() {
        String servico = cbTipoServico.getValue();

        if (servico == null || servico.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um tipo de serviço.");
            return;
        }

        Pessoa pessoaServico = new Pessoa(
                "Funcionário Serviço",
                "00000000000",
                LocalDate.now(),
                "00000000000",
                "servico@hotel.com"
        );

        Funcionario funcionario = new Funcionario(
                pessoaServico,
                "Serviço de Quarto"
        );

        IContPagamentos contPag = ControladorPagamentos.getInstance();

        Recibo recibo = contPag.gerarReciboServico(
                hospedagemSelecionada,
                funcionario,
                servico
        );

        contPag.adicionarRecibo(
                hospedagemSelecionada.getConta(),
                recibo
        );

        mostrarAlerta(Alert.AlertType.INFORMATION, "Serviço solicitado",
                "Serviço solicitado com sucesso!\n" +
                        "Serviço: " + servico + "\n" +
                        "Valor adicionado: R$ " + String.format("%.2f", recibo.getValor()));
    }

    @FXML
    private void abrirFrigobarAdm() {

    }

    @FXML
    private void atualizar() {

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}