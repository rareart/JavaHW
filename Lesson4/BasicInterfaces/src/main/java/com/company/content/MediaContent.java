package com.company.content;

public interface MediaContent {

    String getContentType();

    String getContentFormat();

    void setExtraDuration(int sec);

    void setDecreaseDuration(int sec);

    int getDuration();

    void PlayingState(boolean state); //false for stop, true for play
}
