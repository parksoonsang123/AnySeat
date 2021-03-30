package com.example.anyseat;

import java.util.ArrayList;

public class FreeBoardDetailItem {

    private String commentcnt;
    private String goodcnt;
    private String title;
    private String contents;
    private String writetime;
    private String postid;
    private String replyid;
    private String userid;
    private String press;

    private String imageexist;
    private ArrayList<String> imageurilist = new ArrayList<>();
    private ArrayList<String> imagenamelist = new ArrayList<>();

    private int viewType;

    private String CurrentUserId;

    public FreeBoardDetailItem() {}

    public FreeBoardDetailItem(ArrayList<String> imageurilist) {
        this.imageurilist = imageurilist;
    }

    public FreeBoardDetailItem(String commentcnt, String goodcnt, String title, String contents, String writetime, String postid, String userid, String imageexist, ArrayList<String> imageurilist, ArrayList<String> imagenamelist, int viewType) {
        //첫번째 타입
        this.commentcnt = commentcnt;
        this.goodcnt = goodcnt;
        this.title = title;
        this.contents = contents;
        this.writetime = writetime;
        this.postid = postid;
        this.userid = userid;
        this.imageexist = imageexist;
        this.imageurilist = imageurilist;
        this.imagenamelist = imagenamelist;
        this.viewType = viewType;
    }

    public FreeBoardDetailItem(String contents, String writetime, String postid, String replyid, String userid, int viewType) {
        //두번째 타입
        this.contents = contents;
        this.writetime = writetime;
        this.postid = postid;
        this.replyid = replyid;
        this.userid = userid;
        this.viewType = viewType;
    }

    public String getCommentcnt() {
        return commentcnt;
    }

    public void setCommentcnt(String commentcnt) {
        this.commentcnt = commentcnt;
    }

    public String getGoodcnt() {
        return goodcnt;
    }

    public void setGoodcnt(String goodcnt) {
        this.goodcnt = goodcnt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        CurrentUserId = currentUserId;
    }

    public String getImageexist() {
        return imageexist;
    }

    public void setImageexist(String imageexist) {
        this.imageexist = imageexist;
    }

    public ArrayList<String> getImageurilist() {
        return imageurilist;
    }

    public void setImageurilist(ArrayList<String> imageurilist) {
        this.imageurilist = imageurilist;
    }

    public ArrayList<String> getImagenamelist() {
        return imagenamelist;
    }

    public void setImagenamelist(ArrayList<String> imagenamelist) {
        this.imagenamelist = imagenamelist;
    }
}
