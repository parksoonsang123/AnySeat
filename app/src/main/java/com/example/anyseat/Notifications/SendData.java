package com.example.anyseat.Notifications;

public class SendData {

    private String contents;
    private String postid;

    public SendData(String contents, String postid) {
        this.contents = contents;
        this.postid = postid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
