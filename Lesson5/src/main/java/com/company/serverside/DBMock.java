package com.company.serverside;

import java.math.BigDecimal;
import java.util.Arrays;

public class DBMock {
    private byte[] passwordHash;

    private BigDecimal credits;

    public DBMock() {
        this.passwordHash = null;
        this.credits = null;
    }

    public void setCreditsValueInDB(BigDecimal credits){
        this.credits = credits;
    }

    public BigDecimal getCreditsValueFromDB(){
        return credits;
    }

    public void setPasswordHashInDB(byte[] passwordHash){
        this.passwordHash = passwordHash;
    }

    public byte[] getUserPasswordHashFromDB(){
        return Arrays.copyOf(passwordHash, passwordHash.length);
    }
}
