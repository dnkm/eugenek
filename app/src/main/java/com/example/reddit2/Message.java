package com.example.reddit2;

import java.util.ArrayList;
import java.util.List;

public class Message {
    String id;
    List<String> replies = new ArrayList<>();
    int score;
    String text;

    public Message() {

    }

    public Message(String id, List<String> replies, String text) {
        this.id = id;
        this.replies = replies;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public List<String> getReplies() {
        return replies;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

}
