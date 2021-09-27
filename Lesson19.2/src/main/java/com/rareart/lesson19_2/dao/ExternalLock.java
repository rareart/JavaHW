package com.rareart.lesson19_2.dao;

public interface ExternalLock {

    void forceLock();

    void forceUnlock();
}
