package EncDec;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class secretKey {

    public SecretKeySpec generateSecretKey(char[] password, byte[] salt,
                                          int iterationCount, int keyLength)
            throws GeneralSecurityException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey tempKey = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(tempKey.getEncoded(), "AES");
    }

    private String base64Encoder(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] base64Decoder(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }

    public String encrypt(String dataToEncrypt, SecretKeySpec key)
            throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);

        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);

        byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();

        return base64Encoder(iv) + ":" + base64Encoder(cryptoText);
    }

    public String decrypt(String encryptedData, SecretKeySpec key)
            throws GeneralSecurityException, UnsupportedEncodingException {
        String[] parts = encryptedData.split(":");
        byte[] iv = base64Decoder(parts[0]);
        byte[] cryptoText = base64Decoder(parts[1]);

        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        return new String(pbeCipher.doFinal(cryptoText), "UTF-8");
    }
}
