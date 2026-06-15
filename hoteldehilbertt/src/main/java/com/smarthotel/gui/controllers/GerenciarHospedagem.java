package com.smarthotel.gui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.models.Recibo;
import com.smarthotel.models.StatusHospedagem;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.ControladorPagamentos;
import com.smarthotel.negocios.ControladorPessoas;
import com.smarthotel.negocios.GeradorPDF;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.IContPagamentos;
import com.smarthotel.negocios.IContPessoas;
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
import javafx.scene.control.TextField;

public class GerenciarHospedagem extends Transitavel {

    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        GerenciarHospedagem.hospedagemSelecionada = hospedagemSelecionada;
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
    private TextField txtCpfFuncionario;

    @FXML
    public void initialize() {
        Hospedagem hospedagemDaBusca = BuscarHospedagem.getHospedagemSelecionada();

        if (hospedagemDaBusca != null) {
            setHospedagemSelecionada(hospedagemDaBusca);
        }

        if (cbTipoServico != null) {
            cbTipoServico.getItems().clear();
            cbTipoServico.getItems().addAll(
                    "Limpar Quarto",
                    "Levar Comida",
                    "Lavar Roupa"
            );
        }

        if (hospedagemSelecionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhuma hospedagem foi selecionada.");
            return;
        }

        IContPagamentos contP = ControladorPagamentos.getInstance();
        contP.verificarDiariaAtrasada(hospedagemSelecionada);

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
            ConfirmarCheckin.setHospedagemSelecionada(hospedagemSelecionada);

            abrirTela("/com/smarthotel/gui/telas/TelaConfirmarCheckin.fxml", "Confirmar Check-in");
        }
    }

    @FXML
    private void checkOut() {
        try {
            IContHospedagens contHosp = ControladorHospedagens.getInstance();
            contHosp.checkOut(hospedagemSelecionada);

            GeradorPDF gerador = new GeradorPDF();
            gerador.gerarFaturaPDF(hospedagemSelecionada, "relatorios/fatura.pdf");

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Check-out Realizado",
                    "Check-out realizado com sucesso.\nA fatura em PDF foi gerada na pasta relatórios."
            );

        } catch (CINRException | COJRException | DNPException | SPException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao realizar check-out ou gerar fatura PDF.");
        }
    }

    @FXML
    private void alterarEstadia() {
        AlterarEstadia.setHospedagemSelecionada(hospedagemSelecionada);

        abrirTela(
                "/com/smarthotel/gui/telas/TelaAlterarEstadia.fxml",
                "Alterar Estadia"
        );
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
        String cpfFuncionario = txtCpfFuncionario.getText();

        if (servico == null || servico.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um tipo de serviço.");
            return;
        }

        if (cpfFuncionario == null || cpfFuncionario.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Informe o CPF do funcionário.");
            return;
        }

        try {
            IContPessoas contPessoas = ControladorPessoas.getInstance();

            Pessoa pessoa = contPessoas.buscarPessoa(cpfFuncionario.trim());

            if (!(pessoa instanceof Funcionario)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "O CPF informado não pertence a um funcionário.");
                return;
            }

            Funcionario funcionario = (Funcionario) pessoa;

            if (funcionario.isOcupado()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Funcionário ocupado", "Este funcionário já está ocupado.");
                return;
            }

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

            funcionario.setOcupado(true);

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Serviço solicitado",
                    "Serviço solicitado com sucesso!\n" +
                            "Funcionário: " + funcionario.getNome() + "\n" +
                            "Serviço: " + servico + "\n" +
                            "Valor adicionado: R$ " + String.format("%.2f", recibo.getValor())
            );

            txtCpfFuncionario.clear();

        } catch (ONEException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    @FXML
    private void abrirFrigobarAdm() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaFrigobarAdmin.fxml",
                "Gerenciar Frigobar"
        );
    }

    @FXML
    private void atualizar() {
        initialize();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Atualizado", "Dados da hospedagem atualizados.");
    }

    @FXML
    private void cancelarReserva() {
        if (hospedagemSelecionada == null) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Nenhuma hospedagem foi selecionada."
            );
            return;
        }

        if (hospedagemSelecionada.getStatus() != StatusHospedagem.RESERVADA) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Cancelar Reserva",
                    "Essa ação só funciona para hospedagens com status RESERVADA."
            );
            return;
        }

        hospedagemSelecionada.setStatus(StatusHospedagem.CANCELADA);
        lblStatus.setText(hospedagemSelecionada.getStatus().toString());

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Reserva Cancelada",
                "A reserva foi cancelada com sucesso."
        );
    }
}