package com.smarthotel.gui.controllers;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.negocios.ControladorPessoas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BuscarPessoa extends Transitavel {

    private static Pessoa pessoaSelecionada;

    public static Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public static void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        BuscarPessoa.pessoaSelecionada = pessoaSelecionada;
    }

    @FXML
    private TextField txtCpfPessoa;

    @FXML
    private ListView<String> listHospedes;

    @FXML
    private ListView<String> listFuncionarios;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        carregarPessoas();
    }

    private void carregarPessoas() {
        ControladorPessoas controladorPessoas = ControladorPessoas.getInstance();

        ArrayList<String> hospedes = new ArrayList<>();
        ArrayList<String> funcionarios = new ArrayList<>();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Pessoa p : controladorPessoas.listarHospedes()) {
            hospedes.add(
                    "CPF: " + p.getCpf()
                            + " | Nome: " + p.getNome()
                            + " | Nasc.: " + p.getDataNascimento().format(formato)
            );
        }

        for (Pessoa p : controladorPessoas.listarFuncionarios()) {
            funcionarios.add(
                    "CPF: " + p.getCpf()
                            + " | Nome: " + p.getNome()
                            + " | Nasc.: " + p.getDataNascimento().format(formato)
            );
        }

        listHospedes.setItems(FXCollections.observableArrayList(hospedes));
        listFuncionarios.setItems(FXCollections.observableArrayList(funcionarios));
    }

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
                mostrarErro("Essa pessoa está cadastrada, mas ainda não é hóspede nem funcionário.");
            }

        } catch (ONEException e) {
            pessoaSelecionada = null;
            mostrarErro("Pessoa não encontrada.");
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