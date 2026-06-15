package com.smarthotel.gui.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.negocios.*;
import com.smarthotel.negocios.exceptions.*;
import com.smarthotel.dados.exceptions.*;
import com.smarthotel.models.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

public class RealizarReserva extends RealizarHospedagem {
    /* 
    @FXML
    private TextField txtCpfHospede;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private Button btnVoltar;

    @FXML
    private void adicionarHospede() {
        String cpf = txtCpfHospede.getText();

        if (cpf != null && !cpf.trim().isEmpty()) {
            listHospedes.getItems().add(cpf);
            txtCpfHospede.clear();
        }
    }

    @FXML
    private void confirmarReserva() {
        System.out.println("Reserva confirmada!");
    }

    @FXML
    private void voltar() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
    */

    @FXML
    private DatePicker dpDataEntrada;

    @FXML
    private void calcularValor() {
        String idQuarto = txtIdQuarto.getText();
        LocalDate dataSaida = dpDataSaida.getValue();
        LocalDate dataEntrada = dpDataEntrada.getValue();

        IContQuartos controladorQuartos = ControladorQuartos.getInstance();
        IContPagamentos contP = ControladorPagamentos.getInstance();

        try {
            Quarto quarto = controladorQuartos.buscarQuarto(idQuarto);
            double valor = contP.calcularValor(dataEntrada, dataSaida, quarto.getMultTaxa());

            lblValorEstimado.setText("R$ " + String.format("%.2f", valor));
        } catch (ONEException e) {
            mostrarAlerta(AlertType.ERROR, idQuarto, "Quarto não encontrado");
        }
    }

    @FXML
    private void confirmarReserva() {
        String cpfResponsavel = txtCpfResponsavel.getText();
        ArrayList<String> cpfsHospedes = new ArrayList<>(listHospedes.getItems());
        String idQuarto = txtIdQuarto.getText();
        String pagamento = txtPagamento.getText();
        LocalDate dataEntrada = dpDataEntrada.getValue();
        LocalDate dataSaida = dpDataSaida.getValue();

        IContPessoas controladorPessoas = ControladorPessoas.getInstance();
        IContQuartos controladorQuartos = ControladorQuartos.getInstance();
        IContHospedagens controladorHospedagens = ControladorHospedagens.getInstance();

        // atribuindo o valor do responsável e dos hóspedes a partir dos CPFs informados
        try {
            Pessoa responsavel = controladorPessoas.buscarPessoa(cpfResponsavel);
            
            ArrayList<Hospede> hospedes = new ArrayList<>();
            for (String cpfHospede : cpfsHospedes) {
                Pessoa pessoa = controladorPessoas.buscarPessoa(cpfHospede);
                if (pessoa instanceof Hospede) {
                    Hospede h = (Hospede) pessoa;
                    hospedes.add(h);
                }
            }

            Quarto quarto = controladorQuartos.buscarQuarto(idQuarto);

            
            if (pagamento == null || pagamento.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Insira dados de pagamento.");
                alert.showAndWait();
                return;
            }

            if (hospedes == null || hospedes.isEmpty()) {
                System.out.println(" - - - - Erro: Nenhum hóspede válido adicionado.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Nenhum hóspede válido adicionado.");
                alert.showAndWait();
                return;
            }

            if (dataSaida == null || dataSaida.isBefore(LocalDate.now().plusDays(1))) {
                System.out.println(" - - - - Erro: Data de saída invalida.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Data de saída inválida.");
                alert.showAndWait();
                return;
            }

            if (dataEntrada == null || dataEntrada.isBefore(LocalDate.now())) {
                System.out.println(" - - - - Erro: Data de entrada invalida.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Data de entrada inválida.");
                alert.showAndWait();
                return;
            }

            boolean hospedagemExiste = controladorHospedagens.hospedagmJaExiste(dataSaida, quarto);

            if (!hospedagemExiste){

                //------> criando de fato a Reserva

                String idHospedagem = controladorHospedagens.reservarHospedagem(quarto, dataEntrada, dataSaida.atTime(12, 0), new ContaHospedagem("conta" + cpfResponsavel, responsavel, pagamento), hospedes);
                System.out.println("Reserva confirmada!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reserva Confirmada");
                alert.setContentText("O id da reserva é: " + idHospedagem);
                alert.showAndWait();
            } else {
                System.out.println(" - - - - Erro: Já existe uma hospedagem para esse quarto na data de saída.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Já existe uma hospedagem para esse quarto na data de saída.");
                alert.showAndWait();
                return;
            }

        } catch (ONEException ne) {
            System.out.println(" - - - - Erro: " + ne.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(ne.getMessage());
            alert.showAndWait();

        } catch (QJRException | CIFException | CIJRException | HPException | QLException | ORException e) {
            System.out.println(" - - - - Erro ao realizar reserva: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao realizar reserva: " + e.getMessage());
            alert.showAndWait();
        }
    }
}