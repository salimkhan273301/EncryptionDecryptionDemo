package EncDec;



import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecript {

    public void writeEncryptedPasswordToFile(String password)
            throws IOException, GeneralSecurityException {

        System.out.println("Original password: " + password);

        secretKey object = new secretKey();

        byte[] salt = new String("622836429").getBytes();
        int iterationCount = 10000;
        int keyLength = 128;

        SecretKeySpec key = object.generateSecretKey(password.toCharArray(), salt, iterationCount, keyLength);

        String encryptedPassword = object.encrypt(password, key);

        Properties properties = new Properties();
        properties.setProperty("EncryptedPassword", encryptedPassword);

        FileOutputStream fileOutputStream = new FileOutputStream("src/encrypted.properties");
        properties.store(fileOutputStream, "Encrypted Password");
        fileOutputStream.close();
    }

    public void readEncryptedPasswordFromFile()
            throws IOException, GeneralSecurityException {

        FileInputStream fileInputStream = new FileInputStream("src/encrypted.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        String encryptedPassword = properties.getProperty("EncryptedPassword");
        fileInputStream.close();

        secretKey object = new secretKey();

        byte[] salt = new String("622836429").getBytes();
        int iterationCount = 10000;
        int keyLength = 128;

        String password = "YourPassword"; // Replace 'YourPassword' with the password you used for encryption
        SecretKeySpec key = object.generateSecretKey(password.toCharArray(), salt, iterationCount, keyLength);

        String decryptedPassword = object.decrypt(encryptedPassword, key);

        System.out.println("Decrypted password: " + decryptedPassword);
    }

    public static void main(String[] args) throws Exception {
        EncryptDecript manager = new EncryptDecript();

        String password = "YourPassword"; // Replace 'YourPassword' with the original password

        manager.writeEncryptedPasswordToFile(password);
        manager.readEncryptedPasswordFromFile();
    }
}
