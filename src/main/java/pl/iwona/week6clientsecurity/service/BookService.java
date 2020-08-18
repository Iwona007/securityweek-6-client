package pl.iwona.week6clientsecurity.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.iwona.week6clientsecurity.RsaKey.RSAUtil;

@Service
public class BookService {

    public void postObject() {
        Map<String, Object> keys = new HashMap<>();
        keys = RSAUtil.getKeys();
        Token token = new Token();
        String jwt = token.generateJwt(true, (RSAPublicKey) keys.get("public"), (RSAPrivateKey) keys.get("private"));

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        Base64.Encoder encoder = Base64.getEncoder();
        RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");
        headers.add("Certification", encoder.encodeToString(publicKey.getEncoded()));

        String bookToAdd = "New Book";
        HttpEntity httpEntity = new HttpEntity(bookToAdd, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> exchange = restTemplate.exchange("http://localhost:8081/book/add",
                HttpMethod.POST,
                httpEntity,
                Object.class);


    }

    public void getObject() {
        Map<String, Object> keys = new HashMap<>();
        keys = RSAUtil.getKeys();

        Token token = new Token();
        String jwt = token.generateJwt(true, (RSAPublicKey) keys.get("public"), (RSAPrivateKey) keys.get("private"));

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        Base64.Encoder encoder = Base64.getEncoder();
        RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");
        headers.add("Certification", encoder.encodeToString(publicKey.getEncoded()));

        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("http://localhost:8081/book",
                HttpMethod.GET,
                httpEntity,
                String[].class);
        Stream.of(exchange.getBody()).forEach(System.out::println);
    }
}
