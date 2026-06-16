package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cimmino.shop.database.BinsGruppoVendita;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.Titolare;
import com.cimmino.shop.database.TitolareRepository;
import com.cimmino.shop.database.Trasportatore;
import com.cimmino.shop.database.TrasportatoreRepository;
import com.cimmino.shop.database.Vendita;
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
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;
	@Autowired
	TrasportatoreRepository trasportatoreRepository;
	@Autowired
	TitolareRepository titolariRepository;

	private Configurazione conf;
	private List<GruppoVendite> vendite;
	private Commerciante commerciante;
	private int totColli = 0;
	private int numRows = 0;
	private BigDecimal totPeso = BigDecimal.ZERO;

//	public void exec(List<Vendita> vendite, Configurazione conf) throws Exception {
//
//		this.conf = conf;
//		this.vendite = vendite;
//		if (vendite.size() > 0)
//			this.commerciante = vendite.get(0).getCommerciante();
//
//		DDTDTO dto1 = ddtService.create("");
//		String html = buildHtml(dto1);
//
//		outputStream = new ByteArrayOutputStream();
//
//		PdfRendererBuilder builder = new PdfRendererBuilder();
//
//		builder.withHtmlContent(html, null);
//		builder.toStream(outputStream);
//		builder.run();
//
//		DDTDTO dto = ddtService.create(html,dto1);
//		Long ddtId = dto1.getId();
//		for (Vendita vendita : vendite) {
//
//			vendita.setDdt("" + ddtId);
//			venditeService.save(vendita);
//		}
//
//	}
	public void exec(List<GruppoVendite> vendite, Configurazione conf, DDTInputData ddtInputData) throws IOException {

		this.conf = conf;
		this.vendite = vendite;
		if (vendite.size() > 0)
			this.commerciante = vendite.get(0).getCommerciante();

		DDTDTO dto1 = ddtService.create("", ddtInputData);
		String html = buildHtml(dto1, ddtInputData);

		outputStream = new ByteArrayOutputStream();

		PdfRendererBuilder builder = new PdfRendererBuilder();

		builder.withHtmlContent(html, null);
		builder.toStream(outputStream);
		builder.run();

		DDTDTO dto = ddtService.create(html, dto1);
		Long ddtId = dto.getId();
		for (GruppoVendite vendita : vendite) {

			vendita.setDdt(ddtInputData.getNumeroDDT());
			gruppoVenditeRepository.save(vendita);
		}

	}

	private String buildHtml(DDTDTO dto1, DDTInputData ddtInputData) {
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

		html.append(makeTestata1(dto1, ddtInputData));
		html.append(makeTestata2());
		html.append(makeTestata3(ddtInputData));
		html.append(makeTestata4());
		html.append(makeBinsBox(dto1, ddtInputData));

		html.append(makeFooter1(ddtInputData));
		html.append(makeFooter2(ddtInputData));

		html.append("""
				    </body>
				</html>
				""");

		return html.toString();

	}

	private Object makeTestata1(DDTDTO dto1, DDTInputData ddtInputData) {
		StringBuilder out = new StringBuilder();

		out.append("<table>");

		out.append("<tr>");

		out.append("<td>");
		out.append("<h4>Cedente: Ditta </h4>");
		insertAzienda(out, ddtInputData);
		out.append("</td>");

		out.append("<td>");
		insertTestataDestra(out, dto1, ddtInputData);
		out.append("</td>");

		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

//	private String buildHtml(DDTDTO dto1) {
//
//		StringBuilder html = new StringBuilder();
//
//		html.append("""
//				                <html>
//				                    <head>
//				                        <meta charset="UTF-8"/>
//
//				                        <style>
//
//				                            @page {
//
//				                                margin: 10mm;
//				                            }
//
//				                            body {
//				                                font-family: Arial, sans-serif;
//				                                font-size: 10px;
//				                                margin: 0;
//				                                padding: 0;
//				                            }
//
//				                            h1 {
//				                                color: #2c3e50;
//				                                margin-bottom: 20px;
//				                            }
//
//				                           table {
//				    width: 100%;
//				    border-collapse: collapse;
//				    table-layout: fixed;
//				}
//
//				                            th, td {
//				                                border: 1px solid #000;
//				                                padding: 4px;
//				                                text-align: left;
//				                                vertical-align: top;
//				                            }
//
//				                            th {
//				                                background-color: #f2f2f2;
//				                            }
//
//				                            .arrivo-box {
//				                                margin-bottom: 25px;
//				                                page-break-inside: avoid;
//				                            }
//
//				                            .section-title {
//				                                background-color: #dfe6e9;
//				                                font-weight: bold;
//				                            }
//
//				                            tr {
//				                                page-break-inside: avoid;
//				                            }
//
//				                        </style>
//
//				                    </head>
//
//				                    <body>
//				                """);
//
//	//	html.append(makeTestata1(dto1));
//		html.append(makeTestata2());
//		html.append(makeTestata3());
//		html.append(makeTestata4());
//		html.append(makeBinsBox(dto1));
//
//		html.append(makeFooter1());
//		html.append(makeFooter2());
//
//		html.append("""
//				    </body>
//				</html>
//				""");
//
//		return html.toString();
//	}
	private Object makeFooter2(DDTInputData ddtInputData) {
		StringBuilder out = new StringBuilder();

		Optional<Titolare> optTit = titolariRepository.findById(ddtInputData.getTitolareId());
		if (optTit.isEmpty())
			return out.toString();
		Titolare tit = optTit.get();

		out.append("<table>");
		out.append("<tr>");
		out.append("<td>");
		out.append("<h2>");
		out.append("PRODOTTO CERTIFICATO GLOBAL GAP");
		out.append("</h2>");
		out.append("<h2>");
		out.append("GGN " + tit.getGgn());
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

	private Object makeFooter1(DDTInputData ddtInputData) {
		StringBuilder out = new StringBuilder();
		Optional<Trasportatore> optra1 = trasportatoreRepository.findById(ddtInputData.getTrasportatore1Id());
		if (optra1.isEmpty())
			return out.toString();
		Trasportatore tra1 = optra1.get();

		out.append("<table>");
		out.append("<tr>");
		out.append("<td>");
		out.append("<h4>");
		out.append("1 Incaricato del trasporto (DITTA, INDIRIZZO, N.ALBO)");
		out.append("</h4>");
		out.append(tra1.getName() + "<br/>");
		out.append(tra1.getIndirizzo() + "<br/>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("</td>");
		out.append("<td>");
		out.append("<h4>");
		out.append("2 Incaricato del trasporto (DITTA, INDIRIZZO, N.ALBO)");
		out.append("</h4>");
		insertTrasportatore2(out, ddtInputData);

		out.append("</td>");
		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private void insertTrasportatore2(StringBuilder out, DDTInputData ddtInputData) {
		if (ddtInputData.getTrasportatore2Id() == null)
			return;

		Optional<Trasportatore> optra1 = trasportatoreRepository.findById(ddtInputData.getTrasportatore2Id());
		if (optra1.isEmpty())
			return;
		Trasportatore tra2 = optra1.get();

		out.append(tra2.getName() + "<br/>");
		out.append(tra2.getIndirizzo() + "<br/>");
	}

	private Object makeBinsBox(DDTDTO dto1, DDTInputData ddtInputData) {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<thead> ");
		out.append("<tr>");
		// <th style="width: 80%;"></th>

		out.append("<th>DESCRIZIONE PRODOTTO</th>");
		out.append("<th>Tipo</th>");
		out.append("<th>N.Colli</th>");
		out.append("<th>U.M. KG- Quantità</th>");

		out.append("</tr>");
		out.append("</thead> ");
		out.append("<tbody>");

		BigDecimal totPeso = BigDecimal.ZERO;
		for (GruppoVendite vendita : vendite)
			
				totPeso = processVenditaNormale(vendita, out);
//			else
//				totPeso = processVenditaGruppo(vendita, out, dto1, ddtInputData);

		addMorelines(out);
		addRiassuntoBins(out, vendite);
		addCessioneBeni(out, ddtInputData);
		out.append("</tbody>");
		out.append("</table>");
		makeFooter2(out, totColli, totPeso, ddtInputData);
		return out.toString();
	}

	private void addRiassuntoBins(StringBuilder out, List<GruppoVendite> vendite) {
		Map<String, Integer> map = new HashMap<>();

		for (GruppoVendite vendita : vendite) {
			List<BinsGruppoVendita> bins = vendita.getBins();
			for (BinsGruppoVendita bin : bins) {
				String key = bin.getBin().getName() + ","
						+ (bin.getNostraProprieta() ? "NOSTRA Prop." : "VOSTRA PROP.");
				Integer v = map.get(key);
				if (v == null)
					v = 0;
				v += bin.getNumBins();
				map.put(key, v);
			}
		}
		out.append("<table>");
		for (Entry<String, Integer> ent : map.entrySet()) {
			out.append("<tr>");

			String key = ent.getKey();
			Integer num = ent.getValue();
			String vals[] = key.split(",");

			out.append("<td>");
			out.append(vals[0]);
			out.append("</td>");
			out.append("<td>");
			out.append(vals[1]);
			out.append("</td>");
			out.append("<td>");
			out.append("" + num);
			out.append("</td>");

			out.append("</tr>");
		}
		out.append("</table>");
	}

	private void addCessioneBeni(StringBuilder out, DDTInputData ddtInputData) {
		String cess = ddtInputData.getCessioneBeniConPrezzo();
		if (cess == null || !cess.equals("1"))
			return;
		out.append("<tr>");
		out.append("<td>");
		out.append("CESSIONE DI BENI CON PREZZO");
		out.append("<p/>");
		out.append("DA DETERMINARE DM 15/11/75");
		out.append("</td>");
		out.append("</tr>");

	}

	private void addMorelines(StringBuilder out) {
		for (int i = 0; i < 10 - numRows; i++) {
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
	}

	private BigDecimal processVenditaGruppo(Vendita vendita, StringBuilder out, DDTDTO dto1,
			DDTInputData ddtInputData) {
		int numBins = 0;
		String nomeMerce = "";
		String bin = "";
		for (BinsVendite b : vendita.getBins()) {
			totColli += b.getNumBins();
			nomeMerce = vendita.getArrivo().getMerce().getName();
			bin = b.getBin().getName();

		}
		// gruppoVenditeRepository.findById(vendita.ge);
		out.append("<tr>");
		out.append("<td>");
		out.append(nomeMerce);
		out.append("</td>");
		out.append("<td>");
		out.append(bin);
		out.append("</td>");
		out.append("<td>");

		out.append(totColli);

		out.append("</td>");
		out.append("<td>");
		out.append(vendita.getGruppoVendite().getPesoLordoTotale());
		// totPeso = totPeso.add(b.getPesoLordo());
		out.append("</td>");

		out.append("</tr>");
		// totPeso = totPeso.add(vendita.getGruppoVendite().getPesoLordoTotale());

		GruppoVendite gruppo = vendita.getGruppoVendite();
		List<Vendita> lista = gruppo.getVendite();
		for (Vendita v : lista) {
			v.setDdt(ddtInputData.getNumeroDDT());
		}
		numRows++;
		return vendita.getGruppoVendite().getPesoLordoTotale();
	}

	private BigDecimal processVenditaNormale(GruppoVendite vendita, StringBuilder out) {
		for (BinsGruppoVendita b : vendita.getBins()) {
			out.append("<tr>");
			out.append("<td>");
			out.append(b.getMerce().getName());
			out.append("</td>");
			out.append("<td>");
			out.append(b.getBin().getName());
			out.append("</td>");
			out.append("<td>");
			out.append(b.getNumBins());
			totColli += b.getNumBins();
			out.append("</td>");
			out.append("<td>");
			if( b.getPesoLordo()!=null) {
			out.append(b.getPesoLordo());
			totPeso = totPeso.add(b.getPesoLordo());}
			out.append("</td>");

			out.append("</tr>");
			numRows++;
		}
		return totPeso;
	}

	private void makeFooter2(StringBuilder out, int totColli2, BigDecimal totPeso2, DDTInputData ddtInputData) {
		out.append("<table>");
		out.append("<tr>");
		// out.append("<td colspan=\"3\">N.Colli</td>");
		out.append("<td  >");
		out.append("PAESE DI ORIGINE<br/>dei prodotti<br/>ITALIA");
		out.append("</td>");
		out.append("<td  >");
		out.append("CATEGORIA<br/>" + ddtInputData.getCategoria());
		out.append("</td>");
		out.append("<td  >");
		out.append("Aspetto esteriore dei beni<br/>" + ddtInputData.getAspettoEsteriore());
		out.append("</td>");
		out.append("<td  class=\"text-end fw-bold\">n.Colli<br/>" + totColli2 + "</td>");
		out.append("<td  class=\"text-end fw-bold\" >Peso kg.<br/>" + totPeso2 + "</td>");
		out.append("</tr>");
		out.append("</table>");
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

	private Object makeTestata3(DDTInputData ddtInputData) {
		StringBuilder out = new StringBuilder();

		out.append("<table>");
		out.append("<tr>");

		out.append("<td>");
		out.append("<b>Secondo cessionario:</b>");
		out.append("<p/>");
		out.append("<p/>");
		

	
		out.append(commerciante.getName());
		out.append("<br/>" + clean(commerciante.getIndirizzo()));
		out.append("</td>");
		out.append("<td>");
		out.append("Luogo di destinazione<p/>");
		out.append(ddtInputData.getLuogoDiDestinazione());
		out.append("</td>");
		out.append("</tr>");
		out.append("</table>");

		return out.toString();
	}

	private String makeTestata2() {
		StringBuilder out = new StringBuilder();
		out.append("<table>");
		out.append("<tr>");

		out.append("<td>");
		out.append("<b>Primo cessionario:</b>");

		out.append("<p/>");
		out.append("<p/>");
		out.append(conf.getPrimoCessionario() + "<br/>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("<br/>");
		out.append("</td>");

		out.append("</tr>");
		out.append("</table>");
		return out.toString();
	}

	private void insertTestataDestra(StringBuilder out, DDTDTO dto, DDTInputData ddtInputData) {
		out.append("<h2>");
		out.append("D.T.T.  SCHEDA DI TRASPORTO");
		out.append("</h2>");
		out.append("Trasporto a mezzo " + ddtInputData.getTrasportoAmezzo());
		out.append("<table>");

		out.append("<tr>");

		out.append("<td>");
		out.append("<h2>");
		out.append("N." + ddtInputData.getNumeroDDT());
		out.append("</h2>");
		out.append("</td>");
		out.append("<td>");
		out.append("<h2>");
		out.append("Del " + LocalDate.now());
		out.append("</h2>");
		out.append("</td>");
		out.append("</tr>");
		out.append("</table>");

	}

	private void insertAzienda(StringBuilder out, DDTInputData ddtInputData) {
		Long titId = ddtInputData.getTitolareId();
		Optional<Titolare> optTit = titolariRepository.findById(titId);
		if (optTit.isEmpty())
			return;
		Titolare titolare = optTit.get();
		out.append("<p><b>");
		out.append(titolare.getName() + "</b>");
		out.append("</p>");
		out.append("<p>");
		out.append(titolare.getIndirizzo());
		out.append("</p>");
		out.append("<p>");
		out.append(titolare.getPec());
		out.append("</p>");
		out.append("<p>");
		out.append(titolare.getCodFiscale());
		out.append("</p>");
		out.append("<p>");
		out.append(titolare.getpIva());
		out.append("</p>");
	}

}
