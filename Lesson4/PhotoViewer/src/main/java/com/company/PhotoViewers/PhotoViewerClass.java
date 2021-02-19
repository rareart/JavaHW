package com.company.PhotoViewers;

import com.company.content.StaticContent;
import com.company.playback.Viewable;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewerClass implements Viewable {

    private final List<String> availableStaticContentTypes;
    private final List<String> availableStaticContentFormats;

    public PhotoViewerClass(){
        this.availableStaticContentTypes = new ArrayList<>();
        this.availableStaticContentFormats = new ArrayList<>();
        PhotoViewerClassConfigLoader.initAvailableStaticContentTypes(availableStaticContentTypes);
        PhotoViewerClassConfigLoader.initAvailableStaticContentFormats(availableStaticContentFormats);
    }

    @Override
    public boolean isSupported(String contentType, String contentFormat) {
        return availableStaticContentTypes.contains(contentType) &&
                availableStaticContentFormats.contains(contentFormat);
    }

    @Override
    public List<String> getAvailableContentTypes() {
        return new ArrayList<>(availableStaticContentTypes);
    }

    @Override
    public List<String> getAvailableContentFormats() {
        return new ArrayList<>(availableStaticContentFormats);
    }

    @Override
    public void show(StaticContent staticContent) {
        if(isSupported(staticContent.getContentType(), staticContent.getContentFormat())){
            staticContent.ShowingState(true);
        } else {
            System.out.println("Unsupported image");
        }
    }

    @Override
    public void hide(StaticContent staticContent) {
        if(isSupported(staticContent.getContentType(), staticContent.getContentFormat())){
            staticContent.ShowingState(false);
        } else {
            System.out.println("Unsupported image");
        }
    }
}
