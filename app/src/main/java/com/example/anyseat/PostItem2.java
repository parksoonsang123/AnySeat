package com.example.anyseat;

import java.util.ArrayList;

public class PostItem2 {
    private String title;
    private String contents;
    private String writetime;
    private String postid;
    private String imageexist;
    private ArrayList<String> imageurilist = new ArrayList<>();
    private ArrayList<String> imagenamelist = new ArrayList<>();

    private int viewType;

    public PostItem2() {}



    public PostItem2(String title, String contents, String writetime, String postid, String imageexist, ArrayList<String> imageurilist, ArrayList<String> imagenamelist, int viewType) {
        this.title = title;
        this.contents = contents;
        this.writetime = writetime;
        this.postid = postid;
        this.imageexist = imageexist;
        this.imageurilist = imageurilist;
        this.imagenamelist = imagenamelist;
        this.viewType = viewType;
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
