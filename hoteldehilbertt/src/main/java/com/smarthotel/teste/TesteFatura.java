package com.smarthotel.teste;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.smarthotel.models.*;
import com.smarthotel.negocios.*;

public class TesteFatura {

    public static void main(String[] args) {

        try {

            Quarto quarto = new QuartoPadrao(1, 1, 2);
            quarto.setId("001");

            Pessoa pessoa = new Pessoa(
                    "Carlos",
                    "4321",
                    LocalDate.of(1985, 3, 20));

            Responsavel responsavel =
                    new Responsavel(pessoa, "99999");

            ContaHospedagem conta =
                    new ContaHospedagem("conta1", responsavel);

            ArrayList<Hospede> hospedes = new ArrayList<>();

            hospedes.add(
                    new Hospede(
                            new Pessoa(
                                    "Maria",
                                    "5678",
                                    LocalDate.of(1990, 5, 15))));

            Hospedagem hospedagem =
            new Hospedagem(
                quarto,
                LocalDate.now().minusDays(3),
                LocalDateTime.now(),
                conta,
                hospedes);

            hospedagem.setId("H001");

            hospedagem.setHorarioCheckIn(
            LocalDateTime.now().minusDays(3));

            ControladorPagamentos pagamentos =
                    new ControladorPagamentos();

            Recibo diaria =
                    pagamentos.gerarReciboDiaria(hospedagem);

            pagamentos.adicionarRecibo(
                    conta,
                    diaria);

                Recibo servico =
                new Recibo(
                        TipoRecibo.SERVICO,
                        80.0,
                        "Lavanderia");

                servico.setId("2SERVICO");

                pagamentos.adicionarRecibo(
                        conta,
                        servico);

                Recibo frigobar =
                        new Recibo(
                                TipoRecibo.FRIGOBAR,
                                25.0,
                                "Coca-Cola");

                frigobar.setId("3FRIGOBAR");

                pagamentos.adicionarRecibo(
                        conta,
                        frigobar);

            GeradorPDF gerador =
                    new GeradorPDF();

            gerador.gerarFaturaPDF(hospedagem);

            System.out.println("PDF GERADO COM SUCESSO!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}