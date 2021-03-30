package com.example.anyseat;

import java.util.ArrayList;

public class PostItem {
    private String commentcnt;
    private String goodcnt;
    private String title;
    private String contents;
    private String writetime;
    private String postid;
    private String userid;
    private String imageexist;
    private ArrayList<String> imageurilist = new ArrayList<>();
    private ArrayList<String> imagenamelist = new ArrayList<>();

    private int viewType;

    public PostItem() {}

    public PostItem(String commentcnt, String goodcnt, String title, String contents, String writetime, String postid, String userid, String imageexist, int viewType) {
        this.commentcnt = commentcnt;
        this.goodcnt = goodcnt;
        this.title = title;
        this.contents = contents;
        this.writetime = writetime;
        this.postid = postid;
        this.userid = userid;
        this.imageexist = imageexist;
        this.viewType = viewType;
    }

    public PostItem(String commentcnt, String goodcnt, String title, String contents, String writetime, String postid, String userid, String imageexist, ArrayList<String> imageurilist, ArrayList<String> imagenamelist, int viewType) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImageexist() {
        return imageexist;
    }

    public void setImageexist(String image_exist) {
        this.imageexist = image_exist;
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
