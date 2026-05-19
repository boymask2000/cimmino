package com.cimmino.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.PdfService;
import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;

import jakarta.annotation.PostConstruct;

@RestController
public class PdfController {
	@Autowired
	PdfService pdfService;
	@Autowired
	ArriviRepository arriviRepository;

	@GetMapping("/pdf")
	public ResponseEntity<byte[]> pdf() throws Exception {
		List<Arrivi> lista = arriviRepository.findAll();
		byte[] pdf = pdfService.generatePdf(lista);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fattura.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@PostConstruct
	public void init() {

	}
}