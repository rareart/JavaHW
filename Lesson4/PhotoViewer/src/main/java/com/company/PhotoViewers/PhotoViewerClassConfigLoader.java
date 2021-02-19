package com.company.PhotoViewers;

import java.util.List;

public class PhotoViewerClassConfigLoader {
    public static void initAvailableStaticContentTypes(List<String> availableStaticContentTypes){
        availableStaticContentTypes.add("Photo");
        availableStaticContentTypes.add("Animation");
        availableStaticContentTypes.add("OpenGraphPreview");
    }

    public static void initAvailableStaticContentFormats(List<String> availableStaticContentFormats){
        availableStaticContentFormats.add("jpg");
        availableStaticContentFormats.add("png");
        availableStaticContentFormats.add("bmp");
        availableStaticContentFormats.add("tiff");
        availableStaticContentFormats.add("gif");
    }
}
