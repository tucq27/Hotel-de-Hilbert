package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import com.smarthotel.negocios.*;
import com.smarthotel.negocios.exceptions.QIException;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.*;

public class TelaRealizarHospedagemController {

    @FXML
    private TextField txtCpfResponsavel;

    @FXML
    private TextField txtCpfHospede;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private TextField txtIdQuarto;

    @FXML
    private DatePicker dpDataSaida;


    @FXML
    private void adicionarHospede() {
        String cpf = txtCpfHospede.getText();

        if (cpf != null && !cpf.trim().isEmpty()) {
            listHospedes.getItems().add(cpf);
            txtCpfHospede.clear();
        }
    }

    @FXML
    private void confirmarHospedagem() {
        String cpfResponsavel = txtCpfResponsavel.getText();
        ArrayList<String> cpfsHospedes = new ArrayList<>(listHospedes.getItems());
        String idQuarto = txtIdQuarto.getText();
        LocalDate dataSaida = dpDataSaida.getValue();

        ControladorPessoas controladorPessoas = new ControladorPessoas();
        ControladorQuartos controladorQuartos = new ControladorQuartos();
        ControladorHospedagens controladorHospedagens = new ControladorHospedagens();

        // atribuindo o valor do responsável e dos hóspedes a partir dos CPFs informados
        try {
            Responsavel responsavel = (Responsavel) controladorPessoas.buscarPessoa(cpfResponsavel);
            
            ArrayList<Hospede> hospedes = new ArrayList<>();
            for (String cpfHospede : cpfsHospedes) {
                Hospede pessoa = (Hospede) controladorPessoas.buscarPessoa(cpfHospede);
                if (pessoa instanceof Hospede) {
                    hospedes.add((Hospede) pessoa);
                }
            }

            Quarto quarto = controladorQuartos.buscarQuarto(idQuarto);

            if (dataSaida == null) {
                System.out.println(" - - - - Erro: Data de saída não selecionada.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Data de saída não selecionada.");
                alert.showAndWait();
                return;
            }

            // criando de fato a hospedagem, utilizando o método de check-in imediato
            controladorHospedagens.hospedarAgora(quarto, dataSaida.atStartOfDay(), new ContaHospedagem("conta" + cpfResponsavel, responsavel), hospedes);

        } catch (ONEException ne) {
            System.out.println(" - - - - Erro: " + ne.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(ne.getMessage());
            alert.showAndWait();
            
        } catch (QIException | CIFException | CIJRException | HPException | QLException | ORException e) {
            System.out.println(" - - - - Erro ao realizar hospedagem: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao realizar hospedagem: " + e.getMessage());
            alert.showAndWait();
        }

        

        System.out.println("Hospedagem confirmada!");
        System.out.println("Responsável: " + cpfResponsavel);
        System.out.println("Hóspedes: " + cpfsHospedes);
        System.out.println("ID do Quarto: " + idQuarto);
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) listHospedes.getScene().getWindow();
        stage.close();
    }
}