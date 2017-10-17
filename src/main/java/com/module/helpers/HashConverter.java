package com.module.helpers;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class HashConverter {

    public static String encodeString(String data) {
        try {
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode("MfTkfF7JPeHiE+ykKmQPWg"), "AES");
            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = Base64.getDecoder().decode("x7cDbay/QHKCnzZVAuTo7g==".getBytes("UTF-8"));
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] byteDataToEncrypt = data.getBytes();
            byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);

            byte[] strCipherText = byteCipherText;

            return Base64.getEncoder().encodeToString(strCipherText);
        } catch (NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            System.out.println(e.getMessage());
            return data;
        }
    }
}