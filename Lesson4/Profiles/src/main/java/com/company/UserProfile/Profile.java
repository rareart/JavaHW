package com.company.UserProfile;

import com.company.content.StaticContent;
import com.company.profile.Humanable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profile implements Humanable<Profile>, Comparable<Profile>{

    private final List<Profile> friendsList;
    private final Date dateOfBirth;
    private final String name;
    private final StaticContent userPic;

    public Profile(String name, Date dateOfBirth, List<Profile> friendsList, StaticContent userPic){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.friendsList = friendsList;
        this.userPic = userPic;
    }

    @Override
    public StaticContent getPhoto() {
        return userPic;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getDateOfBirth() {
        return (Date) dateOfBirth.clone();
    }

    @Override
    public List<Profile> getFriendsList() {
        return new ArrayList<>(friendsList);
    }

    @Override
    public void addFriend(Profile profile) {
        friendsList.add(profile);
    }

    @Override
    public void removeFriend(Profile profile) {
        friendsList.remove(profile);
    }

    @Override
    public int compareTo(Profile o) {
        return this.name.compareTo(o.getName());
    }
}
