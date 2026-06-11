package com.smarthotel.negocios;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.smarthotel.models.Hospedagem;

public class GeradorCSV {

    public void gerarRelatorioGeralCSV(
            ArrayList<Hospedagem> hospedagens)
            throws Exception {

        File pasta = new File("relatorios");
        pasta.mkdirs();

        FileWriter writer =
                new FileWriter(
                        "relatorios/relatorio_geral.csv");

        writer.append(
                "ID,RESPONSAVEL,CPF,QUARTO,TIPO,STATUS,ENTRADA,SAIDA,TOTAL\n");

        for (Hospedagem hosp : hospedagens) {

            writer.append(
                    hosp.getId() + "," +
                    hosp.getConta().getResponsavel().getNome() + "," +
                    hosp.getConta().getResponsavel().getCpf() + "," +
                    hosp.getQuarto().getNumero() + "," +
                    hosp.getQuarto().getTipo() + "," +
                    hosp.getStatus() + "," +
                    hosp.getDataEntrada() + "," +
                    hosp.getHorarioSaida() + "," +
                    hosp.getConta().CalcularDivida() +
                    "\n"
            );
        }

        writer.close();

        System.out.println(
                "Relatório CSV gerado em: relatorios/relatorio_geral.csv");
    }
}