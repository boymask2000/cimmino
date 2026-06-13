package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsGruppoVendita;
import com.cimmino.shop.database.BinsGruppoVenditeRepository;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.BinsVenditeRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.BinsGruppoVenditaDTO;
import com.cimmino.shop.database.dto.BinsVenditaDTO;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.MagazzinoRow;
import com.cimmino.shop.service.MagazzinoService;
import com.cimmino.shop.service.VenditeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/gruppovendite")
public class GruppoVenditeController {
	@Autowired
	MagazzinoService magazzinoService;
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;
	@Autowired
	BinsVenditeRepository binsVenditeRepository;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	BinsGruppoVenditeRepository binsGruppoVenditeRepository;
	@Autowired
	VenditeService venditeService;

	@GetMapping("/new")
	public String newGruppo(Model model) {

		GruppoVendite gruppo = new GruppoVendite();

		List<Map<String, Object>> binsJs = binRepository.findAll().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getId());
			m.put("name", b.getName());
			m.put("pesoLordo", b.getPesoLordo());
			m.put("tara", b.getTara());
			return m;
		}).toList();

//		List<Map<String, Object>> arriviJs = binRepository.findAll().stream().map(b -> {
//			Map<String, Object> m = new HashMap<>();
//			m.put("id", 0);
//			m.put("name", "pp");
//			m.put("pesoLordo", 0);
//			m.put("tara", 0);
//			return m;
//		}).toList();
		List<Map<String, Object>> arriviJs = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> merceJs = merceRepository.findAll().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getMerce_id());
			m.put("name", b.getName());

			return m;
		}).toList();

		List<MagazzinoRow> magazzinorows = magazzinoService.dump();

		model.addAttribute("magazzinorows", magazzinorows);

		model.addAttribute("currData", LocalDate.now());
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("configurazione", configurazioneService.getConfigurazione());
		model.addAttribute("gruppo", gruppo);
		model.addAttribute("binsJs", binsJs);
		model.addAttribute("arriviJs", arriviJs);
		model.addAttribute("merceJs", merceJs);
		return "new_gruppo";
	}

	public List<String> getArrivi(@RequestParam Long merceId, @RequestParam Long binId) {

		List<String> out = new ArrayList<String>();

		List<Arrivi> arrivi = arriviRepository.findAll();

		if (merceId != 0) {
			arrivi = arrivi.stream().filter(p -> p.getMerce().getMerce_id() == merceId).toList();
		}

		for (Arrivi arrivo : arrivi) {
			List<BinsArrivi> bins = arrivo.getBins();
			if (binId != 0) {
				bins = bins.stream().filter(p -> p.getBin().getId() == binId).toList();
			}

			for (BinsArrivi bin : bins) {
				String v = arrivo.getId() + "," + arrivo.getData() + "," + arrivo.getMerce().getName() + ","
						+ bin.getBin().getName() + "," + bin.getNumBins();
				out.add(v);
			}
		}

		return out;
	}

	@GetMapping("/getArrivi")
	@ResponseBody
	public List<String> getMagazzino(@RequestParam Long merceId, @RequestParam Long binId) {

		List<String> out = new ArrayList<String>();

		List<MagazzinoRow> rows = magazzinoService.dump();

		rows = rows.stream().filter(p -> p.getNum() > 0).toList();

		if (merceId != 0) {
			rows = rows.stream().filter(p -> p.getMerceId() == merceId).toList();
		}

		if (binId != 0) {
			rows = rows.stream().filter(p -> p.getBinId() == binId).toList();
		}
		for (MagazzinoRow row : rows) {

			String v = row.getArrivoId() + "," + row.getDate() + "," + row.getNomeMerce() + "," + row.getBin() + ","
					+ row.getNum();
			out.add(v);

		}

		return out;
	}

	@GetMapping("/show/{id}")
	public String showGruppo(@PathVariable Long id, Model model) {
		Optional<Vendita> opven = venditeRepository.findById(id);
		if (opven.isEmpty())
			return "";
		Vendita ven = opven.get();
		GruppoVendite gruppo = ven.getGruppoVendite();

		model.addAttribute("gruppo", gruppo);

		return "handle_gruppo";
	}

	@PostMapping("/save")
	public String save( //
			@ModelAttribute GruppoVendite gr, //
			@RequestParam String binsJson, //

			Model model) {

		ObjectMapper mapper = new ObjectMapper();
		List<BinsGruppoVenditaDTO> binsd = new ArrayList<BinsGruppoVenditaDTO>();
		try {
			binsd = mapper.readValue(binsJson, new TypeReference<List<BinsGruppoVenditaDTO>>() {
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		Long commId=binsd.get(0).getCommerciante();
		Optional<Commerciante> c = commercianteRepository.findById(commId);
		Commerciante comm = c.get();
		gr.setCommerciante(comm);

		for (BinsGruppoVenditaDTO dto : binsd) {
			String vals[] = dto.getArriviSelect().split(",");
			String sArrivoId = vals[0];
			String sDate = vals[1];
			String sNomeMerce = vals[2];
			String sbin = vals[3];
			String snuBin = vals[4];
			
			Vendita vendita = new Vendita();
			vendita.setCommerciante(comm);
			vendita.setArrivo(arriviRepository.findById(Long.parseLong(sArrivoId)).get());
			vendita.setData(LocalDate.parse(sDate));
		//	vendita.set
			venditeService.eseguiCalcoli(vendita);
			venditeService.save(vendita, commId);
			

		}

		gr.setPeso_lordo(gr.getPesoLordoTotale());
//		venditaTotale.setNettoDiScarto(sommaNettoScarto);
//		venditaTotale.setNettoDiTara(sommaNettoTara);
//		venditaTotale.setMedia(media);

		gr.setData(gr.getData());
	//	gr = gruppoVenditeRepository.save(gr);
		List<BinsGruppoVendita> bins = createBinsVendite(gr, binsd);
		gr.setBins(bins);
		int totaleBins = bins.stream().mapToInt(b -> b.getNumBins()).sum();
gr.setNumeroTotaleBins(totaleBins);
		gr = gruppoVenditeRepository.save(gr);
		
//		GruppoVendite gruppo = gruppoVenditeRepository.findById(gr.getId()).get();
//		List<Vendita> vendite = venditeRepository.findByGruppoVendite(gruppo);
//
//		List<BinsVendite> bins = new ArrayList<>();
//		Vendita unaVendita = vendite.get(0);
//		for (Vendita ven : vendite) {
//
//			bins.addAll(ven.getBins());
//		}
//
//		BigDecimal sommaNettoScarto = vendite.stream().map(Vendita::getNettoDiScarto).reduce(BigDecimal.ZERO,
//				BigDecimal::add);
//		BigDecimal sommaNettoTara = vendite.stream().map(Vendita::getNettoDiTara).reduce(BigDecimal.ZERO,
//				BigDecimal::add);
//
//		BigDecimal sommaMedie = vendite.stream().map(Vendita::getMedia).reduce(BigDecimal.ZERO, BigDecimal::add);
//		BigDecimal media = sommaMedie.divide(new BigDecimal(vendite.size()));
//
//		Vendita venditaTotale = new Vendita();
////		venditaTotale.setGruppoVendite(gruppo);
//		venditaTotale.setIsMasterGruppo(true);
//		venditaTotale.setArrivo(unaVendita.getArrivo());
//		venditaTotale.setBins(bins);
//		venditaTotale.setPeso_lordo(gr.getPesoLordoTotale());
//		venditaTotale.setNettoDiScarto(sommaNettoScarto);
//		venditaTotale.setNettoDiTara(sommaNettoTara);
//		venditaTotale.setMedia(media);
//		venditaTotale.setCommerciante(unaVendita.getCommerciante());
//		venditaTotale.setData(unaVendita.getData());
//		venditaTotale = venditeRepository.save(venditaTotale);
//
//		for (Vendita ven : vendite) {
//			List<BinsVendite> binss = ven.getBins();
//			for (BinsVendite binVen : binss) {
//				binVen.setVendita(venditaTotale);
//				binsVenditeRepository.save(binVen);
//			}
//			bins.addAll(ven.getBins());
//		}
//		venditaTotale = venditeRepository.save(venditaTotale);

//		gruppo.getVendite().add(venditaTotale);
//		model.addAttribute("gruppo", gruppo);
//
//		gruppo.setStatus("1");
//		gruppo.setPesoLordoTotale(gr.getPesoLordoTotale());
//		gruppoVenditeRepository.save(gruppo);
		return "home";
	}

	private List<BinsGruppoVendita> createBinsVendite(GruppoVendite gr, List<BinsGruppoVenditaDTO> binsd) {
		List<BinsGruppoVendita> out = new ArrayList<>();

		for (BinsGruppoVenditaDTO dto : binsd) {
			String vals[] = dto.getArriviSelect().split(",");
			String sArrivoId = vals[0];
			String sDate = vals[1];
			String sNomeMerce = vals[2];
			String sbin = vals[3];
			String snuBin = vals[4];

			BinsGruppoVendita bin = new BinsGruppoVendita();

			bin.setBin(binRepository.findByName(sbin));
			bin.setNumBins(Integer.parseInt(snuBin));
			bin.setMerce(merceRepository.findbyName(sNomeMerce));

			
		//	binsGruppoVenditeRepository.save(bin);
			
			out.add(bin);
		}

		return out;
	}

//
//	@GetMapping("/merge")
//	public String merge(Model model) {
//
//		Master master = new Master();
//
//		model.addAttribute("master", master);
//
//		return "getMasterAddress";
//	}

}