package com.company.serverside;

import com.company.serverside.Exceptions.PasswordInitException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class ServerLoader {
    ////err_code_02
    public static byte[] accountLoader(byte[] salt) throws PasswordInitException {
        char[] password = {'1', '3', '3', '7'}; //example for password

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        byte[] passwordHash;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            passwordHash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordInitException("Hash generation error, check server's loader", e, 2);
        }
        return passwordHash;
    }

    public static BigDecimal creditsLoader(){
        //example for bank account
        return new BigDecimal("11200.11").setScale(2, BigDecimal.ROUND_CEILING); //example for account credits
    }
}
