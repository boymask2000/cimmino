package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.dto.ArriviDTO;
import com.cimmino.shop.database.dto.BinsArriviDTO;
import com.cimmino.shop.mappers.ArriviMapper;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class ArriviService {

	@Autowired
	private ArriviRepository arriviRepository;

	@Autowired
	private BinRepository binRepository;

	@Autowired
	BinArriviService binArriviService;
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	private BinsArriviRepository binsArriviRepository;
	@Autowired
	private ArriviMapper arriviMapper;
	
	

	public ArriviDTO getById(Long id) {
		Arrivi a = arriviRepository.findById(id).orElseThrow();

		return arriviMapper.toDto(a);
	}

	public void eseguiCalcoli(Arrivi arrivo) {
		BigDecimal totalePesoNetto = BigDecimal.ZERO;
		BigDecimal totalePesoLordo = BigDecimal.ZERO;

		for (BinsArrivi b : arrivo.getBins()) {
			Bin bin = b.getBin();
			totalePesoLordo = totalePesoLordo.add(bin.getPesoLordo());
			totalePesoNetto = totalePesoNetto.add(bin.getPesoLordo().subtract(bin.getTara()));
		}
		arrivo.setPeso_lordo(totalePesoLordo);
		arrivo.setPeso_netto(totalePesoNetto);
	}

	public void calcSums(List<Arrivi> risultati) {

		for (Arrivi arr : risultati) {

			int avail = 0;
			Map<String, BigDecimal> sums = arr.getSums();

			for (Vendita v : arr.getVendite()) {
				if (sums.get("NETTO") == null)
					sums.put("NETTO", BigDecimal.ZERO);
				if (sums.get("LORDO") == null)
					sums.put("LORDO", BigDecimal.ZERO);
				BigDecimal lordo = sums.get("LORDO");
				if (v.getPeso_lordo() != null)
					lordo = lordo.add(v.getPeso_lordo());
				sums.put("LORDO", lordo);
				BigDecimal netto = sums.get("NETTO");
				if (v.getNettoDiTara() != null)
					netto = netto.add(v.getNettoDiTara());
				sums.put("NETTO", netto);

			}

			for (BinsArrivi b : arr.getBins()) {
				int k = binArriviService.calcAvail(b.getBin().getId(), arr.getId());
				avail += k;
			}
			sums.put("AVAIL", new BigDecimal(avail));
		}
	}

	@Transactional
	public void save(ArriviDTO dto) {

		Arrivi arrivo = arriviMapper.toEntity(dto);

		arrivo = arriviRepository.save(arrivo);

		if (dto.getBins() != null) {

			for (BinsArriviDTO b : dto.getBins()) {

				BinsArrivi entity = new BinsArrivi();

				entity.setArrivo(arrivo);
				entity.setBin(binRepository.findById(b.getBinId()).orElseThrow());

				entity.setNumBins(b.getNumBins());
				entity.setPesoLordo(b.getPesoLordo());
				entity.setPesoNetto(b.getPesoNetto());

				binsArriviRepository.save(entity);
			}
		}
	}

	public void calcNumTotaleBins(List<Arrivi> arr) {
	

	}

	@Transactional
	public void delete(Long id) {
		Optional<Arrivi> opt = arriviRepository.findById(id);
		if (opt.isEmpty())
			return;
		Arrivi arrivo = opt.get();

		movimentiBinService.unregister(arrivo, "Cancellazione Arrivo");

		List<BinsArrivi> bins = arrivo.getBins();
		for (BinsArrivi bin : bins)
			binArriviService.delete(bin);

		arriviRepository.delete(arrivo);
	}
	public List<ArriviDTO> getAll() {
		List<Arrivi> lista = arriviRepository.findAll();
		return arriviMapper.toDtoList(lista);
	}
	public List<ArriviDTO> cercaPerInstallation(String installId) {
		List<Arrivi> lista = arriviRepository.cercaPerInstallation(installId);
		return arriviMapper.toDtoList(lista);
	}
	public String cercaPerInstallationAsJSON(String installId) {
		List<Arrivi> lista = arriviRepository.cercaPerInstallation(installId);
		
		List<ArriviDTO> dtos=new ArrayList<ArriviDTO>(); 
		for( Arrivi arr: lista) {
			dtos.add(arriviMapper.toDto(arr));
		}
		
		ObjectMapper mapper = new ObjectMapper();

      //  mapper.registerModule(new JavaTimeModule());

        String json = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(dtos);

        System.out.println(json);
		return json;
	}

	@Transactional
	public void addBin(Long arrivoId,
	                   Integer numBins,
	                   Long binId,
	                   BigDecimal pesoLordo,
	                   BigDecimal pesoNetto) {

	     Arrivi arrivo = arriviRepository.findById(arrivoId)
	            .orElseThrow();

	    Bin bin = binRepository.findById(binId)
	            .orElseThrow();

	    BinsArrivi ab = new BinsArrivi();
	    
	    BigDecimal media=BigDecimal.ZERO;
	    if( numBins>0) {
	    	media= pesoLordo.divide(new BigDecimal(numBins));
	    }

	    ab.setArrivo(arrivo);
	    ab.setBin(bin);
	    ab.setNumBins(numBins);
	    ab.setPesoLordo(pesoLordo);
	    ab.setPesoNetto(pesoNetto);
	    ab.setMedia(media);

	    arrivo.getBins().add(ab);

	    arriviRepository.save(arrivo);
	}
}