package com.company.VideoPlayers;

import com.company.content.MediaContent;
import com.company.playback.Playable;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerClass implements Playable{

    private final List<String> availableMediaTypes;
    public final List<String> availableMediaFormats;

    public VideoPlayerClass(){
        availableMediaTypes = new ArrayList<>();
        availableMediaFormats = new ArrayList<>();
        VideoPlayerClassConfigLoader.initAvailableMediaTypes(availableMediaTypes);
        VideoPlayerClassConfigLoader.initAvailableMediaFormats(availableMediaFormats);
    }

    @Override
    public boolean isSupported(String mediaType, String mediaFormat) {
        return availableMediaTypes.contains(mediaType) && availableMediaFormats.contains(mediaFormat);
    }

    @Override
    public List<String> getAvailableMediaTypes() {
        return new ArrayList<>(availableMediaTypes);
    }

    @Override
    public List<String> getAvailableMediaFormats() {
        return new ArrayList<>(availableMediaFormats);
    }

    @Override
    public void play(MediaContent content) {
        if (isSupported(content.getContentType(),content.getContentFormat())){
            content.PlayingState(true);
        } else {
            System.out.println("Unsupported video content");
        }
    }

    @Override
    public void stop(MediaContent content) {
        if (isSupported(content.getContentType(),content.getContentFormat())){
            content.PlayingState(false);
        } else {
            System.out.println("Unsupported video content");
        }
    }

    @Override
    public void forward(MediaContent content, int deltaSec) {
        if (isSupported(content.getContentType(),content.getContentFormat())){
            content.setExtraDuration(deltaSec);
        } else {
            System.out.println("Unsupported video content");
        }
    }

    @Override
    public void reverse(MediaContent content, int deltaSec) {
        if (isSupported(content.getContentType(),content.getContentFormat())){
            content.setDecreaseDuration(deltaSec);
        } else {
            System.out.println("Unsupported video content");
        }
    }
}
