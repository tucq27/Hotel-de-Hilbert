package com.smarthotel.gui.telas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.smarthotel.negocios.*;
import com.smarthotel.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("TelaInicial.fxml"))
        );

        stage.setScene(scene);
        stage.setTitle("SmartHotel");
        stage.show();
    }

    public static void main(String[] args) {

        IContHospedagens contHospedagens = ControladorHospedagens.getInstance();
        IContPessoas contPessoas = ControladorPessoas.getInstance();
        IContQuartos contQuartos = ControladorQuartos.getInstance();
        IContItens contItens = ControladorItens.getInstance();

        try {
            // ITENS QUE JÁ EXISTIAM
            Item agua = new Item("", "Água Mineral", LocalDate.of(2026, 12, 31), 5.00);
            Item chocolate = new Item("", "Chocolate", LocalDate.of(2026, 10, 20), 8.50);

            // +2 ITENS NOVOS
            Item refrigerante = new Item("", "Refrigerante", LocalDate.of(2026, 11, 30), 7.50);
            Item salgadinho = new Item("", "Salgadinho", LocalDate.of(2026, 10, 15), 6.00);

            contItens.adicionarItem(agua);
            contItens.adicionarItem(chocolate);
            contItens.adicionarItem(refrigerante);
            contItens.adicionarItem(salgadinho);

            // QUARTOS QUE JÁ EXISTIAM
            Quarto quarto1 = new Quarto(TipoQuarto.PADRAO, 101, 1, 2);
            Quarto quarto2 = new Quarto(TipoQuarto.SUITE, 202, 2, 3);

            // +2 QUARTOS NOVOS
            Quarto quarto3 = new Quarto(TipoQuarto.PRESIDENCIAL, 303, 3, 4);
            Quarto quarto4 = new Quarto(TipoQuarto.PADRAO, 104, 1, 2);

            contQuartos.adicionarQuarto(quarto1);
            contQuartos.adicionarQuarto(quarto2);
            contQuartos.adicionarQuarto(quarto3);
            contQuartos.adicionarQuarto(quarto4);

            // colocando itens nos frigobares
            contQuartos.reporItemFrigobar(quarto1.getId(), agua.getId());
            contQuartos.reporItemFrigobar(quarto1.getId(), chocolate.getId());

            contQuartos.reporItemFrigobar(quarto2.getId(), refrigerante.getId());
            contQuartos.reporItemFrigobar(quarto2.getId(), salgadinho.getId());

            // PESSOAS QUE JÁ EXISTIAM
            Pessoa pessoa1 = new Pessoa("João Silva", "1111", LocalDate.of(1997, 1, 1));
            Pessoa pessoa2 = new Pessoa("Maria Santos", "2222", LocalDate.of(1990, 5, 15));
            Pessoa pessoa3 = new Pessoa("Carlos Lima", "3333", LocalDate.of(1985, 3, 20));
            Pessoa pessoa4 = new Pessoa("Ana Souza", "4444", LocalDate.of(1998, 8, 10));
            Pessoa responsavel1 = new Pessoa("Roberto Costa", "5555", LocalDate.of(1978, 4, 12));
            Pessoa responsavel2 = new Pessoa("Fernanda Alves", "6666", LocalDate.of(1982, 9, 25));

            // +2 PESSOAS NOVAS
            Pessoa pessoa5 = new Pessoa("Pedro Lima", "7777", LocalDate.of(1995, 4, 22));
            Pessoa pessoa6 = new Pessoa("Juliana Rocha", "8888", LocalDate.of(1999, 7, 18));

            contPessoas.adicionarPessoa(pessoa1);
            contPessoas.adicionarPessoa(pessoa2);
            contPessoas.adicionarPessoa(pessoa3);
            contPessoas.adicionarPessoa(pessoa4);
            contPessoas.adicionarPessoa(responsavel1);
            contPessoas.adicionarPessoa(responsavel2);
            contPessoas.adicionarPessoa(pessoa5);
            contPessoas.adicionarPessoa(pessoa6);

            contPessoas.adicionarHospede("1111", "Prefere quarto silencioso");
            contPessoas.adicionarHospede("2222", "Prefere andar alto");

            contPessoas.adicionarFuncionario("3333", "Recepcionista");
            contPessoas.adicionarFuncionario("4444", "Camareira");

            Hospede hospede1 = (Hospede) contPessoas.buscarPessoa("1111");
            Hospede hospede2 = (Hospede) contPessoas.buscarPessoa("2222");

            ContaHospedagem conta1 = new ContaHospedagem("conta001", responsavel1, "Cartão - 999990");
            ContaHospedagem conta2 = new ContaHospedagem("conta002", responsavel2, "Pix");

            ArrayList<Hospede> hospedesQuarto1 = new ArrayList<>();
            hospedesQuarto1.add(hospede1);

            ArrayList<Hospede> hospedesQuarto2 = new ArrayList<>();
            hospedesQuarto2.add(hospede2);

            String idHospedagem1 = contHospedagens.hospedarAgora(
                    quarto1,
                    LocalDateTime.of(2026, 6, 5, 12, 0),
                    conta1,
                    hospedesQuarto1
            );

            String idHospedagem2 = contHospedagens.hospedarAgora(
                    quarto2,
                    LocalDateTime.of(2026, 6, 6, 14, 0),
                    conta2,
                    hospedesQuarto2
            );

            Hospedagem hospedagem1 = contHospedagens.buscarHospedagem(idHospedagem1);

            GeradorPDF geradorPDF = new GeradorPDF();

            geradorPDF.gerarFaturaPDF(hospedagem1, "relatorios/fatura_teste.pdf");
            geradorPDF.gerarRelatorioHospedagemPDF(hospedagem1, "relatorios/relatorio_teste.pdf");

            ControladorRelatorios relatorios = ControladorRelatorios.getInstance();
            relatorios.exportarRelatorioGeralCSV();

            System.out.println("Hospedagem 1 criada: " + idHospedagem1);
            System.out.println("Hospedagem 2 criada: " + idHospedagem2);

        } catch (Exception e) {
            System.out.println(" - - - - Erro: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("ANTES DO LAUNCH");
        launch(args);
    }
}