package com.example.anyseat;

public class Notice_Item {
    private String contents;
    private String writetime;

    public Notice_Item(String contents, String writetime) {
        this.contents = contents;
        this.writetime = writetime;
    }

    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
