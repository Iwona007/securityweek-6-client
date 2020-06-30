package pl.iwona.week6clientsecurity.service;

import java.util.stream.Stream;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    public BookService() {
        postObject();
        getObject();
    }

    public void postObject() {
        Token token = new Token();
        String jwt = token.generateJwt(true);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        String bookToAdd = "New Book";
        HttpEntity httpEntity = new HttpEntity(bookToAdd, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/book",
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    public void getObject() {
        Token token = new Token();
        String jwt = token.generateJwt(true);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("http://localhost:8080/book",
                HttpMethod.GET,
                httpEntity,
                String[].class);
        Stream.of(exchange.getBody()).forEach(System.out::println);
    }
}
