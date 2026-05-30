package com.cimmino.shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.database.dto.ArriviDTO;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/files")
public class FileController {

	@PostMapping("/upload")
	public String upload(@RequestBody String body) {

		try {

			System.out.println("RICEVUTO");
			System.out.println(body);
			
			ObjectMapper mapper = new ObjectMapper();
		//	mapper.registerModule(new JavaTimeModule());

			List<ArriviDTO> list = mapper.readValue(
					body,
			        new TypeReference<List<ArriviDTO>>() {}
			);

			return "File ricevuto";

		} catch (Exception e) {

			e.printStackTrace();

			return "Errore";
		}
	}
}