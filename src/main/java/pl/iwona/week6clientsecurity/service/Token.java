package pl.iwona.week6clientsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Token {

    protected static String generateJwt(RSAPublicKey publicKey,
                                 RSAPrivateKey privateKey) {
//        RSAKeyPairGenerator r = new RSAKeyPairGenerator();
//        PrivateKey rsaPrivateKey = r.readFileKeyLoad("key/privatekey");

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        String token = JWT.create()
                .withClaim("role", "ADMIN")
                .withClaim("name", "Iwona")
                .sign(algorithm);

        return token;
    }
}
