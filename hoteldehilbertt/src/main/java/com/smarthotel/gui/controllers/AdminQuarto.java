package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.GeradorCSV;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class AdminQuarto extends Transitavel {

    @FXML
    private Button btnCadastrarQuarto;
    @FXML
    private Button btnBuscarQuarto;
    @FXML
    private Button btnAlterarTaxa;
    @FXML
    private Button btnRelatorioFaturamento;

    @FXML
    private Button btnVoltar;

    @FXML
    private void abrirCadastrarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaCadastrarQuarto.fxml",
                "Cadastrar Quarto"
        );
    }

    @FXML
    private void abrirBuscarQuarto() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaBuscarQuarto.fxml",
                "Buscar Quarto"
        );
    }

    @FXML
    private void abrirAlterarTaxa() {
        abrirTela(
                "/com/smarthotel/gui/telas/TelaAlterarTaxa.fxml",
                "Alterar Taxas"
        );
    }

    @FXML
    private void gerarRelatorioFaturamento() {

        try {

            GeradorCSV gerador = new GeradorCSV();

            ArrayList<Hospedagem> hospedagens =
                    ControladorHospedagens
                            .getInstance()
                            .getRepositorioHospedagens()
                            .getObjetos();

            gerador.gerarRelatorioGeralCSV(
                    hospedagens
            );

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("Relatório Gerado");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Relatório CSV gerado com sucesso na pasta relatorios."
            );

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(
                    Alert.AlertType.ERROR
            );

            alert.setTitle("Erro");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Erro ao gerar relatório CSV."
            );

            alert.showAndWait();
        }
    }
        
}