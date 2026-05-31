package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.TrasportatoreRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.print.DDTInputData;

@Controller
@RequestMapping("/ddt")
public class DDTController {
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	TrasportatoreRepository trasportatoreRepository;
	
	
	@GetMapping("/home")
	public String ddt(Model model) {
		LocalDate today = LocalDate.now();

		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);

		// model.addAttribute("status", statusService.getStatus());

//		List<User> users = usersRepo.findAll();
//		model.addAttribute("nusers", users.size());
		model.addAttribute("commercianti", commercianteRepository.findAll());
		return "ddt_commercianti";
	}
	@GetMapping("/vendite_commercianteNoDDT/{id}")
	public String vendite_commercianteNoDDt(@PathVariable Long id, Model model) {
		Optional<Commerciante> opt_comm = commercianteRepository.findById(id);
		Commerciante comm = opt_comm.get();
		List<Vendita> vendite = venditeRepository.findVenditeDiCommercianteSenzaDDT(comm.getCommerciante_id());
		model.addAttribute("vendite", vendite);
		model.addAttribute("commerciante", comm);
		return "ddt_vendite_commerciante_noddt";
	}
	@GetMapping("/vendite_commerciante/{id}")
	public String vendite_commerciante(@PathVariable Long id, Model model) {
		Optional<Commerciante> opt_comm = commercianteRepository.findById(id);
		Commerciante comm = opt_comm.get();
		List<Vendita> vendite = venditeRepository.findVenditeDiCommerciante(comm.getCommerciante_id());
		model.addAttribute("vendite", vendite);
		model.addAttribute("commerciante", comm);
		return "ddt_vendite_commerciante";
	}
	@PostMapping("/vendite/selezionate")
	public String gestisciSelezionati(@RequestParam("ids") List<Long> ids,
			@RequestParam("commercianteId")Long commercianteId,
			 RedirectAttributes redirectAttributes) {
		Optional<Commerciante> opt_comm = commercianteRepository.findById(commercianteId);
		Commerciante comm = opt_comm.get();
		
	    System.out.println(ids);
	    redirectAttributes.addAttribute("commercianteId", commercianteId);

	    // passa tutti gli id selezionati
	    redirectAttributes.addAttribute("ids", ids);


	    return "redirect:/pdf/ddt/vendite";
	}
	@GetMapping("/viewddt/{id}")
	public String viewDDT(@PathVariable Long id,  RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("id", id);
		return "redirect:/pdf/ddt/showddt";
	}
	
	@GetMapping("/parameters")
	public String parameters(@RequestParam("ids") List<Long> ids,
			@RequestParam("commercianteId")Long commercianteId, Model model) {
		DDTInputData  ddtInputData=new DDTInputData();
		ddtInputData.setIds(ids);
		ddtInputData.setCommercianteId(commercianteId);
		model.addAttribute("ddtInputData", ddtInputData);
		model.addAttribute("trasportatori", trasportatoreRepository.findAll());
	
		return "ddt_input_data";
	}
	@PostMapping("/vendite/selezionate1")
	public String selezionate1(
			@ModelAttribute DDTInputData ddtInputData,
			 RedirectAttributes redirectAttributes) {
		Long commercianteId=ddtInputData.getCommercianteId();
		List<Long> ids = ddtInputData.getIds();
//		Optional<Commerciante> opt_comm = commercianteRepository.findById(commercianteId);
//		Commerciante comm = opt_comm.get();
		
	    System.out.println(ids);
	    redirectAttributes.addAttribute("commercianteId", commercianteId);

	    // passa tutti gli id selezionati
	    redirectAttributes.addAttribute("ids", String.join(",",
	            ids.stream().map(String::valueOf).toList()));

	    redirectAttributes.addFlashAttribute("ddtInputData", ddtInputData);

	    return "redirect:/pdf/ddt/vendite1";
	}
}
