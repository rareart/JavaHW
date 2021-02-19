package com.company.Message;

import com.company.UserProfile.Profile;
import com.company.content.StaticContent;
import com.company.content.MediaContent;

import java.util.Date;

public class Message implements Comparable<Message> {
    private final Profile from;
    private final Profile to;
    private final Date date;
    private String text;
    private StaticContent attachedStaticContent;
    private MediaContent attachedMediaContent;

    public Message(Profile from, Profile to, String text){
        this.from = from;
        this.to = to;
        this.date = new java.util.Date();
        this.text = text;
    }

    public Message(Profile from, Profile to, String text, StaticContent content){
        this.from = from;
        this.to = to;
        this.date = new java.util.Date();
        this.text = text;
        this.attachedStaticContent = content;
    }

    public Message(Profile from, Profile to, String text, MediaContent content){
        this.from = from;
        this.to = to;
        this.date = new java.util.Date();
        this.text = text;
        this.attachedMediaContent = content;
    }

    public Message(Profile from, Profile to, String text, StaticContent staticContent, MediaContent mediaContent){
        this.from = from;
        this.to = to;
        this.date = new java.util.Date();
        this.text = text;
        this.attachedStaticContent = staticContent;
        this.attachedMediaContent = mediaContent;
    }

    public String getFrom() {
        return from.getName();
    }

    public String getTo() {
        return to.getName();
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StaticContent getAttachedStaticContent() {
        return attachedStaticContent;
    }

    public MediaContent getAttachedMediaContent() {
        return attachedMediaContent;
    }

    public void setAttachedStaticContent(StaticContent attachedStaticContent) {
        this.attachedStaticContent = attachedStaticContent;
    }

    public void setAttachedMediaContent(MediaContent attachedMediaContent) {
        this.attachedMediaContent = attachedMediaContent;
    }


    @Override
    public int compareTo(Message o) {
        return o.getDate().compareTo(this.date);
    }
}
