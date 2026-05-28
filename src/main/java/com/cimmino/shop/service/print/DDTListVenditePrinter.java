package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.dto.DDTDTO;
import com.cimmino.shop.service.DDTService;
import com.cimmino.shop.service.VenditeService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Component
public class DDTListVenditePrinter extends BasePrinter implements HasOutputStream {
	@Autowired
	DDTService ddtService;
	@Autowired
	VenditeService venditeService;

	private Configurazione conf;
	private List<Vendite> vendite;
	private Commerciante commerciante;

	
	public void exec(List<Vendite> vendite, Configurazione conf) throws Exception{

		this.conf = conf;
		this.vendite = vendite;
		if(vendite.size()>0)
			this.commerciante=vendite.get(0).getCommerciante();
		String html = buildHtml();

		outputStream = new ByteArrayOutputStream();

		PdfRendererBuilder builder = new PdfRendererBuilder();

		builder.withHtmlContent(html, null);
		builder.toStream(outputStream);
		builder.run();
		
		DDTDTO dto = ddtService.create(html);
		Long ddtId = dto.getId();
		for (Vendite vendita : vendite) {
			
			vendita.setDdt(""+ddtId);
			venditeService.save(vendita);
		}
		
	}

	private String buildHtml() {

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
		html.append(makeBinsBox());

		html.append(makeFooter1());
		html.append(makeFooter2());

		html.append("""
				    </body>
				</html>
				""");

		return html.toString();
	}

	private Object makeFooter2() {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<tr>");
		out.append("<td>");
		out.append("<h2>");
		out.append("PRODOTTO CERTIFICATO GLOBAL GAP");
		out.append("</h2>");
		out.append("<h2>");
		out.append("GGN 4063061826466");
		out.append("</h2>");
		out.append("</td>");
		out.append("<td>");
		out.append("<p>Firma del conducente</p>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("<p>Firma del destinatario</p>");
		out.append("<br/>");
		out.append("</td>");
		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private Object makeFooter1() {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<tr>");
		out.append("<td>");
		out.append("<h4>");
		out.append("1 Incaricato del trasporto (DITTA, INDIRIZZO, N.ALBO)");
		out.append("</h4>");

		out.append("</td>");
		out.append("<td>");
		out.append("<h4>");
		out.append("2 Incaricato del trasporto (DITTA, INDIRIZZO, N.ALBO)");
		out.append("</h4>");
		out.append("</td>");
		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private Object makeBinsBox() {
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
		int totColli = 0;
		int numRows=0;
		BigDecimal totPeso = BigDecimal.ZERO;
		for (Vendite vendita : vendite)
			for (BinsVendite b : vendita.getBins()) {
				out.append("<tr>");
				out.append("<td>");
				out.append(vendita.getArrivo().getMerce().getName());
				out.append("</td>");
				out.append("<td>");
				out.append(b.getBin().getName());
				out.append("</td>");
				out.append("<td>");
				out.append(b.getNumBins());
				totColli += b.getNumBins();
				out.append("</td>");
				out.append("<td>");
				out.append(b.getPesoLordo());
				totPeso = totPeso.add(b.getPesoLordo());
				out.append("</td>");

				out.append("</tr>");
				numRows++;
			}
		for(int i=0; i<10- numRows; i++) {
			out.append("<tr>");
			out.append("<td>");
		
			out.append("</td>");
			out.append("<td>");
			
			out.append("</td>");
			out.append("<td>");
			
			out.append("</td>");
			out.append("<td>");
		
			out.append("</td>");
			
			out.append("</tr>");
		}
		out.append("</tbody>");
//		makeFooter(out, totColli, totPeso);
		out.append("</table>");
		makeFooter2(out, totColli, totPeso);
		return out.toString();
	}

	private void makeFooter2(StringBuilder out, int totColli, BigDecimal totPeso) {
		out.append("<table>");
		out.append("<tr>");
		// out.append("<td colspan=\"3\">N.Colli</td>");
		out.append("<td  >");
		out.append("PAESE DI ORIGINE<br/>dei prodotti<br/>ITALIA");
		out.append("</td>");
		out.append("<td  >");
		out.append("CATEGORIA<br/>Ove richiesta");
		out.append("</td>");
		out.append("<td  >");
		out.append("Aspetto esteriore dei beni<br/>BINS IN PLASTICA");
		out.append("</td>");
		out.append("<td  class=\"text-end fw-bold\">n.Colli<br/>" + totColli + "</td>");
		out.append("<td  class=\"text-end fw-bold\" >Peso kg.<br/>" + totPeso + "</td>");
		out.append("</tr>");
		out.append("</table>");
		System.out.println(out.toString());
	}

	private void makeFooter(StringBuilder out, int totColli, BigDecimal totPeso) {
		out.append("<tfoot>");
		out.append("<tr>");
		// out.append("<td colspan=\"3\">N.Colli</td>");
		out.append("<td colspan=\"0\" ></td>");
		out.append("<td colspan=\"1\" ></td>");
		out.append("<td colspan=\"1\" class=\"text-end fw-bold\">n.Colli<br/>" + totColli + "</td>");
		out.append("<td colspan=\"1\" class=\"text-end fw-bold\" >Peso kg.<br/>" + totPeso + "</td>");
		out.append("</tr>");
		out.append("</tfoot>");
		System.out.println(out.toString());
	}

	private String makeTestata4() {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<tr>");

		out.append("<td>");

		out.append("<h3>");
		out.append("Causale del trasporto:");
		out.append("</h3>");

		out.append("<h3>");
		out.append("CONFERIMENTO MERCI");
		out.append("</h3>");
		out.append("</td>");

		out.append("<td>");
		out.append("<h4>");
		out.append("Luogo di compilazione e caico merci (se diverso dal cedente)");
		out.append("</h4>");
		out.append("</td>");

		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private String makeTestata3() {
		StringBuilder out = new StringBuilder();
	
		out.append("<table>");
		out.append("<tr>");

		out.append("<td>");
		out.append(commerciante.getName());
		out.append("<br/>" + clean(commerciante.getIndirizzo()));
		out.append("</td>");

		out.append("</tr>");
		out.append("</table>");

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
		out.append("<h4>Cedente: Ditta </h4>");
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
	//	out.append("N." + conf.getId() + " Del " + vendita.getData());
		out.append("</h2>");

	}

	private void insertAzienda(StringBuilder out) {
		out.append("<p>");
		out.append(conf.getName());
		out.append("</p>");
		out.append("<p>");
		out.append(conf.getIndirizzo());
		out.append("</p>");
		out.append("<p>");
		out.append(conf.getPec());
		out.append("</p>");
		out.append("<p>");
		out.append(conf.getCodFiscale());
		out.append("</p>");
		out.append("<p>");
		out.append(conf.getpIva());
		out.append("</p>");
	}
}
	