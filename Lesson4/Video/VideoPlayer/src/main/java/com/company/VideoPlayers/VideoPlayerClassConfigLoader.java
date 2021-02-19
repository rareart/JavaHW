package com.company.VideoPlayers;

import java.util.List;

public class VideoPlayerClassConfigLoader {
    public static void initAvailableMediaTypes(List<String> availableMediaTypes){
        availableMediaTypes.add("Video");
        availableMediaTypes.add("Stream");
        availableMediaTypes.add("Loop");
        availableMediaTypes.add("Stories");
    }

    public static void initAvailableMediaFormats(List<String> availableMediaFormats){
        availableMediaFormats.add("mp4");
        availableMediaFormats.add("mp2");
        availableMediaFormats.add("flv");
        availableMediaFormats.add("webm");
        availableMediaFormats.add("mov");
    }
}
