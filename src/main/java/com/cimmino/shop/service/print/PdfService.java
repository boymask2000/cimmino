package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.DDTDTO;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.DDTService;

@Service
public class PdfService {
	@Autowired
	DDTService ddtService;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	DDTListVenditePrinter ddtListVenditePrinter;

	public byte[] generatePdfGeneraleArrivi(List<Arrivi> arrivi) throws Exception {

		HasOutputStream builder = new GeneraleArriviPrinter(arrivi);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();
	}

	public byte[] generatePdfDettaglioVendita(Long idVednita) throws Exception {

		Optional<Vendita> v = venditeRepository.findById(idVednita);
		if (v.isEmpty())
			return null;
		Vendita ven = v.get();

		HasOutputStream builder = new DettaglioVenditaPrinter(ven);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();
	}

	public byte[] generateDDT(Long idVendita) throws Exception {
		Optional<Vendita> v = venditeRepository.findById(idVendita);

		if (v.isEmpty())
			return null;
		Vendita ven = v.get();
		Configurazione conf = configurazioneService.getConfigurazione();

		HasOutputStream builder = new DDTPrinter(ven, conf);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();

	}

	public byte[] generateDDT4Vendite(List<Long> ids) throws Exception {
		List<Vendita> vendite = new ArrayList<Vendita>();
		for (Long id : ids) {
			Optional<Vendita> v = venditeRepository.findById(id);
			vendite.add(v.get());
		}
		Configurazione conf = configurazioneService.getConfigurazione();

		 ddtListVenditePrinter.exec(vendite, conf);
		
		ByteArrayOutputStream outputStream = ddtListVenditePrinter.getOutputStream();
		return outputStream.toByteArray();
	}

	public byte[] onlyShow(Long id) throws Exception {
	DDTDTO ddt = ddtService.getDDT(id);
		HasOutputStream builder = new DDTPrinterRAW(ddt);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();

	}

}