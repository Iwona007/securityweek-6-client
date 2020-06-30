package pl.iwona.week6clientsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import org.springframework.stereotype.Service;
import pl.iwona.week6clientsecurity.RsaKey.RSAUtil;

@Service
public class Token {

//    private RSAPrivateKey privateKey;
//    private RSAPublicKey publicKey;
//
//    @EventListener(ApplicationReadyEvent.class)
//    protected void key() {
//        RSAUtil r = new RSAUtil();
//        this.privateKey = r.readFileKeyLoad("key/privatekey");
//        try {
//            this.publicKey = (RSAPublicKey) r.getPublicKey();
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//    }

    protected String generateJwt(boolean isAdmin) {
        RSAUtil r = new RSAUtil();
        PrivateKey rsaPrivateKey = r.readFileKeyLoad("key/privatekey");
        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) rsaPrivateKey);
        return JWT.create()
                .withClaim("admin", isAdmin)
                .withClaim("name", "Iwona")
                .sign(algorithm);

    }
}
