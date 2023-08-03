package com.lijukay.customchat.utils.items;

public class Message {

    private final String message;
    private final String time;
    private final String date;
    private final String writer;

    public Message(String message, String writer, String date, String time) {
        this.message = message;
        this.writer = writer;
        this.date = date;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getWriter() {
        return writer;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
