package com.cimmino.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.Master;

@Service
public class AllineamentoService {
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	FileSenderService fileSenderService;
	@Autowired
	ArriviService arriviService;

	private String serverAddress;

	private String installationId;

	public void start(Master master) {
		serverAddress = master.getIndirizzo();

		installationId = configurazioneService.getConfigurazione().getInstallationId();
		
		String arriviJSON = arriviService.cercaPerInstallationAsJSON(installationId);
		
		System.out.println(arriviJSON);
		
	String v =	fileSenderService.sendFile(master, arriviJSON);
System.out.println(v);
	}

}
