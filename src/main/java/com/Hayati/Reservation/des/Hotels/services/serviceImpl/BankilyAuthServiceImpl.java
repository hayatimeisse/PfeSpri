package com.Hayati.Reservation.des.Hotels.services.serviceImpl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.Hayati.Reservation.des.Hotels.dto.BankilyAuthResponse;
import com.Hayati.Reservation.des.Hotels.services.BankilyAuthService;

@Service
public class BankilyAuthServiceImpl implements BankilyAuthService {

    @Value("${auth.server.url}")
    private String authServerUrl;

    @Value("${auth.grant_type}")
    private String grantType;

    @Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    @Value("${auth.client_id}")
    private String clientId;

    @Override
    public BankilyAuthResponse authenticate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("username", username);
        map.add("password", password);
        return getAuthResponse(restTemplate, headers, map);
    }

    private BankilyAuthResponse getAuthResponse(RestTemplate restTemplate, HttpHeaders headers, MultiValueMap<String, String> map) {
        map.add("client_id", clientId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BankilyAuthResponse> response = restTemplate.postForEntity(authServerUrl + "/authentification", request, BankilyAuthResponse.class);
        return response.getBody();
    }


    @Override
    public BankilyAuthResponse refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token"); // Assurez-vous que cela correspond à la spécification de votre serveur d'authentification
        map.add("refresh_token", refreshToken);
        return getAuthResponse(restTemplate, headers, map);
    }
}
