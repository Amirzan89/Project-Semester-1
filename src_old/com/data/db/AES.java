package com.data.db;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;
public class AES {
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;

    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args) {
        try {
            AES aes = new AES();
            aes.init();
            String hash1 = aes.encrypt("terserah");
            String password1 = aes.decrypt(hash1);
            aes.init();
            String hash2 = aes.encrypt("terserah");
            String password2 = aes.decrypt(hash2);
            aes.init();
            String hash3 = aes.encrypt("terserah");
            String password3 = aes.decrypt(hash3);
            aes.init();
            String hash4 = aes.encrypt("terserah");
            String password4 = aes.decrypt(hash4);
            aes.init();
            String hash5 = aes.encrypt("terserah");
            String password5 = aes.decrypt(hash5);
            aes.init();
            System.out.println(" hasil ");
            System.out.println("Encrypted Message1 : " + hash1);
            System.out.println("Encrypted Message2 : " + hash2);
            System.out.println("Encrypted Message3 : " + hash3);
            System.out.println("Encrypted Message4 : " + hash4);
            System.out.println("Encrypted Message5 : " + hash5);
            System.out.println("Decrypted Message1 : " + password1);
            System.out.println("Decrypted Message2 : " + password2);
            System.out.println("Decrypted Message3 : " + password3);
            System.out.println("Decrypted Message4 : " + password4);
            System.out.println("Decrypted Message5 : " + password5);
        } catch (Exception ignored) {
            System.out.println(ignored);
            ignored.printStackTrace();
        }
    }
}






