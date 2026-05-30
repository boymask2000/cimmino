package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.ConfigurazioneRepository;

@Service
public class ConfigurazioneService {
	@Autowired
	ConfigurazioneRepository configurazioneRepository;
	
	
	public Configurazione getConfigurazione() {
	
		 List<Configurazione> confs = configurazioneRepository.findAll();
		if( confs.isEmpty())
			return initConfig();
		if( confs.size()>1) {
			System.out.println("ERR getConfigurazione");
		}
		
		return confs.get(0);
	}
	
	
	private Configurazione initConfig() {
		Configurazione conf = new Configurazione();
		conf.setPrezzoFrigoxCaldo(new BigDecimal("0.01"));
		conf.setPrezzoFrigoxFreddo(new BigDecimal("0.01"));
		conf.setInstallationId(""+(new Date().getTime()));
		configurazioneRepository.save(conf);
		return conf;
	}


	public void save(Configurazione conf) {
		configurazioneRepository.save(conf);
		
	}
}
