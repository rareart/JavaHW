package com.company.Messenger;

import com.company.Message.Message;
import com.company.UserProfile.Profile;
import com.company.content.MediaContent;
import com.company.content.StaticContent;

import java.util.Date;
import java.util.List;

public interface Messenger {

    List<Message> getMessagesListForWebPage(int from, int to, boolean sortedByDate);

    List<Message> getMessagesByUserName(String name);

    List<Message> getMessagesUntilSelectedDate(Date date);

    boolean receiveMessage(Message msg);

    boolean sendNewMessage(Profile to, String text);

    boolean sendNewMessageWithStaticContent(Profile to, String text, StaticContent staticContent);

    boolean sendNewMessageWithMediaContent(Profile to, String text, MediaContent mediaContent);

    boolean sendNewMessageWithAllContent(Profile to, String text, StaticContent staticContent, MediaContent mediaContent);

    boolean removeMessage(Message message);

    boolean editTextInMessage(Message message, String text);

    boolean editStaticContentInMessage(Message message, StaticContent staticContent);

    boolean editMediaContentInMessage(Message message, MediaContent mediaContent);

}
