package com.junko.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Slf4j
public class FullContactIntegration {
    // this should be in configurations
    private final String API_KEY = "rE3BNeVDIYr5WhJ29hF9umzrciWro5Ae";
    private final String URL = "https://api.fullcontact.com/v3/person.enrich";

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public FullContactIntegration() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        this.headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        this.headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY);
    }

    private PersonalInfo getInfo(String email) {
        PersonalInfo result = null;
        RequestObject request = new RequestObject(email);
        ResponseEntity<FullContactResponse> response = null;

        try {
            response = this.restTemplate.exchange(URL, HttpMethod.POST,
                    new HttpEntity<RequestObject>(request, headers), FullContactResponse.class);

            result = response.getBody().convert();
            System.out.println(response.getHeaders());

        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());

            if(e.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS)){
                log.info("You exceeded the number of requests allowed, wait 60 seconds and try again");
            }
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                log.info("Email does not exists!");
            }
        }

        return result;
    }

    public static void main(String[] args) {
        FullContactIntegration fci = new FullContactIntegration();
        fci.getInfo("bill.gates@microsoft.com");
        fci.getInfo("itaisoudry@gmail.com");
    }


    @Data
    @AllArgsConstructor
    private class RequestObject {
        private String email;
    }


}
