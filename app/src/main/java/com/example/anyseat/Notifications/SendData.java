package com.example.anyseat.Notifications;

public class SendData {
    private String senderName;
    private int icon;
    private String body;
    private String title;
    private String receiverName;
    private String type;
    private String key;

    public SendData(String senderName, String body, String type, String key) {
        this.senderName = senderName;
        this.body = body;
        this.type = type;
        this.key=key;
    }

    public SendData(String sender, int icon, String body, String title, String receiver, String type) {
        this.senderName = sender;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.receiverName = receiver;
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
