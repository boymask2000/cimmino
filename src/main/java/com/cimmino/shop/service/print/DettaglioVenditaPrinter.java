package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Vendite;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class DettaglioVenditaPrinter extends BasePrinter implements HasOutputStream {
	

	public DettaglioVenditaPrinter(Vendite ven) throws Exception {
		
		
		String html = buildHtml(ven);

		outputStream = new ByteArrayOutputStream();

		PdfRendererBuilder builder = new PdfRendererBuilder();

		builder.withHtmlContent(html, null);
		builder.toStream(outputStream);
		builder.run();
	}

	private String buildHtml(Vendite vendita) {

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

		html.append("<h1>Dettaglio Vendita</h1>");
		html.append(makeVenditaBox(vendita));
		html.append(makeBinsBox(vendita));

		html.append("""
				    </body>
				</html>
				""");

		return html.toString();
	}

	private String makeBinsBox(Vendite vendita) {
		StringBuilder out = new StringBuilder();
	
		out.append("<table>");

//		out.append("<tr>");
		out.append("<th colspan='4' class='section-title'>Bins</th>");
//		out.append("</tr>");
		out.append("<tr>");
		out.append("<th>Bin</th>");
		out.append("<th>Num</th>");

		out.append("<th>Lordo</th>");
		out.append("<th>Netto</th>");

		out.append("</tr>");
		for (BinsVendite bis : vendita.getBins()) {
			out.append("<tr>");
			
			out.append("<td>").append(clean(bis.getBin().getName())).append("</td>");
			out.append("<td>").append(clean(bis.getNumBins())).append("</td>");
			out.append("<td>").append(clean(bis.getPesoLordo())).append("</td>");
			out.append("<td>").append(clean(bis.getPesoNetto())).append("</td>");
	
			out.append("</tr>");
		}
		out.append("</table>");
		return out.toString();
	}

	private String makeVenditaBox(Vendite ven) {

		StringBuilder out = new StringBuilder();

		out.append("<table>");

		out.append("<tr>");
		out.append("<th colspan='10' class='section-title'>VENDITE</th>");
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
		
		
		out.append("<th>Prezzo</th>");
		out.append("<th>Importo</th>");
		out.append("</tr>");


			out.append("<tr>");

			out.append("<td>").append(clean(ven.getCommerciante().getName())).append("</td>");
			out.append("<td>").append(clean(ven.getData())).append("</td>");
			out.append("<td>").append(clean(ven.getDtt())).append("</td>");
			out.append("<td>").append(clean(ven.getPeso_lordo())).append("</td>");
			out.append("<td>").append(clean(ven.getPeso_netto())).append("</td>");
			out.append("<td>").append(clean(ven.getTara())).append("</td>");
			out.append("<td>").append(clean(ven.getMedia())).append("</td>");
			out.append("<td>").append(clean(ven.getScarto())).append("</td>");
			// out.append("<td>").append(clean(ven.getBin().getName())).append("</td>");
			// out.append("<td>").append(clean(ven.getnBins())).append("</td>");
			out.append("<td>").append(clean(ven.getPrezzo())).append("</td>");
			out.append("<td>").append(clean(ven.getImporto())).append("</td>");

			out.append("</tr>");
		

		out.append("</table>");

		return out.toString();
	}

}
