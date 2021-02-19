package com.company.FriendsListMenu;

import FriendsListMenu.FriendsList;
import com.company.UserProfile.Profile;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FriendsListTest {

    private final Profile profile;
    private final List<Profile> friendsList;
    private final FriendsList friendsListMenu;

    public FriendsListTest(){
        friendsList = new ArrayList<>();
        profile = new Profile("Self", new Date(1610226883L), friendsList, null);
        initFriendsListForProfile();
        friendsListMenu = new FriendsList(profile);
    }

    private void initFriendsListForProfile(){
        profile.addFriend(new Profile("Alex", new Date(668985283L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Clara", new Date(1022706883L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Adam", new Date(628809283L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Robert", new Date(905807683L), new ArrayList<>(), null));
        profile.addFriend(new Profile("James", new Date(750633283L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Lucas", new Date(750633283L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Ethan", new Date(577487683L), new ArrayList<>(), null));
        profile.addFriend(new Profile("Ethan", new Date(978642883L), new ArrayList<>(), null));
    }

    @Test
    public void gettingUserRawFriendListTest(){
        int counter = 0;
        for(Profile friend : friendsListMenu.getUserRawFriendList()){
            assertEquals(friend, friendsList.get(counter++));
        }
    }

    @Test
    public void searchingByNameTest(){
        int counter = 0;
        for(Profile friend: friendsListMenu.searchByName("Alex")){
            assertEquals("Alex", friend.getName());
            counter++;
        }
        assertEquals(1, counter);

        counter = 0;
        for(Profile friend: friendsListMenu.searchByName("Ethan")){
            assertEquals("Ethan", friend.getName());
            counter++;
        }
        assertEquals(2, counter);
    }

    @Test
    public void searchingByDateOfBirthTest(){
        int counter = 0;
        for(Profile friend: friendsListMenu.searchByDateOfBirth(new Date(668985283L))){
            assertEquals(new Date(668985283L), friend.getDateOfBirth());
            counter++;
        }
        assertEquals(1, counter);

        counter = 0;
        for(Profile friend: friendsListMenu.searchByDateOfBirth(new Date(750633283L))){
            assertEquals(new Date(750633283L), friend.getDateOfBirth());
            counter++;
        }
        assertEquals(2, counter);
    }

    @Test
    public void removingGroupOfFriendsTest(){
        List<Profile> removingFriends = new ArrayList<>();
        removingFriends.add(new Profile("Alex", new Date(668985283L), new ArrayList<>(), null));
        removingFriends.add(new Profile("Clara", new Date(1022706883L), new ArrayList<>(), null));
        assertEquals("Alex", friendsListMenu.getUserRawFriendList().get(0).getName());
        assertEquals("Clara", friendsListMenu.getUserRawFriendList().get(1).getName());
        friendsListMenu.removeGroupOfFriends(removingFriends);
        assertFalse(friendsListMenu.getUserRawFriendList().contains("Alex"));
        assertFalse(friendsListMenu.getUserRawFriendList().contains("Clara"));
    }

    @After
    public void restoreFriendsList(){
        initFriendsListForProfile();
    }

    @Test
    public void gettingFriendsListForWebPageTest(){
        List<Profile> tmp = friendsListMenu.getFriendsListForWebPage(1, 4, true);
        assertEquals("Alex", tmp.get(0).getName());
        assertEquals("Clara", tmp.get(1).getName());
        assertEquals("Ethan", tmp.get(2).getName());
    }
}
