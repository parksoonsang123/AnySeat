package com.example.anyseat;

public class ReplyItem {
    private String contents;
    private String writetime;
    private String postid;
    private String replyid;
    private String userid;

    private int viewType;

    public ReplyItem() {}

    public ReplyItem(String contents, String writetime, String postid, String replyid, String userid, int viewType) {
        this.contents = contents;
        this.writetime = writetime;
        this.postid = postid;
        this.replyid = replyid;
        this.userid = userid;
        this.viewType = viewType;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
