package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;

import com.cimmino.shop.database.dto.DDTDTO;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class DDTPrinterRAW extends BasePrinter implements HasOutputStream {

	public DDTPrinterRAW(DDTDTO ddt) throws Exception {
		String html = ddt.getBody();
		outputStream = new ByteArrayOutputStream();

		PdfRendererBuilder builder = new PdfRendererBuilder();

		builder.withHtmlContent(html, null);
		builder.toStream(outputStream);
		builder.run();
		
	}

}
