package com.cimmino.shop.service.print;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.ConfigurazioneService;

@Service
public class PdfService {

	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	ConfigurazioneService configurazioneService;

	public byte[] generatePdfGeneraleArrivi(List<Arrivi> arrivi) throws Exception {

		HasOutputStream builder = new GeneraleArriviPrinter(arrivi);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();
	}

	public byte[] generatePdfDettaglioVendita(Long idVednita) throws Exception {
		
		Optional<Vendite> v = venditeRepository.findById(idVednita);
		if(v.isEmpty())return null;
		Vendite ven = v.get();
		
		
		HasOutputStream builder = new DettaglioVenditaPrinter(ven);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();
	}

	public byte[] generateDTT(Long idVendita) throws Exception {
		Optional<Vendite> v = venditeRepository.findById(idVendita);
	
		if(v.isEmpty())return null;
		Vendite ven = v.get();
		Configurazione conf = configurazioneService.getConfigurazione();
		
		HasOutputStream builder = new DTTPrinter(ven, conf);

		ByteArrayOutputStream outputStream = builder.getOutputStream();
		return outputStream.toByteArray();
		
	}

}