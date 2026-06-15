package com.smarthotel.gui.controllers;

import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Hospede;
import com.smarthotel.models.Pessoa;
import com.smarthotel.models.Quarto;
import com.smarthotel.negocios.ControladorHospedagens;
import com.smarthotel.negocios.ControladorPessoas;
import com.smarthotel.negocios.ControladorQuartos;
import com.smarthotel.negocios.IContHospedagens;
import com.smarthotel.negocios.IContPessoas;
import com.smarthotel.negocios.IContQuartos;
import com.smarthotel.negocios.exceptions.HNEException;
import com.smarthotel.negocios.exceptions.QIException;
import com.smarthotel.negocios.exceptions.QLException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AtualizarHospedagem extends Transitavel{

    private static Hospedagem hospedagemSelecionada;

    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }

    public static void setHospedagemSelecionada(Hospedagem hospedagemSelecionada) {
        AtualizarHospedagem.hospedagemSelecionada = hospedagemSelecionada;
    }

    @FXML
    private Label lblResponsavel;
    @FXML
    private Label lblQuarto;
    @FXML
    private ListView<String> listHospedes;

    @FXML
    private TextField txtIdConta;
    @FXML
    private TextField txtIdQuarto;
    @FXML
    private TextField txtCpfHospede;

    @FXML
    public void initialize() {
        setHospedagemSelecionada(BuscarHospedagem.getHospedagemSelecionada());

        Pessoa resp = hospedagemSelecionada.getConta().getResponsavel();
        Quarto quarto = hospedagemSelecionada.getQuarto();
        ArrayList<String> nomesHospedes = new ArrayList<>();

        for (Hospede hospede : hospedagemSelecionada.getHospedes()) {
            nomesHospedes.add(hospede.getNome());
        }

        lblResponsavel.setText(resp.getNome() + " | " + resp.getCpf());
        lblQuarto.setText(quarto.getId() + " | " + quarto.getTipo());
        listHospedes.setItems(FXCollections.observableArrayList(nomesHospedes));
    }

    @FXML
    public void adicionarHospede() {
        try{
            IContHospedagens contH = ControladorHospedagens.getInstance();
            IContPessoas contPessoas = ControladorPessoas.getInstance();
            String cpf = txtCpfHospede.getText();
            Pessoa hosp = contPessoas.buscarPessoa(cpf);
            
            if (!(hosp instanceof Hospede)) {
                mostrarAlerta(AlertType.ERROR, null, "Hospede não registrado(a)");
            }

            contH.adicionarHospede(hospedagemSelecionada, (Hospede) hosp);

            initialize();
            
        } catch (ONEException e) {
            mostrarAlerta(AlertType.ERROR, "erro", "Pessoa não encontrada");
        } catch (QLException e) {
            mostrarAlerta(AlertType.ERROR, null, e.getMessage());
        }
    }

    @FXML
    public void removerHospede() {
        try {
            IContHospedagens contH = ControladorHospedagens.getInstance();
            IContPessoas contPes = ControladorPessoas.getInstance();
            String cpf = txtCpfHospede.getText();

            Pessoa hosp = contPes.buscarPessoa(cpf);
            contH.removerHospede(hospedagemSelecionada, (Hospede) hosp);
            mostrarAlerta(AlertType.INFORMATION,"Sucesso","Hóspede removido.");

            mostrarAlerta(AlertType.ERROR,"Erro","Hóspede não encontrado nesta hospedagem."
            );
            
            initialize();

        } catch (HNEException e) {
            mostrarAlerta(AlertType.ERROR,"Erro",e.getMessage());
        } catch (ONEException e) {
            mostrarAlerta(AlertType.ERROR,"Erro", "Pessoa não encontrada");
        }
    }

    @FXML
    public void trocarResp() {
        try {
            IContHospedagens contH = ControladorHospedagens.getInstance();
            String idConta = txtIdConta.getText();
            contH.trocarContaPorId(hospedagemSelecionada, idConta);

            Pessoa novoResp = hospedagemSelecionada.getConta().getResponsavel();
            lblResponsavel.setText(novoResp.getNome() + " | " + novoResp.getCpf());

            mostrarAlerta(AlertType.INFORMATION,"Sucesso","Responsável alterado.");

            initialize();

        } catch (ONEException e) {
            mostrarAlerta(AlertType.ERROR,"Erro",e.getMessage());
        }
    }

    @FXML
    public void trocarQuarto() {
        try {
            IContHospedagens contH = ControladorHospedagens.getInstance();
            IContQuartos contQ = ControladorQuartos.getInstance(); 
            String idQuarto = txtIdQuarto.getText();
            Quarto novoQuarto = contQ.buscarQuarto(idQuarto);

            contH.trocarQuarto(hospedagemSelecionada, novoQuarto);

            Pessoa novoResp = hospedagemSelecionada.getConta().getResponsavel();
            lblResponsavel.setText(novoResp.getNome() + " | " + novoResp.getCpf());

            mostrarAlerta(AlertType.INFORMATION,"Sucesso","Responsável alterado.");
            
            initialize();

        } catch (ONEException e) {
            mostrarAlerta(AlertType.ERROR,"Erro",e.getMessage());
        } catch (QIException | QLException e) {
            mostrarAlerta(AlertType.ERROR,"Erro",e.getMessage());
        }
    }
}
