package com.example.anyseat;

public class SeatInfo {
    public int num;
    public String user;
    public String status;
    public int statusnum; //0:사람없음 1:사람있음

    public SeatInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusnum() {
        return statusnum;
    }

    public void setStatusnum(int statusnum) {
        this.statusnum = statusnum;
    }

    public SeatInfo(int num, int status, String user) {
        this.num = num;

        this.statusnum = status;
        if(statusnum == 0) {
            this.status = "off";
            this.user = "빈 자리";
        }
        else {
            this.user = user;
            this.status = "on";
        }
    }


    public boolean equals(String s){
        if(user.equals(s)) return true;
        else return  false;
    }

    @Override
    public String toString() {
        return "SeatInfo{" +
                "user='" + user + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
