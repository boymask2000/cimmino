package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.ConfigurazioneRepository;

@Service
public class ConfigurazioneService {
	@Autowired
	ConfigurazioneRepository configurazioneRepository;
	
	
	public Configurazione getConfigurazione() {
	
		Optional<Configurazione> optConf = configurazioneRepository.findById((long) 1);
		if( optConf.isEmpty())
			return initConfig();
		
		return optConf.get();
	}
	
	
	private Configurazione initConfig() {
		Configurazione conf = new Configurazione();
		conf.setPrezzoFrigo(new BigDecimal("0.01"));
		conf.setInstallationId(""+(new Date().getTime()));
		configurazioneRepository.save(conf);
		return conf;
	}


	public void save(Configurazione conf) {
		configurazioneRepository.save(conf);
		
	}
}
