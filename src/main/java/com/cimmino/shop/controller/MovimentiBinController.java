package com.cimmino.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.cimmino.shop.database.MovimentiBinRepository;
import com.cimmino.shop.database.MovimentoBin;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.mappers.BinsArriviMapper;
import com.cimmino.shop.service.MovimentiBinService;

@Controller
@RequestMapping("/movimentibin")
public class MovimentiBinController {
	@Autowired
	MovimentiBinRepository movimentiBinRepository;
	@Autowired
	BinsArriviMapper binsArriviMapper;
	@Autowired
	BinRepository binRepository;
	@Autowired
	MovimentiBinService movimentiBinService;

	@GetMapping("/lista")
	public String listaCommercianti(RedirectAttributes redirectAttributes) {

		return "redirect:/movimentibin/view";
	}

	@GetMapping("/view")
	public String view(Model model) {

		List<MovimentoBin> ll;

		ll = movimentiBinRepository.findAll();
		
		List<BinMovimentoView> riepilogo = movimentiBinRepository.getRiepilogoMovimenti();

		model.addAttribute("riepilogo", riepilogo);
		model.addAttribute("operazioni", ll);

		return "movimentibin";
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

	    List<BinsArrivi> binsarrivi = form.getBinsarrivi();

//	    System.out.println(binsarrivi.size());
//	    System.out.println("oper = "+operazione);
//	    for( BinsArrivi bin: binsarrivi) {
//	    	System.out.println(bin.getBin().getName());
//	    	System.out.println(bin.getNumBins());
//	    }
	    movimentiBinService.register(binsarrivi, operazione);

	    return "redirect:/movimentibin/view";
	}
	
}
