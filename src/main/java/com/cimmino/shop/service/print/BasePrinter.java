package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;

public class BasePrinter {
	protected ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	
	
	public ByteArrayOutputStream getOutputStream() {
		return outputStream;
	}
	
	public String clean(Object value) {
System.out.println("Clean1 "+value);
		if (value == null) {
			return "";
		}
		System.out.println("Clean1 "+value.toString());
		return value.toString();
	}

}
