package com.cimmino.shop.service;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class IpService {

    private final RestClient restClient;

    public IpService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getPublicIp() {

        return restClient.get()
                .uri("https://api.ipify.org?format=text")
                .retrieve()
                .body(String.class);
    }
}