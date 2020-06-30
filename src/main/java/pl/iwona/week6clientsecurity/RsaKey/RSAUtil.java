package pl.iwona.week6clientsecurity.RsaKey;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class RSAUtil {

    String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnzyis1ZjfNB0bBgKFMSv\n" +
            "vkTtwlvBsaJq7S5wA+kzeVOVpVWwkWdVha4s38XM/pa/yr47av7+z3VTmvDRyAHc\n" +
            "aT92whREFpLv9cj5lTeJSibyr/Mrm/YtjCZVWgaOYIhwrXwKLqPr/11inWsAkfIy\n" +
            "tvHWTxZYEcXLgAXFuUuaS3uF9gEiNQwzGTU1v0FqkqTBr4B8nW3HCN47XUu0t8Y0\n" +
            "e+lf4s4OxQawWD79J9/5d3Ry0vbV3Am1FtGJiJvOwRsIfVChDpYStTcHTCMqtvWb\n" +
            "V6L11BWkpzGXSW4Hv43qa+GSYOD2QU68Mb59oSk2OB+BtOLpJofmbGEGgvmwyCI9\n" +
            "MwIDAQAB";

    //
//    static String privateKeyString = "MIIEogIBAAKCAQEAnzyis1ZjfNB0bBgKFMSvvkTtwlvBsaJq7S5wA+kzeVOVpVWwkWdVha4s38XM/" +
//            "pa/yr47av7+z3VTmvDRyAHcaT92whREFpLv9cj5lTeJSibyr/Mrm/YtjCZVWgaOYIhwrXwKLqPr/11inWsAkfIytvHWTxZYEcXLgAX" +
//            "FuUuaS3uF9gEiNQwzGTU1v0FqkqTBr4B8nW3HCN47XUu0t8Y0e+lf4s4OxQawWD79J9/5d3Ry0vbV3Am1FtGJiJvOwRsIfVChDpYSt" +
//            "TcHTCMqtvWbV6L11BWkpzGXSW4Hv43qa+GSYOD2QU68Mb59oSk2OB+BtOLpJofmbGEGgvmwyCI9MwIDAQABAoIBACiARq2wkltjtcj" +
//            "skFvZ7w1JAORHbEufEO1Eu27zOIlqbgyAcAl7q+/1bip4Z/x1IVES84/yTaM8p0goamMhvgry/mS8vNi1BN2SAZEnb/7xSxbflb70b" +
//            "X9RHLJqKnp5GZe2jexw+wyXlwaM+bclUCrh9e1ltH7IvUrRrQnFJfh+is1fRon9Co9Li0GwoN0x0byrrngU8Ak3Y6D9D8GjQA4Elm9" +
//            "4ST3izJv8iCOLSDBmzsPsXfcCUZfmTfZ5DbUDMbMxRnSo3nQeoKGC0Lj9FkWcfmLcpGlSXTO+Ww1L7EGq+PT3NtRae1FZPwjddQ1/4" +
//            "V905kyQFLamAA5YlSpE2wkCgYEAy1OPLQcZt4NQnQzPz2SBJqQN2P5u3vXl+zNVKP8w4eBv0vWuJJF+hkGNnSxXQrTkvDOIUddSKOz" +
//            "HHgSg4nY6K02ecyT0PPm/UZvtRpWrnBjcEVtHEJNpbU9pLD5iZ0J9sbzPU/LxPmuAP2Bs8JmTn6aFRspFrP7W0s1Nmk2jsm0CgYEAy" +
//            "H0X+jpoqxj4efZfkUrg5GbSEhf+dZglf0tTOA5bVg8IYwtmNk/pniLG/zI7c+GlTc9BBwfMr59EzBq/eFMI7+LgXaVUsM/sS4Ry+ye" +
//            "K6SJx/otIMWtDfqxsLD8CPMCRvecC2Pip4uSgrl0MOebl9XKp57GoaUWRWRHqwV4Y6h8CgYAZhI4mh4qZtnhKjY4TKDjxQYufXSdLA" +
//            "i9v3FxmvchDwOgn4L+PRVdMwDNms2bsL0m5uPn104EzM6w1vzz1zwKz5pTpPI0OjgWN13Tq8+PKvm/4Ga2MjgOgPWQkslulO/oMcXb" +
//            "PwWC3hcRdr9tcQtn9Imf9n2spL/6EDFId+Hp/7QKBgAqlWdiXsWckdE1Fn91/NGHsc8syKvjjk1onDcw0NvVi5vcba9oGdElJX3e9m" +
//            "xqUKMrw7msJJv1MX8LWyMQC5L6YNYHDfbPF1q5L4i8j8mRex97UVokJQRRA452V2vCO6S5ETgpnad36de3MUxHgCOX3qL382Qx9/TH" +
//            "Vmbma3YfRAoGAUxL/Eu5yvMK8SAt/dJK6FedngcM3JEFNplmtLYVLWhkIlNRGDwkg3I5Ky18Ae9n7dHVueyslrb6weq7dTkYDi3iOY" +
//            "RW8HRkIQh06wEdbxt0shTzAJvvCQfrBjg/3747WSsf/zBTcHihTRBdAv6OmdhV4/dD5YBfLAkLrd+mX7iE=";


    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public String getPublicKeyString() {
        return publicKeyString;
    }

    protected PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Base64 decodePublicKey = new Base64();
        byte[] publicKeyBytes = decodePublicKey.decode(publicKeyString);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    protected static RSAPrivateKey readPrivateKey(File keyFile) {
        try {
            PEMParser pemParser = new PEMParser(new FileReader(keyFile));
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
            return (RSAPrivateKey) kp.getPrivate();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RSAPrivateKey readFileKeyLoad(String filename) {
        return RSAUtil.readPrivateKey(readFile(filename));
    }

    private File readFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}

