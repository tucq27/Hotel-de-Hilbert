package com.smarthotel.gui.controllers;

import java.io.File;
import java.util.ArrayList;

import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Recibo;
import com.smarthotel.models.TipoRecibo;
import com.smarthotel.negocios.ControladorPagamentos;
import com.smarthotel.negocios.GeradorPDF;
import com.smarthotel.negocios.IContPagamentos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
<<<<<<< HEAD
import javafx.stage.FileChooser;
=======
import javafx.scene.control.Alert.AlertType;
>>>>>>> 6e4b87c64e1698ecc29bbc3d13d639d6e7c2fcd4

public class VerRecibos extends Transitavel {
    
    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        VerRecibos.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnGerarFatura;
    @FXML
    private Button btnPagamento;
    @FXML
    private Button btnPagarGrupo;
    @FXML
    private Button btnGerarRecibo;
    @FXML
    private Button btnGerarRelatorio;

    @FXML
    protected Label lblResponsavel;
    @FXML
    protected Label lblDadosPagamento;
    @FXML
    protected Label lblSaldoPago;
    @FXML
    protected Label lblSaldoPendente;
    @FXML
    protected Label lblValorDiaria;
    @FXML
    protected ListView<String> listRecibos;

    @FXML
    public void initialize() {
<<<<<<< HEAD

        System.out.println("ENTROU NO INITIALIZE DE VER RECIBOS");

        setHospedagemSelecionada(BuscarHospedagem.getHospedagemSelecionada());
=======
        Hospedagem hospedagemDaBusca = BuscarHospedagem.getHospedagemSelecionada();

        if (hospedagemDaBusca != null) {
            setHospedagemSelecionada(hospedagemDaBusca);
        }
>>>>>>> 6e4b87c64e1698ecc29bbc3d13d639d6e7c2fcd4

        double dividaPendente = hospedagemSelecionada.getConta().getSaldoPendente();
        double saldoPago = hospedagemSelecionada.getConta().getSaldoPago();
        ArrayList<String> recibos = new ArrayList<>(); 

        for (Recibo r : hospedagemSelecionada.getConta().getRecibos()) {
            double valor = r.getValor();
            recibos.add(r.getId() + "  |  " + r.getTipo() + "  |  " + String.format("RS %.2f", valor));
        }

        lblResponsavel.setText( hospedagemSelecionada.getConta().getResponsavel().getNome() );
        lblSaldoPendente.setText(String.format("RS %.2f", dividaPendente));
        lblSaldoPago.setText(String.format("RS %.2f", saldoPago));
        listRecibos.setItems(FXCollections.observableArrayList(recibos));
        lblDadosPagamento.setText("Não informados");
        
        String dadosPagamento = hospedagemSelecionada.getConta().getDadosPagamento();
        if (dadosPagamento != null) {
            lblDadosPagamento.setText(dadosPagamento);
        }
    }

    @FXML
    private void gerarFatura() {

        try {

            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Salvar Fatura");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Arquivos PDF",
                            "*.pdf"));

            fileChooser.setInitialFileName(
                    "fatura_" +
                    hospedagemSelecionada.getId() +
                    ".pdf");

            File arquivo = fileChooser.showSaveDialog(
                    btnVoltar.getScene().getWindow());

            if (arquivo == null) {
                return;
            }

            GeradorPDF gerador = new GeradorPDF();

            gerador.gerarFaturaPDF(
                    hospedagemSelecionada,
                    arquivo.getAbsolutePath());

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Fatura Gerada",
                    "A fatura PDF foi gerada com sucesso.");

        } catch (Exception e) {

            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao gerar a fatura.");
        }
    }

    @FXML
    private void gerarRelatorio() {

        try {

            GeradorPDF gerador = new GeradorPDF();

            gerador.gerarRelatorioHospedagemPDF(
                    hospedagemSelecionada,
                    "relatorios/relatorio_hospedagem.pdf"
            );

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Relatório Gerado",
                    "Relatório da hospedagem gerado com sucesso."
            );

        } catch (Exception e) {

            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao gerar relatório da hospedagem."
            );
        }
    }

    @FXML
    private void pagarGrupo() {
        if (hospedagemSelecionada.getConta().getSaldoPendente() > 0) {
            IContPagamentos contP = ControladorPagamentos.getInstance();
            contP.pagarDividaGrupo(hospedagemSelecionada);
            mostrarAlerta(AlertType.CONFIRMATION, null, "Pagamento de grupo bem sucedido");
        } else if (hospedagemSelecionada.isDiariaPaga() == false) {
            mostrarAlerta(AlertType.INFORMATION, null, "Diaria não paga para atual Hospedagem");
        } else {
            mostrarAlerta(AlertType.INFORMATION, null, "Dívida paga para atual Hospedagem");
        }
        initialize();
    }

    @FXML
    private void realizarPagamento() {
        if (hospedagemSelecionada.getConta().getSaldoPendente() > 0) {
            IContPagamentos contP = ControladorPagamentos.getInstance();
            contP.pagarDivida(hospedagemSelecionada);
            mostrarAlerta(AlertType.CONFIRMATION, null, "Pagamento bem sucedido");
        } else if (hospedagemSelecionada.isDiariaPaga() == false) {
            mostrarAlerta(AlertType.INFORMATION, null, "Diaria não paga");
        } else {
            mostrarAlerta(AlertType.INFORMATION, null, "Dívida já paga");
        }
        initialize();
    }

    @FXML
    private void gerarRecibo() {

<<<<<<< HEAD
=======
        ContaHospedagem conta = hospedagemSelecionada.getConta();

        for (Recibo r : conta.getRecibos()) {
            if (r.getTipo() == TipoRecibo.DIARIA) {
                mostrarAlerta(AlertType.ERROR,null, "Recibo de diária já foi gerado.");
                return;
            }
        }

        IContPagamentos contP = ControladorPagamentos.getInstance();

        Recibo diaria = contP.gerarReciboDiaria(hospedagemSelecionada);
        contP.adicionarRecibo(conta, diaria);

        initialize();
>>>>>>> 6e4b87c64e1698ecc29bbc3d13d639d6e7c2fcd4
    }
}

