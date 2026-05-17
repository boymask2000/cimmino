package com.cimmino.shop;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.Vendite;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generatePdf0(List<Arrivi> arrivi) throws Exception {

        String html = buildHtml(arrivi);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();

        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }
    public byte[] generatePdf(List<Arrivi> arrivi) throws Exception {

        String html = buildHtml(arrivi);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();

        builder.withHtmlContent(html, null);

        // LANDSCAPE A4
        builder.useDefaultPageSize(
                297,
                210,
                PdfRendererBuilder.PageSizeUnits.MM
        );

        builder.toStream(outputStream);

        builder.run();

        return outputStream.toByteArray();
    }

    private String buildHtml(List<Arrivi> arrivi) {

        StringBuilder html = new StringBuilder();

        html.append("""
                <html>
                    <head>
                        <meta charset="UTF-8"/>

                        <style>

                            @page {
                               
                                margin: 10mm;
                            }

                            body {
                                font-family: Arial, sans-serif;
                                font-size: 10px;
                                margin: 0;
                                padding: 0;
                            }

                            h1 {
                                color: #2c3e50;
                                margin-bottom: 20px;
                            }

                           table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
}

                            th, td {
                                border: 1px solid #000;
                                padding: 4px;
                                text-align: left;
                                vertical-align: top;
                            }

                            th {
                                background-color: #f2f2f2;
                            }

                            .arrivo-box {
                                margin-bottom: 25px;
                                page-break-inside: avoid;
                            }

                            .section-title {
                                background-color: #dfe6e9;
                                font-weight: bold;
                            }

                            tr {
                                page-break-inside: avoid;
                            }

                        </style>

                    </head>

                    <body>
                """);

        html.append("<h1>Report Arrivi</h1>");

        html.append(generateArrivi(arrivi));

        html.append("""
                    </body>
                </html>
                """);

        return html.toString();
    }
    private String generateArrivi(List<Arrivi> arrivi) {

        StringBuilder out = new StringBuilder();

        for (Arrivi arrivo : arrivi) {

            boolean hasVendite = arrivo.getVendite() != null && !arrivo.getVendite().isEmpty();

            out.append("<table style='width:100%; table-layout:fixed;'>");

            // FORZA COLONNE 30% / 70%
            out.append("<colgroup>");
            out.append("<col style=\"width:20%;\" />");
            out.append("<col style=\"width:80%;\" />");
            out.append("</colgroup>");

            out.append("<tr>");

            // COLONNA 1 SEMPRE
            out.append("<td style='vertical-align:top;'>");
            out.append(makeArriviBox(arrivo));
            out.append("</td>");

            // COLONNA 2 SEMPRE (MA GESTITA)
            out.append("<td style='vertical-align:top;'>");

            if (hasVendite) {
                out.append(makeVenditeBox(arrivo));
            } else {
                // IMPORTANTISSIMO: evita collapse del layout
                out.append("<div style='min-height:1px;'></div>");
            }

            out.append("</td>");

            out.append("</tr>");
            out.append("</table>");
        }

        return out.toString();
    }
    private String generateArrivi0(List<Arrivi> arrivi) {

        StringBuilder out = new StringBuilder();

        for (Arrivi arrivo : arrivi) {

            out.append("<div class='arrivo-box'>");

            out.append(makeArriviBox(arrivo));

            if (!arrivo.getVendite().isEmpty()) {
                out.append(makeVenditeBox(arrivo));
            }

            out.append("</div>");
        }

        return out.toString();
    }

    private String makeArriviBox(Arrivi arrivo) {

        StringBuilder out = new StringBuilder();

        out.append("<table>");

        out.append("<tr>");
        out.append("<th colspan='2' class='section-title'>DATI ARRIVO</th>");
        out.append("</tr>");

        addRow(out, "Merce", clean(arrivo.getMerce().getName()));
        addRow(out, "Data", clean(arrivo.getData()));
        addRow(out, "Peso Lordo", clean(arrivo.getPeso_lordo()));
        addRow(out, "Peso Netto", clean(arrivo.getPeso_netto()));
        addRow(out, "Freddo", clean(arrivo.getFreddo()));
        addRow(out, "Calo", clean(arrivo.getCalo()));

        out.append("<tr>");
        out.append("<td colspan='2'>");
        out.append(buildListaBins(arrivo));
        out.append("</td>");
        out.append("</tr>");

        out.append("</table>");

        return out.toString();
    }

    private String buildListaBins(Arrivi arrivo) {

        StringBuilder out = new StringBuilder();

        out.append("<table>");

        out.append("<tr>");
        out.append("<th colspan='2'>Bins</th>");
        out.append("</tr>");

        out.append("<tr>");
        out.append("<th>Bin</th>");
        out.append("<th>Quantità</th>");
        out.append("</tr>");

        for (BinsArrivi bin : arrivo.getBins()) {

            out.append("<tr>");

            out.append("<td>");
            out.append(clean(bin.getBin().getName()));
            out.append("</td>");

            out.append("<td>");
            out.append(clean(bin.getNumBins()));
            out.append("</td>");

            out.append("</tr>");
        }

        out.append("</table>");

        return out.toString();
    }

    private String makeVenditeBox(Arrivi arrivo) {

        StringBuilder out = new StringBuilder();

        out.append("<table>");

        out.append("<tr>");
        out.append("<th colspan='12' class='section-title'>VENDITE</th>");
        out.append("</tr>");

        out.append("<tr>");
        out.append("<th>Commerciante</th>");
        out.append("<th>Data</th>");
        out.append("<th>DTT</th>");
        out.append("<th>Lordo</th>");
        out.append("<th>Netto</th>");
        out.append("<th>Tara</th>");
        out.append("<th>Media</th>");
        out.append("<th>Scarto</th>");
        out.append("<th>Bin</th>");
        out.append("<th>N° Bin</th>");
        out.append("<th>Prezzo</th>");
        out.append("<th>Importo</th>");
        out.append("</tr>");

        for (Vendite ven : arrivo.getVendite()) {

            out.append("<tr>");

            out.append("<td>").append(clean(ven.getCommerciante().getName())).append("</td>");
            out.append("<td>").append(clean(ven.getData())).append("</td>");
            out.append("<td>").append(clean(ven.getDtt())).append("</td>");
            out.append("<td>").append(clean(ven.getLordo())).append("</td>");
            out.append("<td>").append(clean(ven.getNetto())).append("</td>");
            out.append("<td>").append(clean(ven.getTara())).append("</td>");
            out.append("<td>").append(clean(ven.getMedia())).append("</td>");
            out.append("<td>").append(clean(ven.getScarto())).append("</td>");
            out.append("<td>").append(clean(ven.getBin().getName())).append("</td>");
            out.append("<td>").append(clean(ven.getnBins())).append("</td>");
            out.append("<td>").append(clean(ven.getPrezzo())).append("</td>");
            out.append("<td>").append(clean(ven.getImporto())).append("</td>");

            out.append("</tr>");
        }

        out.append("</table>");

        return out.toString();
    }

    private void addRow(StringBuilder out, String label, String value) {

        out.append("<tr>");

        out.append("<th>");
        out.append(label);
        out.append("</th>");

        out.append("<td>");
        out.append(value);
        out.append("</td>");

        out.append("</tr>");
    }

    private String clean(Object value) {

        if (value == null) {
            return "";
        }

        return value.toString();
    }
}