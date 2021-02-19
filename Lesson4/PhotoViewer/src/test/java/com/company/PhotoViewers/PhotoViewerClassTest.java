package com.company.PhotoViewers;

import com.company.content.StaticContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhotoViewerClassTest {

    private final PhotoViewerClass photoViewer;

    @Mock
    private StaticContent staticContent;

    public PhotoViewerClassTest(){
        photoViewer = new PhotoViewerClass();
    }

    @Test
    public void getAvailableMediaTypesAndFormatsTest(){
        List<String> availableMediaTypes = new ArrayList<>();
        PhotoViewerClassConfigLoader.initAvailableStaticContentTypes(availableMediaTypes);

        List<String> availableMediaFormats = new ArrayList<>();
        PhotoViewerClassConfigLoader.initAvailableStaticContentFormats(availableMediaFormats);

        assertArrayEquals(availableMediaTypes.toArray(new String[0]), photoViewer.getAvailableContentTypes().toArray(new String[0]));
        assertArrayEquals(availableMediaFormats.toArray(new String[0]), photoViewer.getAvailableContentFormats().toArray(new String[0]));
    }

    @Test
    public void viewerWithUnsupportedContentTest(){
        when(staticContent.getContentType()).thenReturn("WrongType");
        when(staticContent.getContentFormat()).thenReturn("WrongFormat");
        assertFalse(photoViewer.isSupported(staticContent.getContentType(), staticContent.getContentFormat()));

        when(staticContent.getContentType()).thenReturn("Photo");
        assertFalse(photoViewer.isSupported(staticContent.getContentType(), staticContent.getContentFormat()));

        when(staticContent.getContentFormat()).thenReturn("jpg");
        assertTrue(photoViewer.isSupported(staticContent.getContentType(), staticContent.getContentFormat()));

        when(staticContent.getContentType()).thenReturn("WrongType");
        assertFalse(photoViewer.isSupported(staticContent.getContentType(), staticContent.getContentFormat()));

        photoViewer.show(staticContent);
        verify(staticContent, times(0)).ShowingState(true);

        photoViewer.hide(staticContent);
        verify(staticContent, times(0)).ShowingState(false);
    }

    @Test
    public void viewerWithSupportedContentTest(){
        when(staticContent.getContentType()).thenReturn("Photo");
        when(staticContent.getContentFormat()).thenReturn("png");

        photoViewer.show(staticContent);
        verify(staticContent).ShowingState(true);

        photoViewer.hide(staticContent);
        verify(staticContent).ShowingState(false);
    }


}
