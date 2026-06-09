package com.smarthotel.negocios;

import java.util.ArrayList;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.smarthotel.models.Hospedagem;

public class GeradorPDF {

    public void gerarPDFTeste() throws Exception {

        PdfWriter writer = new PdfWriter("relatorio_teste.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("RELATÓRIO DO HOTEL DE HILBERT"));

        document.close();
    }

    public void gerarRelatorioSaidasPDF(ArrayList<Hospedagem> hospedagens) throws Exception {

        PdfWriter writer = new PdfWriter("relatorio_saidas.pdf");
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

}