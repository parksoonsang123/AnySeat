package com.sme.anyseat;

public class AlramItem {

    private String type;
    private String writetime;
    private String postid;
    private String alramid;
    private String replyid;

    public AlramItem() {}

    public AlramItem(String type, String writetime, String postid, String alramid, String replyid) {
        this.type = type;
        this.writetime = writetime;
        this.postid = postid;
        this.alramid = alramid;
        this.replyid = replyid;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getAlramid() {
        return alramid;
    }

    public void setAlramid(String alramid) {
        this.alramid = alramid;
    }
}
