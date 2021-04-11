package com.sme.anyseat.Notifications;

public class SendData {

    private String contents;
    private String postid;
    private String type;
    private String alramid;
    private String replyid;

    public SendData(String contents, String postid, String type, String alramid, String replyid) {
        this.contents = contents;
        this.postid = postid;
        this.type = type;
        this.alramid = alramid;
        this.replyid = replyid;
    }


    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getAlramid() {
        return alramid;
    }

    public void setAlramid(String alramid) {
        this.alramid = alramid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
