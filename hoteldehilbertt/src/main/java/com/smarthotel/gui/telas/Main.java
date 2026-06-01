package com.smarthotel.gui.telas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Importações para teste
import com.smarthotel.negocios.*;
import com.smarthotel.dados.exceptions.*;
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
        Quarto quarto1 = new QuartoPadrao("Q001", 1, 2);
        QuartoSuite quarto2 = new QuartoSuite("Q002", 1, 2);
        
        Hospede hospJ = new Hospede("Jojo", "1234", LocalDate.of(1997, 1, 1));
        Hospede hospM = new Hospede("Maria", "5678", LocalDate.of(1990, 5, 15));
        Responsavel resp = new Responsavel("Carlos", "4321", LocalDate.of(1985, 3, 20), "99999");

        ContaHospedagem conta1 = new ContaHospedagem("C001", resp);
        ArrayList<Hospede> hospedes = new ArrayList<>();
        hospedes.add(hospJ);

        Hospedagem h1= new Hospedagem(quarto1, LocalDate.of(2026, 6, 2), LocalDateTime.of(2026, 6, 5, 12, 0), conta1, hospedes);
        
        ControladorHospedagens contHospedagens = new ControladorHospedagens();

        System.out.println("id da hospedagem: " + h1.getId());
        
        // abre a tela principal do sistema
        launch();
    }
}