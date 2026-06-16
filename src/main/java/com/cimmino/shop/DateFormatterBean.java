package com.cimmino.shop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("fmt")
public class DateFormatterBean {

	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public String date(LocalDate d) {
		return d == null ? "" : d.format(DATE);
	}
}