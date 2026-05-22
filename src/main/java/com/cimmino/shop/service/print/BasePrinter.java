package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;

public class BasePrinter {
	protected ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	
	
	public ByteArrayOutputStream getOutputStream() {
		return outputStream;
	}
	
	public String clean(Object value) {

		if (value == null) {
			return "";
		}

		return value.toString();
	}

}
