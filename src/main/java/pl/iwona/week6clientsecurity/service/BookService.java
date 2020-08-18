package pl.iwona.week6clientsecurity.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.iwona.week6clientsecurity.RsaKey.RSAKeyPairGenerator;


@Service
public class BookService {


    public void postObject() {
        Map<String, Object> keys = new HashMap<>();
        keys = RSAKeyPairGenerator.getKeys();

        String jwt = getString(keys);

        MultiValueMap<String, String> headers = getStringStringMultiValueMap(keys, jwt);

        String bookToAdd = "New Book";
        HttpEntity httpEntity = new HttpEntity(bookToAdd, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url2 = "http://localhost:8081/add";
        ResponseEntity<Void> resp2 = restTemplate.exchange(url2, HttpMethod.POST, httpEntity, Void.class);
        System.out.println(resp2);
    }

    public void getObject() {
        Map<String, Object> keys = new HashMap<>();
        keys = RSAKeyPairGenerator.getKeys();

        String jwt = getString(keys);

        MultiValueMap<String, String> headers = getStringStringMultiValueMap(keys, jwt);

        HttpEntity httpEntity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("http://localhost:8081/book",
                HttpMethod.GET,
                httpEntity,
                String[].class);
        Stream.of(exchange.getBody()).forEach(System.out::println);
    }

    private String getString(Map<String, Object> keys) {
        return Token.generateJwt( (RSAPublicKey) keys.get("public"), (RSAPrivateKey) keys.get("private"));
    }

    private MultiValueMap<String, String> getStringStringMultiValueMap(Map<String, Object> keys, String jwt) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + jwt);

        Base64.Encoder encoder = Base64.getEncoder();
        RSAPublicKey publicKey = (RSAPublicKey)keys.get("public");
        headers.add("Certification", encoder.encodeToString(publicKey.getEncoded()));
        return headers;
    }
}
