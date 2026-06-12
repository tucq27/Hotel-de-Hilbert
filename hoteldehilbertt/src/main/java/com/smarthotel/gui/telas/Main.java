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

/*

mvn -f hoteldehilbertt/pom.xml javafx:run

*/
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // abre a tela principal (admin) por hora
        // substituir por outra tela, que vai abrir tela de seleção: (admin) ou (usuario)
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml")));
        stage.setScene(scene);
        stage.setTitle("SmartHotel");
        stage.show();
    }

    public static void main(String[] args) {

        // preencehndo dados de teste
        IContHospedagens contHospedagens = ControladorHospedagens.getInstance();
        IContPessoas contPessoas = ControladorPessoas.getInstance();
        IContQuartos contQuartos = ControladorQuartos.getInstance();

        Quarto quarto1 = new Quarto(TipoQuarto.PADRAO,001, 1, 2);
        quarto1.setId("001");

        Quarto quarto2 = new Quarto(TipoQuarto.SUITE, 002, 1, 2);
        quarto2.setId("002");

        Pessoa j = new Pessoa("Jojo", "1234", LocalDate.of(1997, 1, 1));
        Pessoa m = new Pessoa("Maria", "5678", LocalDate.of(1990, 5, 15));
        Pessoa c = new Pessoa("Carlos", "4321", LocalDate.of(1985, 3, 20));

        Pessoa hospJ = new Hospede(j);
        Pessoa hospM = new Hospede(m);
        Pessoa resp = c;

        try {
            contPessoas.adicionarPessoa(hospJ);
            contPessoas.adicionarPessoa(hospM);
            contPessoas.adicionarPessoa(resp);

            contQuartos.adicionarQuarto(quarto1);
            contQuartos.adicionarQuarto(quarto2);

        } catch (ORException e) {
            System.out.println(" - - - - Erro: " + e.getMessage());
        }

        ContaHospedagem conta1 =
                new ContaHospedagem("conta282828", c, "Cartão - 999990");

        ArrayList<Hospede> hospedes = new ArrayList<>();
        hospedes.add(new Hospede(m));

        try {
            String h1 = contHospedagens.hospedarAgora(
                    quarto1,
                    LocalDateTime.of(2026, 6, 5, 12, 0),
                    conta1,
                    hospedes);
            IContPagamentos contPagamentos = ControladorPagamentos.getInstance();

            Hospedagem hospedagem = contHospedagens.buscarHospedagem(h1);

            Recibo reciboDiaria =
                    contPagamentos.gerarReciboDiaria(hospedagem);

            contPagamentos.adicionarRecibo(
                    hospedagem.getConta(),
                    reciboDiaria);

            GeradorPDF geradorPDF = new GeradorPDF();

            geradorPDF.gerarFaturaPDF(hospedagem);

            GeradorPDF gerador = new GeradorPDF();

           gerador.gerarRelatorioHospedagemPDF(hospedagem);

           ControladorRelatorios relatorios =
           ControladorRelatorios.getInstance();

           relatorios.exportarRelatorioGeralCSV();

            System.out.println("id da hospedagem automatica: " + h1);

        } catch (Exception e) {
            System.out.println(" - - - - Erro: " + e.getMessage());
        }

        // abre a tela principal do sistema
        System.out.println("ANTES DO LAUNCH");
        launch();
    }
}