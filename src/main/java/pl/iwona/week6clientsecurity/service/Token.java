package pl.iwona.week6clientsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.stereotype.Service;

@Service
public class Token {

    protected String generateJwt(boolean isAdmin, RSAPublicKey publicKey,
                                 RSAPrivateKey privateKey) {
//        RSAUtil r = new RSAUtil();
//        PrivateKey rsaPrivateKey = r.readFileKeyLoad("key/privatekey");

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        String token = JWT.create()
                .withClaim("admin", isAdmin)
                .withClaim("name", "Iwona")
                .sign(algorithm);

        return token;
    }
}
