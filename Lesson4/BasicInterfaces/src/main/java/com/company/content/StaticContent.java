package com.company.content;

public interface StaticContent {

    String getContentType();

    String getContentFormat();

    void ShowingState(boolean state); //false for show, true for hide
}
