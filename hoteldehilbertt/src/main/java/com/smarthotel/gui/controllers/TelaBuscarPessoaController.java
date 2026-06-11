package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaBuscarPessoaController {

    protected static Pessoa pessoaSelecionada;

    @FXML
    private TextField txtCpfPessoa;

    @FXML
    private Button btnVoltar;

    @FXML
    private void buscarPessoa() {
        try {
            String cpf = txtCpfPessoa.getText();

            if (cpf == null || cpf.isBlank()) {
                mostrarErro("Digite o CPF da pessoa.");
                return;
            }

            ControladorPessoas controladorPessoas = ControladorPessoas.getInstance();
            pessoaSelecionada = controladorPessoas.buscarPessoa(cpf);

            if (pessoaSelecionada instanceof Hospede) {
                abrirTela(
                        "/com/smarthotel/gui/telas/TelaGerenciarHospede.fxml",
                        "Gerenciar Hóspede"
                );
            } else if (pessoaSelecionada instanceof Funcionario) {
                abrirTela(
                        "/com/smarthotel/gui/telas/TelaGerenciarFuncionario.fxml",
                        "Gerenciar Funcionário"
                );
            } else {
                mostrarErro("Essa pessoa não é hóspede nem funcionário.");
            }

        } catch (ONEException e) {
            pessoaSelecionada = null;
            mostrarErro("Pessoa não encontrada.");
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