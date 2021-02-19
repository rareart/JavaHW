package com.company.MusicPlayers;

import java.util.List;

public class MusicPlayerClassConfigLoader {
    public static void initAvailableMediaTypes(List<String> availableMediaTypes){
        availableMediaTypes.add("Music");
        availableMediaTypes.add("Podcast");
        availableMediaTypes.add("Radio");
    }

    public static void initAvailableMediaFormats(List<String> availableMediaFormats){
        availableMediaFormats.add("mp3");
        availableMediaFormats.add("wav");
        availableMediaFormats.add("ogg");
        availableMediaFormats.add("aac");
        availableMediaFormats.add("flac");
        availableMediaFormats.add("alac");
        availableMediaFormats.add("amr");
    }
}
