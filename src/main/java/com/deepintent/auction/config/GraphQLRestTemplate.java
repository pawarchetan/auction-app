package com.deepintent.auction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GraphQLRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> callApi(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<?> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForEntity("http://localhost:8080/graphql", request, String.class);
    }
}
