package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticatedUser authenticatedUser;

    public UserService(String url) {
        this.baseUrl = url;
    }

    private HttpEntity createAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public User[] getAllUsers(String token){
        User [] userArray = null;
        HttpEntity entity = createAuthEntity(token);
      try {
          ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, entity, User[].class );
            userArray = response.getBody();

            System.out.println("-----------------------------");
            System.out.println("Users");
            System.out.println("ID          Name" );
            for(int i = 0; i < userArray.length; i++){
                System.out.println("-----------------------------");
                System.out.println(userArray[i].getId() + "          " + userArray[i].getUsername());
            }
        } catch (RestClientException e) {
            System.out.println("Failed");
        }
        return userArray;
    }

}
