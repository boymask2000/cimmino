package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsGruppoVendita;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.BinsVenditeRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.dto.BinsGruppoVenditaDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class GruppoVenditeService {
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	VenditeService venditeService;
	@Autowired
	BinRepository binRepository;
	@Autowired
	BinsVenditeRepository binsVenditeRepository;
	@Autowired
	MerceRepository merceRepository;

	@Transactional
	public void saveGruppoVendite(GruppoVendite gr, LocalDate currData, String binsJson) {
		gr.setData(currData);
		ObjectMapper mapper = new ObjectMapper();
		List<BinsGruppoVenditaDTO> binsd = new ArrayList<BinsGruppoVenditaDTO>();
		try {
			binsd = mapper.readValue(binsJson, new TypeReference<List<BinsGruppoVenditaDTO>>() {
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		Long commId = binsd.get(0).getCommerciante();
		Optional<Commerciante> c = commercianteRepository.findById(commId);
		Commerciante comm = c.get();
		gr.setCommerciante(comm);

		BigDecimal sommaNettoTara = BigDecimal.ZERO;
		BigDecimal sommaPesoLordo = BigDecimal.ZERO;
		BigDecimal sommaNettoScarto = BigDecimal.ZERO;
		BigDecimal sommaImporto = BigDecimal.ZERO;

		for (BinsGruppoVenditaDTO dto : binsd) {
			String vals[] = dto.getArriviSelect().split(",");
			String sArrivoId = vals[0];
			String sDate = vals[1];
			String sNomeMerce = vals[2];
			String sbin = vals[3];
			String snuBin = vals[4];

			int numBin = Integer.parseInt(snuBin);
			Arrivi arrivo = arriviRepository.findById(Long.parseLong(sArrivoId)).get();

			BigDecimal scarto = new BigDecimal(1);
			BigDecimal perc = dto.getScarto().divide(new BigDecimal(100));
			scarto = scarto.subtract(perc);
			BigDecimal nettoScarto = dto.getPesoNetto().multiply(scarto);
			BigDecimal importo = nettoScarto.multiply(dto.getPrezzo());
			sommaImporto = sommaImporto.add(importo);

			Vendita vendita = new Vendita();
			vendita.setCommerciante(comm);
			vendita.setArrivo(arrivo);
			vendita.setData(gr.getData());
			vendita.setNumeroTotaleBins(Integer.parseInt(snuBin));
			vendita.setPeso_lordo(dto.getPesoLordo());
			vendita.setNettoDiTara(dto.getPesoNetto());
			vendita.setNettoDiScarto(nettoScarto);
			vendita.setPrezzo(dto.getPrezzo());
			vendita.setImporto(importo);
			vendita.setScarto(dto.getScarto());
			// vendita.set
			venditeService.eseguiCalcoli(vendita);

			sommaPesoLordo = sommaPesoLordo.add(dto.getPesoLordo());
			sommaNettoTara = sommaNettoTara.add(dto.getPesoNetto());

			if (gr.getScarto() != null) {
				BigDecimal scarto1 = new BigDecimal(1);
				BigDecimal perc1 = gr.getScarto().divide(new BigDecimal(100));
				scarto = scarto1.subtract(perc1);

				sommaNettoScarto = sommaNettoScarto.add(dto.getPesoLordo().multiply(scarto));
				vendita.setNettoDiScarto(dto.getPesoLordo().multiply(scarto));

			}
			vendita = venditeService.save(vendita, commId);

			Bin bb = binRepository.findbyName(sbin);
			BinsVendite bArr = new BinsVendite();
			bArr.setVendita(vendita);
			bArr.setBin(bb);
			bArr.setNumBins(dto.getNumBins());
			bArr.setPesoLordo(dto.getPesoLordo());
			bArr.setPesoNetto(dto.getPesoNetto());
			bArr.setNostraProprieta(dto.getNostraProprieta());

			binsVenditeRepository.save(bArr);
		}

		// gr.setNettoDiScarto(sommaNettoScarto);
		gr.setImporto(sommaImporto);
		gr.setNettoDiTara(sommaNettoTara);

		gr.setPeso_lordo(sommaPesoLordo);
//		venditaTotale.setNettoDiScarto(sommaNettoScarto);
//		venditaTotale.setNettoDiTara(sommaNettoTara);
//		venditaTotale.setMedia(media);

		List<BinsGruppoVendita> bins = createBinsVendite(gr, binsd);
		for (BinsGruppoVendita grp : bins) {
			grp.setGruppoVendita(gr);
		}
		gr.setBins(bins);
		int totaleBins = bins.stream().mapToInt(b -> b.getNumBins()).sum();
		gr.setNumeroTotaleBins(totaleBins);

		calcolaMedia(gr);

		gr = gruppoVenditeRepository.save(gr);

		movimentiBinService.register(gr);

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
			bin.setNumBins(dto.getNumBins());
			bin.setMerce(merceRepository.findbyName(sNomeMerce));
			bin.setNostraProprieta(dto.getNostraProprieta());
			bin.setPesoLordo(dto.getPesoLordo());
			bin.setPesoNetto(dto.getPesoNetto());
			bin.setPrezzo(dto.getPrezzo());
			bin.setScarto(dto.getScarto());

			// binsGruppoVenditeRepository.save(bin);

			out.add(bin);
		}

		return out;
	}

	public void calcolaMedia(GruppoVendite gr) {

		int totaleBins = gr.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
		BigDecimal pesoLordo = gr.getPeso_lordo();

		BigDecimal media = BigDecimal.ZERO;

		if (totaleBins > 0 && pesoLordo != null) {
			media = pesoLordo.divide(BigDecimal.valueOf(totaleBins), 2, RoundingMode.HALF_UP);

		}
		gr.setMedia(media);
	}

	@Transactional
	public void cleanDDT(String ddtnum) {
		List<GruppoVendite> ll = gruppoVenditeRepository.findGruppoVenditeByDDT(ddtnum);
		for (GruppoVendite gv : ll) {
			gv.setDdt(null);
			gruppoVenditeRepository.save(gv);
		}
	}

}
