package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospede;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.ControladorQuartos;
import com.smarthotel.negocios.GeradorPDF;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.IContQuartos;
import com.smarthotel.negocios.exceptions.CINRException;
import com.smarthotel.negocios.exceptions.COJRException;
import com.smarthotel.negocios.exceptions.LFException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TelaGerenciarHospedagemController extends TelaBuscarHospedagemController{

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
    private TextField txtIdItem;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private ComboBox<String> cbTipoServico;

    @FXML
    public void initialize() {
        if (cbTipoServico != null) {
            cbTipoServico.getItems().add("Limpar Quarto");
            cbTipoServico.getItems().add("Levar Comida");
            cbTipoServico.getItems().add("Lavar Roupa");
        }

        ArrayList<String> nomesHospedes = new ArrayList<>();
        for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
            nomesHospedes.add(hospede.getNome());
        }
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblResponsavel.setText(hospedagemSelecionada.getConta().getResponsavel().getNome());
        if (nomesHospedes != null) {
            listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
        }
        lblQuarto.setText(hospedagemSelecionada.getQuarto().getId());
        lblStatus.setText(hospedagemSelecionada.getStatus().toString());
        lblEntrada.setText(hospedagemSelecionada.getDataEntrada().format(formato2));
        lblSaida.setText(hospedagemSelecionada.getHorarioSaida().format(formato1));
    }

    @FXML
    private void checkIn() {
        if (hospedagemSelecionada.getHorarioCheckIn() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Checkin já realizado");
            alert.showAndWait();
        } else {
            abrirTela("/com/smarthotel/gui/telas/TelaConfirmarCheckin.fxml", "Confirmar Checkin");
        }
    }

    @FXML
    private void gerarFatura() {

        try {

            GeradorPDF gerador = new GeradorPDF();

            gerador.gerarFaturaPDF(hospedagemSelecionada);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fatura Gerada");
            alert.setHeaderText(null);
            alert.setContentText(
                "A fatura PDF foi gerada com sucesso."
            );
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(
                "Erro ao gerar a fatura."
            );
            alert.showAndWait();
        }
    }

    @FXML
    private void gerarRelatorioHospedagem() {

        try {

            GeradorPDF gerador = new GeradorPDF();

            gerador.gerarRelatorioHospedagemPDF(
                    hospedagemSelecionada);

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION);

            alert.setTitle("Relatório Gerado");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Relatório da hospedagem gerado com sucesso."
            );

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(
                    Alert.AlertType.ERROR);

            alert.setTitle("Erro");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Erro ao gerar relatório da hospedagem."
            );

            alert.showAndWait();
        }
    }

    @FXML
    private void checkOut() {

        try {
            IContHospedagens contHosp = ControladorHospedagens.getInstance();
            contHosp.checkOut(hospedagemSelecionada);

            GeradorPDF gerador = new GeradorPDF();
            gerador.gerarFaturaPDF(hospedagemSelecionada);

            System.out.println("Check-out");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check-out Realizado");
            alert.setContentText(
                "Check-out realizado com sucesso para a hospedagem selecionada.\n" +
                "A fatura em PDF foi gerada na pasta relatorios."
            );
            alert.showAndWait();

        } catch (CINRException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (COJRException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao gerar a fatura PDF.");
            alert.showAndWait();
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
        }
        catch (ONEException e) { 
            System.out.println("hospedagem nao encontrada");
        }
    }

    @FXML
    private void pegarItem() {
        IContQuartos contQ = ControladorQuartos.getInstance();

        String idHosp = hospedagemSelecionada.getQuarto().getId();
        String idItem = txtIdItem.getText();

        try {
            contQ.pegarItemFrigobar(hospedagemSelecionada, idHosp, idItem);
        } catch(ONEException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Item não encontrado no frigobar");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setContentText("item removido");
            alert.showAndWait();
    }

    @FXML
    private void reporItem() {
        IContQuartos contQ = ControladorQuartos.getInstance();

        String idHosp = hospedagemSelecionada.getQuarto().getId();
        String idItem = txtIdItem.getText();
        try {
            contQ.reporItemFrigobar(idHosp, idItem);
        } catch(ONEException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Item não registrado");
            alert.showAndWait();
        } catch (LFException l) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro: " + l);
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setContentText("item adicionado");
            alert.showAndWait();
    }

    @FXML
    private void pedirServico() {
        String servico = cbTipoServico.getValue();
        System.out.println("Serviço escolhido: " + servico);
    }
}