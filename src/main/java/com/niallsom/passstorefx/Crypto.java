package com.niallsom.passstorefx;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Crypto {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public static String encryptPasswordSHA256(String myKey) {
        try {
            key = myKey.getBytes("UTF-8");
            //Checksum: error detection method
            // hash function: a function that produces checksum
            // hash value: numeric value of fixed length that uniquely identifies data
            // message digest: it is a fixed size numeric representation of the contents of the message computed by hash function
            // In java, MessageDigest class provides functionality of message digest using algorithms such as SHA-1 or SHA-256
            // SHA: Secure Hashing Algorithm
            byte[] key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedKey = sha.digest(key);
            return Base64.getEncoder().encodeToString(hashedKey);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException ignored) {}
        return null;
    }
    public static void setKey(String myKey) {
        try {
            key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key,"AES");
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException ignored) {}
    }
    //encryption
    public static String encryptAES(String strToEnc, String sec){
        try {
            setKey(sec); // sets the secret key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes("UTF-8")));
        }
        catch (Exception ignored){}
        return null;
    }
    //decryption
    public static String decryptAES(String strToDec, String sec){
        try {
            setKey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDec)));
        }
        catch (Exception ignored){}
        return null;
    }
}
