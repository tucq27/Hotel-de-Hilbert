package com.smarthotel.negocios;

import java.io.File;
import java.util.ArrayList;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Recibo;

public class GeradorPDF {

    public void gerarPDFTeste() throws Exception {

        File pasta = new File("relatorios");
        pasta.mkdirs();

        PdfWriter writer = new PdfWriter("relatorios/relatorio_teste.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("RELATÓRIO DO HOTEL DE HILBERT"));

        document.close();
    }

    public void gerarRelatorioSaidasPDF(ArrayList<Hospedagem> hospedagens) throws Exception {

        File pasta = new File("relatorios");
        pasta.mkdirs();

        PdfWriter writer = new PdfWriter("relatorios/relatorio_saidas.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("RELATÓRIO DE SAÍDAS"));
        document.add(new Paragraph(" "));

        for (Hospedagem hosp : hospedagens) {

            document.add(new Paragraph(
                "ID: " + hosp.getId()
            ));

            document.add(new Paragraph(
                "Quarto: " + hosp.getQuarto().getNumero()
            ));

            if (!hosp.getHospedes().isEmpty()) {

                document.add(new Paragraph(
                    "Hóspede: " + hosp.getHospedes().get(0).getNome()
                ));
            }

            document.add(new Paragraph(
                "Status: " + hosp.getStatus()
            ));

            document.add(new Paragraph(
                "Check-in: " + hosp.getHorarioCheckIn()
            ));

            document.add(new Paragraph(
                "Check-out: " + hosp.getHorarioCheckOut()
            ));

            document.add(new Paragraph(
                "----------------------------------------"
            ));
        }

        document.close();
    }

    public void gerarFaturaPDF(Hospedagem hospedagem) throws Exception {

        System.out.println("GERANDO FATURA...");

        File pasta = new File("relatorios");
        pasta.mkdirs();

        String nomeArquivo =
        "relatorios/fatura_" + hospedagem.getId() + ".pdf";

        System.out.println("Gerando PDF em: " + nomeArquivo);

        PdfWriter writer = new PdfWriter(nomeArquivo);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("FATURA DO HOTEL DE HILBERT"));
        document.add(new Paragraph(" "));

        document.add(new Paragraph(
            "Hospedagem: " + hospedagem.getId()
        ));

        document.add(new Paragraph(
            "Quarto: " + hospedagem.getQuarto().getNumero()
        ));

        ContaHospedagem conta = hospedagem.getConta();

        document.add(new Paragraph(
            "Responsável: " + conta.getResponsavel().getNome()
        ));

        document.add(new Paragraph(
            "CPF: " + conta.getResponsavel().getCpf()
        ));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("RECIBOS"));
        document.add(new Paragraph(" "));

        for (Recibo recibo : conta.getRecibos()) {

            document.add(new Paragraph(
                "ID: " + recibo.getId()
            ));

            document.add(new Paragraph(
                "Tipo: " + recibo.getTipo()
            ));

            document.add(new Paragraph(
                "Valor: R$ " + recibo.getValor()
            ));

            document.add(new Paragraph(
                "Descrição: " + recibo.getDesricaoAdicional()
            ));

            document.add(new Paragraph(
                "Data: " + recibo.getHorario()
            ));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("----------------------------------------"));
            document.add(new Paragraph(" "));
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph(
            "TOTAL FATURADO: R$ " + conta.getSaldoPendente()
        ));

        document.close();
    }

    public void gerarRelatorioHospedagemPDF(Hospedagem hospedagem) throws Exception {

    System.out.println("GERANDO RELATÓRIO DA HOSPEDAGEM...");

    File pasta = new File("relatorios");
    pasta.mkdirs();

    String nomeArquivo =
        "relatorios/relatorio_hospedagem_" + hospedagem.getId() + ".pdf";

    PdfWriter writer = new PdfWriter(nomeArquivo);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    ContaHospedagem conta = hospedagem.getConta();

    document.add(new Paragraph("RELATÓRIO DA HOSPEDAGEM"));
    document.add(new Paragraph(" "));

    document.add(new Paragraph("ID: " + hospedagem.getId()));
    document.add(new Paragraph("Status: " + hospedagem.getStatus()));

    document.add(new Paragraph(" "));
    document.add(new Paragraph("RESPONSÁVEL"));

    document.add(new Paragraph(
        "Nome: " + conta.getResponsavel().getNome()
    ));

    document.add(new Paragraph(
        "CPF: " + conta.getResponsavel().getCpf()
    ));

    document.add(new Paragraph(" "));
    document.add(new Paragraph("QUARTO"));

    document.add(new Paragraph(
        "Número: " + hospedagem.getQuarto().getNumero()
    ));

    document.add(new Paragraph(
        "Tipo: " + hospedagem.getQuarto().getTipo()
    ));

    document.add(new Paragraph(
        "Status do quarto: " + hospedagem.getQuarto().getStatus()
    ));

    document.add(new Paragraph(" "));
    document.add(new Paragraph("DATAS"));

    document.add(new Paragraph(
        "Entrada: " + hospedagem.getDataEntrada()
    ));

    document.add(new Paragraph(
        "Check-in: " + hospedagem.getHorarioCheckIn()
    ));

    document.add(new Paragraph(
        "Check-out: " + hospedagem.getHorarioCheckOut()
    ));

    document.add(new Paragraph(
        "Saída prevista: " + hospedagem.getHorarioSaida()
    ));

    document.add(new Paragraph(" "));
    document.add(new Paragraph("HÓSPEDES"));

    for (var hospede : hospedagem.getHospedes()) {

        document.add(new Paragraph(
            "- " + hospede.getNome()
        ));
    }

    document.add(new Paragraph(" "));
    document.add(new Paragraph("RECIBOS"));

    for (Recibo recibo : conta.getRecibos()) {

        document.add(new Paragraph(
            "ID: " + recibo.getId()
        ));

        document.add(new Paragraph(
            "Tipo: " + recibo.getTipo()
        ));

        document.add(new Paragraph(
            "Valor: R$ " + recibo.getValor()
        ));

        document.add(new Paragraph(
            "Descrição: " + recibo.getDesricaoAdicional()
        ));

        document.add(new Paragraph(
            "Data: " + recibo.getHorario()
        ));

        document.add(new Paragraph(
            "----------------------------------------"
        ));
    }

    document.add(new Paragraph(" "));
    document.add(new Paragraph(
        "TOTAL DA HOSPEDAGEM: R$ " + conta.getSaldoPendente()
    ));

    document.close();

    System.out.println(
        "Relatório gerado em: " + nomeArquivo
    );
}
}