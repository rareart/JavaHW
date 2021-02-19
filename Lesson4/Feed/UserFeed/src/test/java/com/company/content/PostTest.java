package com.company.content;

import com.company.UserProfile.Profile;

import content.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostTest {

    @Mock
    private StaticContent staticContent;

    @Mock
    private MediaContent videoMediaContent;

    private final Profile poster;
    private Post post;
    private Date dateNow;

    public PostTest(){
        poster = new Profile("Alex", new Date(668985283L), new ArrayList<>(), null);
        initPost();
    }

    private void initPost(){
        post = new Post(poster, "Title", "Some text for testing");
        dateNow = new java.util.Date();
    }

    @Test
    public void baseGetAndSetMethodsTest(){
        assertEquals(poster.getName(), post.getPostersName());
        assertNull(poster.getPhoto());
        assertEquals(dateNow.toString().substring(0, dateNow.toString().length() - 12), post.getDateOfCreation().toString().substring(0, post.getDateOfCreation().toString().length() - 12));
        assertEquals("Title", post.getTitle());
        post.setTitle("NewTitle");
        assertEquals("NewTitle", post.getTitle());
        assertEquals("Some text for testing", post.getText());
        post.setText("Some new text for testing");
        assertEquals("Some new text for testing", post.getText());

        initPost();
    }

    @Test
    public void postWithImagesTest(){
        post.showImages();
        verify(staticContent, times(0)).ShowingState(true);
        post.hideImages();
        verify(staticContent, times(0)).ShowingState(false);
        when(staticContent.getContentType()).thenReturn("Photo");
        when(staticContent.getContentFormat()).thenReturn("jpg");
        post.addImages(staticContent);
        verify(staticContent, times(1)).getContentType();
        verify(staticContent, times(1)).getContentFormat();
        post.showImages();
        verify(staticContent, times(1)).ShowingState(true);

        initPost();
    }

    @Test
    public void postWithAudioTest(){
        post.audioControl('p', 0);
        verify(videoMediaContent, times(0)).PlayingState(true);
        when(videoMediaContent.getContentType()).thenReturn("Music");
        when(videoMediaContent.getContentFormat()).thenReturn("mp3");
        post.addAudio(videoMediaContent);
        verify(videoMediaContent, times(1)).getContentType();
        verify(videoMediaContent, times(1)).getContentFormat();
        post.audioControl('q', 0);
        verifyNoMoreInteractions(videoMediaContent);
        post.audioControl('p', 1);
        verify(videoMediaContent, times(0)).PlayingState(true);
        post.audioControl('p', 0);
        verify(videoMediaContent, times(1)).PlayingState(true);
        post.audioControl('s', 0);
        verify(videoMediaContent, times(1)).PlayingState(false);
        post.audioControl('f', 0);
        verify(videoMediaContent, times(1)).setExtraDuration(10);
        post.audioControl('r', 0);
        verify(videoMediaContent, times(1)).setDecreaseDuration(10);

        initPost();
    }

    @Test
    public void postWithVideoTest(){
        post.videoControl('p', 0);
        verify(videoMediaContent, times(0)).PlayingState(true);
        when(videoMediaContent.getContentType()).thenReturn("Video");
        when(videoMediaContent.getContentFormat()).thenReturn("mp4");
        post.addVideo(videoMediaContent);
        verify(videoMediaContent, times(1)).getContentType();
        verify(videoMediaContent, times(1)).getContentFormat();
        post.videoControl('q', 0);
        verifyNoMoreInteractions(videoMediaContent);
        post.videoControl('p', 1);
        verify(videoMediaContent, times(0)).PlayingState(true);
        post.videoControl('p', 0);
        verify(videoMediaContent, times(1)).PlayingState(true);
        post.videoControl('s', 0);
        verify(videoMediaContent, times(1)).PlayingState(false);
        post.videoControl('f', 0);
        verify(videoMediaContent, times(1)).setExtraDuration(15);
        post.videoControl('r', 0);
        verify(videoMediaContent, times(1)).setDecreaseDuration(15);

        initPost();
    }

    @Test
    public void likesTest(){
        assertNull(post.getLikes());
        Profile liker = new Profile("Robert", new Date(905807683L), new ArrayList<>(), null);
        post.like(liker);
        assertTrue(post.getLikes().contains(liker));

        initPost();
    }

    @Test
    public void commentsTest(){
        assertNull(post.getComments());
        Profile commentator1 = new Profile("Robert", new Date(905807683L), new ArrayList<>(), null);
        Profile commentator2 = new Profile("James", new Date(750633283L), new ArrayList<>(), null);
        post.setComment(commentator1, "Awesome");
        post.setComment(commentator2, "Cool");
        assertTrue(post.getComments().containsKey(commentator1));
        assertTrue(post.getComments().containsKey(commentator2));
        ArrayList<String> tmpList = new ArrayList<>(post.getComments().values());
        assertEquals("Awesome", tmpList.get(0));
        assertEquals("Cool", tmpList.get(1));

        initPost();
    }

    @Test
    public void compareTest(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Post post2 = new Post(poster, "Fresh Post", "This post is newer");
        assertTrue(post2.compareTo(post) < 0);
    }
}
