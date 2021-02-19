package com.company.MusicPlayers;

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
public class MusicPlayerClassTest {

    private final MusicPlayerClass musicPlayer;

    @Mock
    private MediaContent mediaContent;

    public MusicPlayerClassTest(){
        musicPlayer = new MusicPlayerClass();
    }

    @Test
    public void getAvailableMediaTypesAndFormatsTest(){
        List<String> availableMediaTypes = new ArrayList<>();
        MusicPlayerClassConfigLoader.initAvailableMediaTypes(availableMediaTypes);

        List<String> availableMediaFormats = new ArrayList<>();
        MusicPlayerClassConfigLoader.initAvailableMediaFormats(availableMediaFormats);

        assertArrayEquals(availableMediaTypes.toArray(new String[0]), musicPlayer.getAvailableMediaTypes().toArray(new String[0]));
        assertArrayEquals(availableMediaFormats.toArray(new String[0]), musicPlayer.getAvailableMediaFormats().toArray(new String[0]));
    }

    @Test
    public void playerWithUnsupportedContentTest(){
        when(mediaContent.getContentType()).thenReturn("WrongType");
        when(mediaContent.getContentFormat()).thenReturn("WrongFormat");
        assertFalse(musicPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentType()).thenReturn("Music");
        assertFalse(musicPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentFormat()).thenReturn("mp3");
        assertTrue(musicPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        when(mediaContent.getContentType()).thenReturn("WrongType");
        assertFalse(musicPlayer.isSupported(mediaContent.getContentType(), mediaContent.getContentFormat()));

        musicPlayer.play(mediaContent);
        verify(mediaContent, times(0)).PlayingState(true);

        musicPlayer.stop(mediaContent);
        verify(mediaContent, times(0)).PlayingState(false);

        musicPlayer.forward(mediaContent, 10);
        verify(mediaContent, times(0)).setDecreaseDuration(10);

        musicPlayer.reverse(mediaContent, 10);
        verify(mediaContent, times(0)).setDecreaseDuration(10);
    }

    @Test
    public void playerWithSupportedContentTest(){
        when(mediaContent.getContentType()).thenReturn("Music");
        when(mediaContent.getContentFormat()).thenReturn("aac");

        musicPlayer.play(mediaContent);
        verify(mediaContent).PlayingState(true);

        musicPlayer.stop(mediaContent);
        verify(mediaContent).PlayingState(false);

        musicPlayer.forward(mediaContent, 10);
        verify(mediaContent).setExtraDuration(10);

        musicPlayer.reverse(mediaContent, 10);
        verify(mediaContent).setDecreaseDuration(10);
    }
}
