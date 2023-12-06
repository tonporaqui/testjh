package com.jh.test.web.rest;

import com.jh.test.service.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/login")
public class LoginResource {

    @Value("${KEYCLOAK_CLIENT_ID}")
    private String keycloakClientId;

    @Value("${KEYCLOAK_CLIENT_SECRET}")
    private String keycloakClientSecret;

    @Value("${KEYCLOAK_REALM}")
    private String keycloakRealm;

    /**
     * A method that handles the login functionality.
     *
     * @param loginDto an object containing the username and password for the login
     * @return a ResponseEntity with the login response
     */
    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        RestTemplate restTemplate = new RestTemplate();

        // Crear la solicitud para Keycloak
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", keycloakClientId);
        map.add("client_secret", keycloakClientSecret);
        map.add("grant_type", "password");
        map.add("username", loginDto.getUsername());
        map.add("password", loginDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String url = UriComponentsBuilder.fromHttpUrl(keycloakRealm).path("/protocol/openid-connect/token").toUriString();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            // Procesar y devolver la respuesta
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            // Manejar error de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
