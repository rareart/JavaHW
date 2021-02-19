package com.company.VideoPlayers;

import com.company.content.MediaContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VideoPlayerClassTest {

    private final VideoPlayerClass videoPlayer;

    @Mock
    private MediaContent mediaContent;

    public VideoPlayerClassTest(){
        videoPlayer = new VideoPlayerClass();
    }

    @Test
    public void getAvailableMediaTypesAndFormatsTest(){
        List<String> availableMediaTypes = new ArrayList<>();
        VideoPlayerClassConfigLoader.initAvailableMediaTypes(availableMediaTypes);

        List<String> availableMediaFormats = new ArrayList<>();
        VideoPlayerClassConfigLoader.initAvailableMediaFormats(availableMediaFormats);

        assertArrayEquals(availableMediaTypes.toArray(new String[0]), videoPlayer.getAvailableMediaTypes().toArray(new String[0]));
        assertArrayEquals(availableMediaFormats.toArray(new String[0]), videoPlayer.getAvailableMediaFormats().toArray(new String[0]));
    }

    @Test
    public void playerWithUnsupportedContentTest(){
        when(mediaContent.getContentType()).thenReturn("WrongType");
        when(mediaContent.getContentFormat()).thenReturn("WrongFormat");
        assertFalse(videoPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentType()).thenReturn("Video");
        assertFalse(videoPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentFormat()).thenReturn("mp4");
        assertTrue(videoPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentType()).thenReturn("WrongType");
        assertFalse(videoPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        videoPlayer.play(mediaContent);
        verify(mediaContent, times(0)).PlayingState(true);

        videoPlayer.stop(mediaContent);
        verify(mediaContent, times(0)).PlayingState(false);

        videoPlayer.forward(mediaContent, 15);
        verify(mediaContent, times(0)).setDecreaseDuration(15);

        videoPlayer.reverse(mediaContent, 15);
        verify(mediaContent, times(0)).setDecreaseDuration(15);
    }

    @Test
    public void playerWithSupportedContentTest(){
        when(mediaContent.getContentType()).thenReturn("Video");
        when(mediaContent.getContentFormat()).thenReturn("flv");

        videoPlayer.play(mediaContent);
        verify(mediaContent).PlayingState(true);

        videoPlayer.stop(mediaContent);
        verify(mediaContent).PlayingState(false);

        videoPlayer.forward(mediaContent, 15);
        verify(mediaContent).setExtraDuration(15);

        videoPlayer.reverse(mediaContent, 15);
        verify(mediaContent).setDecreaseDuration(15);
    }
}
