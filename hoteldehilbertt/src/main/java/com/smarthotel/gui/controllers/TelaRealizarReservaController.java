package com.smarthotel.gui.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.Responsavel;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.ControladorPessoas;
import com.smarthotel.negocios.ControladorQuartos;
import com.smarthotel.negocios.exceptions.CIFException;
import com.smarthotel.negocios.exceptions.CIJRException;
import com.smarthotel.negocios.exceptions.HPException;
import com.smarthotel.negocios.exceptions.QIException;
import com.smarthotel.negocios.exceptions.QLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaRealizarReservaController extends TelaRealizarHospedagemController {
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
    private void confirmarReserva() {
        String cpfResponsavel = txtCpfResponsavel.getText();
        ArrayList<String> cpfsHospedes = new ArrayList<>(listHospedes.getItems());
        String idQuarto = txtIdQuarto.getText();
        LocalDate dataEntrada = dpDataEntrada.getValue();
        LocalDate dataSaida = dpDataSaida.getValue();

        ControladorPessoas controladorPessoas = new ControladorPessoas();
        ControladorQuartos controladorQuartos = new ControladorQuartos();
        ControladorHospedagens controladorHospedagens = new ControladorHospedagens();

        // atribuindo o valor do responsável e dos hóspedes a partir dos CPFs informados
        try {
            Responsavel responsavel = (Responsavel) controladorPessoas.buscarPessoa(cpfResponsavel);
            
            ArrayList<Hospede> hospedes = new ArrayList<>();
            for (String cpfHospede : cpfsHospedes) {
                Pessoa pessoa = controladorPessoas.buscarPessoa(cpfHospede);
                if (pessoa instanceof Hospede) {
                    Hospede h = (Hospede) pessoa;
                    hospedes.add(h);
                }
            }

            Quarto quarto = controladorQuartos.buscarQuarto(idQuarto);

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
            // criando de fato a Reserva
            if (!hospedagemExiste){
                String idHospedagem = controladorHospedagens.reservarHospedagem(quarto, dataEntrada, dataSaida.atTime(12, 0), new ContaHospedagem("conta" + cpfResponsavel, responsavel), hospedes);
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

        } catch (QIException | CIFException | CIJRException | HPException | QLException | ORException e) {
            System.out.println(" - - - - Erro ao realizar reserva: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao realizar reserva: " + e.getMessage());
            alert.showAndWait();
        }
    }
}