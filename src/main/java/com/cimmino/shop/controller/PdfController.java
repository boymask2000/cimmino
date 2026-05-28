package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/filter_arrivi2")
	public ResponseEntity<byte[]> filter_arrivi(Model model) throws Exception {

		List<Arrivi> lista = arriviRepository.findAll();
		byte[] pdf = pdfService.generatePdfGeneraleArrivi(lista);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=arrivi.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@GetMapping("/filter_arrivi")
	public ResponseEntity<byte[]> stampaFiltrata(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate)
			throws Exception {

		List<Arrivi> lista = arriviRepository.cerca(startDate, endDate);

		byte[] pdf = pdfService.generatePdfGeneraleArrivi(lista);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=arrivi.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@GetMapping("/generale_arrivi")
	public ResponseEntity<byte[]> generale_arrivi() throws Exception {
		List<Arrivi> lista = arriviRepository.findAll();
		byte[] pdf = pdfService.generatePdfGeneraleArrivi(lista);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=arrivi.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@GetMapping("/vendita_dettaglio/{id}")
	public ResponseEntity<byte[]> vendita_dettaglio(@PathVariable Long id) throws Exception {

		byte[] pdf = pdfService.generatePdfDettaglioVendita(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendita.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}
	
	@GetMapping("/ddt/{id}")
	public ResponseEntity<byte[]> ddt(@PathVariable Long id) throws Exception {

		byte[] pdf = pdfService.generateDDT(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ddt.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	@GetMapping("/ddt/vendite")
	public ResponseEntity<byte[]> ddtForVendite(@RequestParam List<Long> ids, Model model) throws Exception {

		byte[] pdf = pdfService.generateDDT4Vendite(ids);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ddt.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}
	@GetMapping("/ddt/showddt")
	public ResponseEntity<byte[]> showddt(@RequestParam Long id, Model model) throws Exception {

		byte[] pdf = pdfService.onlyShow(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ddt.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	
	
	@PostConstruct
	public void init() {

	}
}