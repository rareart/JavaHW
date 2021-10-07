package com.company.serverside;

import com.company.serverside.Exceptions.AccountAccessDeniedException;
import com.company.serverside.Exceptions.NotEnoughMoneyException;
import com.company.serverside.Exceptions.PasswordInitException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class TerminalServer {
    private final DBMock dbMock;
    private final byte[] currentSalt;
    private boolean accessFlag;

    public TerminalServer() throws PasswordInitException {
        dbMock = new DBMock();
        accessFlag = false;

        SecureRandom random = new SecureRandom();
        currentSalt = new byte[16];
        random.nextBytes(currentSalt);

        dbMock.setPasswordHashInDB(ServerLoader.accountLoader(currentSalt));
        dbMock.setCreditsValueInDB(ServerLoader.creditsLoader());

    }

    public boolean passwordIsValid(char[] password) throws PasswordInitException {
        byte[] comparedPasswordHash;
        comparedPasswordHash = hashGenerator(password);
        if(Arrays.equals(comparedPasswordHash, dbMock.getUserPasswordHashFromDB())){
            accessFlag = true;
            return true;
        }
        return false;
    }

    public void closeCurrentSession(){
        accessFlag = false;
    }

    public BigDecimal getCreditsValue() throws AccountAccessDeniedException {
        if(accessFlag){
            return dbMock.getCreditsValueFromDB();
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    public void cashOut(BigDecimal value) throws AccountAccessDeniedException, NotEnoughMoneyException {
        if(accessFlag){
            if(value.setScale(2, BigDecimal.ROUND_CEILING).compareTo(dbMock.getCreditsValueFromDB()) > 0){
                throw new NotEnoughMoneyException("Not enough money on your account");
            } else {
                dbMock.setCreditsValueInDB(dbMock.getCreditsValueFromDB().subtract(value));
            }
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    public void cashIn(BigDecimal value) throws  AccountAccessDeniedException{
        if(accessFlag){
            dbMock.setCreditsValueInDB(dbMock.getCreditsValueFromDB().add(value.setScale(2, BigDecimal.ROUND_CEILING)));
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    ////err_code_03
    private byte[] hashGenerator(char[] passwordArr) throws PasswordInitException{
        KeySpec spec = new PBEKeySpec(passwordArr, currentSalt, 65536, 128);
        byte[] comparedPasswordHash;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            comparedPasswordHash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordInitException("Hash generation error, check server's hashGenerator()", e, 3);
        }
        return comparedPasswordHash;
    }
}
