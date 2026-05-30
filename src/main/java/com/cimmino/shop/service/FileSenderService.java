package com.cimmino.shop.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cimmino.shop.Master;

@Service
public class FileSenderService {

    @Autowired
    private RestClient restClient;

    public String sendFile(Master master, String body) {
    	String serverAddress=master.getIndirizzo();


        return restClient.post()
                .uri("http://"+serverAddress+":8080/api/files/upload")
                .contentType(MediaType.TEXT_PLAIN)
                .body(body)
                .retrieve()
                .body(String.class);
    }
}