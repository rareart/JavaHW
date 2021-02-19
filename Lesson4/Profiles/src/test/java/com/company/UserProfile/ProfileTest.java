package com.company.UserProfile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ProfileTest {

    private final Profile profile;
    List<Profile> friendsList;

    public ProfileTest(){
        friendsList = new ArrayList<>();
        profile = new Profile("Alex", new Date(1610226883L), friendsList, null);
    }

    @Test
    public void baseGetMethodsTest(){
        assertEquals("Alex", profile.getName());
        assertEquals(new Date(1610226883L), profile.getDateOfBirth());
        friendsList.add(new Profile("Clara", new Date(1022706883L), new ArrayList<>(), null));
        for(Profile friend : profile.getFriendsList()){
            assertTrue(friendsList.contains(friend));
        }
        assertEquals(friendsList, profile.getFriendsList());
        assertNull(profile.getPhoto());
    }

    @Test
    public void friendsListTest(){
        Profile jack = new Profile("Jack", new Date(1610226883L), friendsList, null);
        profile.addFriend(jack);
        assertTrue(profile.getFriendsList().contains(jack));
        profile.removeFriend(jack);
        assertFalse(profile.getFriendsList().contains(jack));
    }

    @Test
    public void compareByName(){
        Profile bart = new Profile("Bart", new Date(1610226883L), friendsList, null);
        assertTrue(profile.compareTo(bart)!=0);
        assertTrue(profile.compareTo(bart)<0);
    }

}
