package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.StatusQuarto;
import com.smarthotel.negocios.ControladorQuartos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BuscarQuarto {

    protected static Quarto quartoSelecionado;

    @FXML
    private TextField txtIdQuartos;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVoltar;

    @FXML
    private ListView<String> listQuartosDisp;

    @FXML
    private ListView<String> listQuartosOcu;

    @FXML
    public void initialize() {
        carregarQuartos();
    }

    private void carregarQuartos() {
        ControladorQuartos controladorQuartos = ControladorQuartos.getInstance();

        ArrayList<String> quartosDisponiveis = new ArrayList<>();
        ArrayList<String> quartosOcupados = new ArrayList<>();

        for (Quarto q : controladorQuartos.listarQuartos()) {
            String info = "ID: " + q.getId()
                    + " | Nº: " + q.getNumero()
                    + " | Andar: " + q.getAndar()
                    + " | Capacidade: " + q.getCapacidade()
                    + " | Status: " + q.getStatus();

            if (q.getStatus() == StatusQuarto.DISPONIVEL) {
                quartosDisponiveis.add(info);
            } else {
                quartosOcupados.add(info);
            }
        }

        listQuartosDisp.setItems(FXCollections.observableArrayList(quartosDisponiveis));
        listQuartosOcu.setItems(FXCollections.observableArrayList(quartosOcupados));
    }

    @FXML
    private void buscarQuarto() {
        try {
            ControladorQuartos controladorQuartos = ControladorQuartos.getInstance();

            String idQuarto = txtIdQuartos.getText();

            if (idQuarto == null || idQuarto.isBlank()) {
                mostrarErro("Digite o ID do quarto.");
                return;
            }

            quartoSelecionado = controladorQuartos.buscarQuarto(idQuarto);

            abrirTela(
                    "/com/smarthotel/gui/telas/TelaGerenciarQuarto.fxml",
                    "Gerenciar Quarto"
            );

        } catch (ONEException e) {
            quartoSelecionado = null;
            mostrarErro("Quarto de id " + txtIdQuartos.getText() + " não encontrado.");
        }
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private void abrirTela(String caminhoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}