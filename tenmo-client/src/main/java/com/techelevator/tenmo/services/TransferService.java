package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {
        private String authToken = null;

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        private final String baseUrl;
        private final RestTemplate restTemplate = new RestTemplate();

        public AuthenticatedUser authenticatedUser;



        public TransferService(String url, AuthenticatedUser authenticatedUser) {
            this.baseUrl = url;
            this.authenticatedUser = authenticatedUser;
        }

        private HttpEntity<Transfer> makeAccountEntity(Transfer transfer) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            return new HttpEntity<>(transfer, headers);
        }

        public HttpEntity<Void> makeAuthEntity() {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            return new HttpEntity<>(headers);
        }

        public Transfer [] getTransfersByAccountId(){
             Transfer [] transfersArray = null;
            try {
                transfersArray = restTemplate.exchange(baseUrl + "transfer", HttpMethod.GET, makeAuthEntity(), Transfer[].class ).getBody();
                System.out.println(Arrays.toString(transfersArray));
            } catch (RestClientException e) {
                System.out.println("Failed");
            }
            return transfersArray;
        }


        public Transfer createTransfer(int accountId, BigDecimal amount){
            Transfer transfer = new Transfer();
            try{
                Transfer transfer2 = new Transfer();
                transfer2.setAccountTo(accountId);
                transfer2.setAmount(amount);
                HttpEntity<Transfer> entity = makeAccountEntity(transfer2);
                transfer = restTemplate.postForObject(baseUrl + "transfer", entity, Transfer.class);
            }catch (RestClientException e){
                System.out.println("Failed");
            }
            return transfer;
        }

}
