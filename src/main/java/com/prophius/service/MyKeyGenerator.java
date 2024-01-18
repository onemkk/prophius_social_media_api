package com.prophius.service;


import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;


public class MyKeyGenerator {
    public static void main(String[] args) {
        byte[] key = generateRandomKey(32); // 256 bits for HMAC-SHA256
        String base64Key = encodeToBase64(key);
        System.out.println("Generated Key (Base64): " + base64Key);
    }

    private static byte[] generateRandomKey(int keySizeInBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keySizeInBytes];
        secureRandom.nextBytes(key);
        return key;
    }

    private static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
