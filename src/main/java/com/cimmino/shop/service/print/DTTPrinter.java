package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.Vendite;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class DTTPrinter extends BasePrinter implements HasOutputStream {

	private Configurazione conf;
	private Vendite vendita;

	public DTTPrinter(Vendite ven, Configurazione conf) throws Exception {
		this.conf = conf;
		this.vendita = ven;

		String html = buildHtml(ven);

		outputStream = new ByteArrayOutputStream();

		PdfRendererBuilder builder = new PdfRendererBuilder();

		builder.withHtmlContent(html, null);
		builder.toStream(outputStream);
		builder.run();
	}

	@Override
	public ByteArrayOutputStream getOutputStream() {

		return outputStream;
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

		html.append(makeTestata1());
		html.append(makeTestata2());
		html.append(makeTestata3());
		html.append(makeTestata4());
		html.append(makeBinsBox(vendita));

		html.append("""
				    </body>
				</html>
				""");

		return html.toString();
	}

	private Object makeBinsBox(Vendite vendita2) {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<thead> ");
		out.append("<tr>");

		out.append("<th>DESCRIZIONE PRODOTTO</th>");
		out.append("<th>Tipo</th>");
		out.append("<th>N.Colli</th>");
		out.append("<th>U.M. KG- Quantità</th>");

		out.append("</tr>");
		out.append("</thead> ");
		out.append("<tbody>");
		int totColli =0;
		BigDecimal totPeso=BigDecimal.ZERO;
		for( BinsVendite b: vendita.getBins()) {
			out.append("<tr>");
			out.append("<td>");
			out.append(vendita.getArrivo().getMerce().getName());
			out.append("</td>");
			out.append("<td>");
			out.append(b.getBin().getName());
			out.append("</td>");
			out.append("<td>");
			out.append(b.getNumBins()); totColli+=b.getNumBins();
			out.append("</td>");
			out.append("<td>");
			out.append(b.getPesoLordo());totPeso = totPeso.add(b.getPesoLordo());
			out.append("</td>");
			
			out.append("</tr>");
		}
		out.append("</tbody>");
		makeFooter(out, totColli, totPeso);
		out.append("</table>");
		return out.toString();
	}

	private void makeFooter(StringBuilder out, int totColli, BigDecimal totPeso) {
		out.append("<tfoot>");
		out.append("<tr>");
	//	out.append("<td colspan=\"3\">N.Colli</td>");
		out.append("<td colspan=\"0\" ></td>");
		out.append("<td colspan=\"1\" ></td>");
		out.append("<td colspan=\"1\"  class=\"text-end fw-bold\">"+totColli+"</td>");
		out.append("<td colspan=\"1\" class=\"text-end fw-bold\" >"+totPeso+"</td>");
		out.append("</tr>");
		out.append("</tfoot>");
		System.out.println(out.toString());
	}

	private String makeTestata4() {
		StringBuilder out = new StringBuilder();
		out.append("<h3>");
		out.append("Causale del trasporto:");
		out.append("</h3>");

		out.append("<h3>");
		out.append("CONFERIMENTO MERCI");
		out.append("</h3>");
		return out.toString();
	}

	private String makeTestata3() {
		StringBuilder out = new StringBuilder();
		return out.toString();
	}

	private String makeTestata2() {
		StringBuilder out = new StringBuilder();
		return out.toString();
	}

	private String makeTestata1() {
		StringBuilder out = new StringBuilder();

		out.append("<table>");
		out.append("<tr>");

		out.append("<td>");
		insertAzienda(out);
		out.append("</td>");

		out.append("<td>");
		insertTestataDestra(out);
		out.append("</td>");

		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private void insertTestataDestra(StringBuilder out) {
		out.append("<h2>");
		out.append("D.T.T.  SCHEDA DI TRASPORTO");
		out.append("</h2>");

		out.append("<h2>");
		out.append("N." + conf.getId() + " Del " + vendita.getData());
		out.append("</h2>");

	}

	private void insertAzienda(StringBuilder out) {
		out.append("<p>");
		out.append(conf.getName());
		out.append("</p>");
		out.append("<p>");
		out.append(conf.getIndirizzo());
		out.append("</p>");
	}
}
