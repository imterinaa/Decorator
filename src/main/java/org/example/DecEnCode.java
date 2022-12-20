package org.example;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class DecEnCode {
    public static String encryptRSAToBase64(Data data, PublicKey publicKey) throws java.security.GeneralSecurityException {
        Cipher encrypt = Cipher.getInstance("RSA");
        encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptData = encrypt.doFinal(data.toBytes());
        return Base64.getEncoder().encodeToString(encryptData);
    }

    public static <T extends Data> T decryptRSA(String base64, PrivateKey privateKey, T converter) throws java.security.GeneralSecurityException {
        Cipher decrypt = Cipher.getInstance("RSA");
        decrypt.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] base = Base64.getDecoder().decode(base64);

        byte[] data = decrypt.doFinal(base);

        return (T)converter.fromBytes(data);
    }
}