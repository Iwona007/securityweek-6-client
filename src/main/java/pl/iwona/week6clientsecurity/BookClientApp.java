package pl.iwona.week6clientsecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Controller
public class BookClientApp {


    public BookClientApp() {
        postObject();
        getObject();
    }

    public void postObject() {
        String jwt = generateJwt(true);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "); // dokonczyc
        String body = "Nowa ksiązka";
        HttpEntity httpEntity = new HttpEntity(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/book", HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    public void getObject() {
        String jwt = generateJwt(true);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt); // dokonczyc

        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("http://localhost:8080/book", HttpMethod.GET,
                httpEntity,
                String[].class);
        Stream.of(Objects.requireNonNull(exchange.getBody())).forEach(System.out::println);
    }


    private String generateJwt(boolean isAdmin) {
        Algorithm algorithm = Algorithm.HMAC512("x!A%D*G-KaPdSgVkYp3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r");
        return JWT.create()
                .withClaim("admin", isAdmin)
                .sign(algorithm);
    }

}
