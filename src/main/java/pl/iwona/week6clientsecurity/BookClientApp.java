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


        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "); // dokonczyc
        String body = "Nowa ksiązka";
        HttpEntity httpEntity = new HttpEntity(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("", HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    public void getObject() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "); // dokonczyc

        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("", HttpMethod.GET,
                httpEntity,
                String[].class);
        Stream.of(Objects.requireNonNull(exchange.getBody())).forEach(System.out::println);

    }


    private String generateJwt(boolean isAdmin) {
        String jwt = generateJwt(false);
        Algorithm algorithm = Algorithm.RSA512();
        return JWT.create()
                .withClaim("admin", isAdmin)
                .sign(algorithm);

    }

}
