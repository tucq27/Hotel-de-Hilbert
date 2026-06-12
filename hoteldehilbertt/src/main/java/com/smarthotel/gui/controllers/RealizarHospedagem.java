package com.smarthotel.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import com.smarthotel.negocios.*;
import com.smarthotel.negocios.exceptions.*;
import com.smarthotel.dados.exceptions.*;
import com.smarthotel.models.*;

public class RealizarHospedagem extends Transitavel {

    @FXML
    protected TextField txtCpfResponsavel;

    @FXML
    protected TextField txtCpfHospede;

    @FXML
    protected ListView<String> listHospedes;

    @FXML
    protected TextField txtIdQuarto;

    @FXML
    protected TextField txtPagamento;

    @FXML
    protected DatePicker dpDataSaida;


    @FXML
    protected void adicionarHospede() {
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
        String pagamento = txtPagamento.getText();
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

            if (pagamento == null) {
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

            Quarto quarto = controladorQuartos.buscarQuarto(idQuarto);

            if (dataSaida == null || dataSaida.isBefore(LocalDate.now().plusDays(1))) {
                System.out.println(" - - - - Erro: Data de saída invalida.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Data de saída inválida.");
                alert.showAndWait();
                return;
            }

            boolean hospedagemExiste = controladorHospedagens.hospedagmJaExiste(dataSaida, quarto);

            // criando de fato a hospedagem, utilizando o método de check-in imediato
            if (!hospedagemExiste){

                String idHospedagem = controladorHospedagens.hospedarAgora(quarto, dataSaida.atTime(12, 0), new ContaHospedagem("conta" + cpfResponsavel, responsavel, pagamento), hospedes);
                System.out.println("Hospedagem confirmada!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hospedagem Confirmada");
                alert.setContentText("O id da hospedagem é: " + idHospedagem);
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
            System.out.println(" - - - - Erro ao realizar hospedagem: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao realizar hospedagem: " + e.getMessage());
            alert.showAndWait();
        }
    }
}