package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.BinMovimentoView;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.MovimentiVuotiRepository;
import com.cimmino.shop.database.MovimentoVuoto;
import com.cimmino.shop.mappers.BinsArriviMapper;
import com.cimmino.shop.service.MovimentiVuotoService;

@Controller
@RequestMapping("/movimentivuoti")
public class MovimentiVuotiController {
	@Autowired
	MovimentiVuotiRepository movimentiVuotiRepository;
	@Autowired
	BinsArriviMapper binsArriviMapper;
	@Autowired
	BinRepository binRepository;
	@Autowired
	MovimentiVuotoService movimentiVuotoService;

	@GetMapping("/lista")
	public String listaCommercianti(RedirectAttributes redirectAttributes) {

		return "redirect:/movimentivuoti/view";
	}
	@GetMapping("/searchResult")
	public String view(
	        @RequestParam(required = false) LocalDate dataDa,
	        @RequestParam(required = false) LocalDate dataA,
	        @RequestParam(required = false) String bin,
	        Model model) {
		


	    model.addAttribute("operazioni",
	    		movimentiVuotoService.findFiltered(dataDa, dataA, bin));

//	    model.addAttribute("riepilogo",
//	    		movimentiBinService.riepilogo());
//
//	    model.addAttribute("bins",
//	    		movimentiBinService.findAll());

	//    return "movimenti-bin";
	    List<BinMovimentoView> riepilogo = movimentiVuotiRepository.getRiepilogoMovimenti();

		model.addAttribute("riepilogo", riepilogo);
	
		model.addAttribute("bins", binRepository.findAll());
		
		model.addAttribute("dataDa", dataDa);
		model.addAttribute("dataA",dataA);
		model.addAttribute("bin",bin);
		
		return "movimentiVuoti";
	}

	@GetMapping("/view")
	public String view(Model model) {

		List<MovimentoVuoto> ll;

		ll = movimentiVuotiRepository.findAll();
		
		List<BinMovimentoView> riepilogo = movimentiVuotiRepository.getRiepilogoMovimenti();

		model.addAttribute("bins", binRepository.findAll());
		model.addAttribute("riepilogo", riepilogo);
		model.addAttribute("operazioni", ll);
		model.addAttribute("dataDa", LocalDate.now());
		model.addAttribute("dataA", LocalDate.now());
		return "movimentiVuoti";
	}
	@GetMapping("/reset")
	public String reset(Model model) {
		LocalDate dataFrom = LocalDate.of(2026, 1, 1);
		LocalDate dataTo = LocalDate.of(2126, 1, 1);
		
		List<MovimentoVuoto> ll;

		ll = movimentiVuotiRepository.findAll();
		
		List<BinMovimentoView> riepilogo = movimentiVuotiRepository.getRiepilogoMovimenti();

		model.addAttribute("bins", binRepository.findAll());
		model.addAttribute("riepilogo", riepilogo);
		model.addAttribute("operazioni", ll);
		model.addAttribute("dataDa", dataFrom);
		model.addAttribute("dataA", dataTo);
		return "movimentiVuoti";
	}
	@GetMapping("/sposta/{id}")
	public String spostaBin(@PathVariable Long id,Model model)  {
		
		List<Map<String, Object>> binsJs = binRepository.findAll().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getId());
			m.put("name", b.getName());
			m.put("pesoLordo", b.getPesoLordo());
			m.put("tara", b.getTara());
			return m;
		}).toList();
		
		List<BinsArrivi> binsarrivi = new ArrayList<BinsArrivi>();

		model.addAttribute("binsJs", binsJs);
		model.addAttribute("binsarrivi", binsarrivi);
		model.addAttribute("operazione", id);
		
	
		return "sposta_bin";
	}
	@PostMapping("/save")
	public String save(@ModelAttribute MovimentiForm form, 
			@RequestParam(name = "operazione") Long operazione,
			Model model) {

	    List<BinMovimento> binsarrivi = form.getBinsarrivi();

//	    System.out.println(binsarrivi.size());
//	    System.out.println("oper = "+operazione);
//	    for( BinsArrivi bin: binsarrivi) {
//	    	System.out.println(bin.getBin().getName());
//	    	System.out.println(bin.getNumBins());
//	    }
	    movimentiVuotoService.register(binsarrivi, operazione);

	    return "redirect:/movimentivuoti/view";
	}
	
}
