package com.company.playback;

import com.company.content.StaticContent;

import java.util.List;

public interface Viewable {

    boolean isSupported(String contentType, String contentFormat);

    List<String> getAvailableContentTypes();

    List<String> getAvailableContentFormats();

    void show(StaticContent staticContent);

    void hide(StaticContent staticContent);

}
