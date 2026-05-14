package com.cimmino.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.OpCommerciante;
import com.cimmino.shop.database.OperazioniCommercianteRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;

import jakarta.transaction.Transactional;

@Service
public class VenditeService {

    @Autowired
    private VenditeRepository venditeRepository;

    @Autowired
    private CommercianteRepository commercianteRepository;
    
    @Autowired
    private OperazioniCommercianteRepository opCommercianteRepository;

    @Autowired
    private BinRepository binRepository;

    @Transactional
    public Vendite save(Vendite vendita, Long commercianteId, Long binId) {

        Commerciante commerciante = commercianteRepository.findById(commercianteId)
                .orElseThrow(() -> new RuntimeException("Commerciante non trovato"));

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new RuntimeException("Bin non trovato"));

        vendita.setCommerciante(commerciante);
        vendita.setBin(bin);
        
        saveOperazioneCommerciante(vendita);
        
        

        return venditeRepository.save(vendita);
    }

	private void saveOperazioneCommerciante(Vendite vendita) {
		OpCommerciante op = new OpCommerciante();
		
		op.setBin(vendita.getBin());
		op.setCommerciante(vendita.getCommerciante());
		op.setData(vendita.getData());
		op.setDtt(vendita.getDtt());
		op.setImporto(vendita.getImporto());
		op.setLordo(vendita.getLordo());
		op.setNetto(vendita.getNetto());
		op.setTara(vendita.getTara());
		
		opCommercianteRepository.save(op);
		
	}
}