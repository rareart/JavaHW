package com.company.playback;

import com.company.content.MediaContent;
import java.util.List;

public interface Playable {

    boolean isSupported(String mediaType, String mediaFormat);

    List<String> getAvailableMediaTypes();

    List<String> getAvailableMediaFormats();

    void play(MediaContent content);

    void stop(MediaContent content);

    void forward(MediaContent content, int deltaSec);

    void reverse(MediaContent content, int deltaSec);
}
