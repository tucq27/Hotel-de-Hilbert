package com.smarthotel.gui.telas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Importações para teste
import com.smarthotel.negocios.*;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(FXMLLoader.load( getClass().getResource("TelaPrincipal.fxml") ));

        stage.setScene(scene);
        stage.setTitle("SmartHotel");
        stage.show();
    }

    public static void main(String[] args) {

        // preencehndo dados de teste
        ControladorHospedagens contHospedagens = new ControladorHospedagens();
        ControladorPessoas contPessoas = new ControladorPessoas();
        ControladorQuartos contQuartos = new ControladorQuartos();

        Quarto quarto1 = new QuartoPadrao(001, 1, 2);
        quarto1.setId("001");
        QuartoSuite quarto2 = new QuartoSuite(002, 1, 2);
        quarto2.setId("002");

        Pessoa j = new Pessoa("Jojo", "1234", LocalDate.of(1997, 1, 1));
        Pessoa m = new Pessoa("Maria", "5678", LocalDate.of(1990, 5, 15));
        Pessoa c = new Pessoa("Carlos", "4321", LocalDate.of(1985, 3, 20));

        Pessoa hospJ = new Hospede(j);
        Pessoa hospM = new Hospede(m);
        Pessoa resp = new Responsavel(c, "99999");
        try {
            contPessoas.adicionarPessoa(hospJ);
            contPessoas.adicionarPessoa(hospM);
            contPessoas.adicionarPessoa(resp);
            contQuartos.adicionarQuarto(quarto1);
            contQuartos.adicionarQuarto(quarto2); 
        } catch (ORException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());
        }
        
        ContaHospedagem conta1 = new ContaHospedagem("conta282828", new Responsavel(c, "99999"));
        ArrayList<Hospede> hospedes = new ArrayList<>();
        hospedes.add(new Hospede(m));

        try {
            String h1 =contHospedagens.hospedarAgora(quarto1, LocalDateTime.of(2026, 6, 5, 12, 0), conta1, hospedes);
            System.out.println("id da hospedagem automatica: " + h1);
        } catch (Exception e) {
            System.out.println(" - - - - Erro: " + e.getMessage());
        }
        //Hospedagem h2= new Hospedagem(quarto2, LocalDate.of(2026, 6, 2), LocalDateTime.of(2026, 6, 5, 12, 0), conta1, hospedes);

        // abre a tela principal do sistema
        launch();
    }
}