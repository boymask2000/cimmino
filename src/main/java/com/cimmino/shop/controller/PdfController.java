package com.cimmino.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.service.print.PdfService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/pdf")
public class PdfController {
	@Autowired
	PdfService pdfService;
	@Autowired
	ArriviRepository arriviRepository;

	@GetMapping("/generale_arrivi")
	public ResponseEntity<byte[]> generale_arrivi() throws Exception {
		List<Arrivi> lista = arriviRepository.findAll();
		byte[] pdf = pdfService.generatePdfGeneraleArrivi(lista);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fattura.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@GetMapping("/vendita_dettaglio/{id}")
	public ResponseEntity<byte[]> vendita_dettaglio(@PathVariable Long id) throws Exception {
	

		
		byte[] pdf = pdfService.generatePdfDettaglioVendita(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fattura.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@PostConstruct
	public void init() {

	}
}