package com.company.profile;

import java.util.Date;
import java.util.List;
import com.company.content.StaticContent;

public interface Humanable<T> {

    StaticContent getPhoto();
    String getName();
    Date getDateOfBirth();
    List<T> getFriendsList();
    void addFriend(T profile);
    void removeFriend(T profile);
}
