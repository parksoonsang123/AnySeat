package com.example.anyseat;

public class SeatInfo {
    public int num;
    public String seatnum;
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

    public void setSeatnum(String seatnum) {
        this.seatnum = seatnum;
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

        switch (num){
            case R.id.L11:
                seatnum = "L1-1";
                break;
            case R.id.L12:
                seatnum = "L1-2";
                break;
            case R.id.L21:
                seatnum = "L2-1";
                break;
            case R.id.L22:
                seatnum = "L2-2";
                break;
            case R.id.L31:
                seatnum = "L3-1";
                break;
            case R.id.L32:
                seatnum = "L3-2";
                break;
            case R.id.L41:
                seatnum = "L4-1";
                break;
            case R.id.L42:
                seatnum = "L4-2";
                break;
            case R.id.R1:
                seatnum = "R1";
                break;
            case R.id.R2:
                seatnum = "R2";
                break;
            case R.id.R3:
                seatnum = "R3";
                break;
            case R.id.R4:
                seatnum = "R4";
                break;
        }

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

    public String getSeatnum(){ return seatnum; }

    public boolean equals(String s){
        if(user.equals(s)) return true;
        else return  false;
    }

    @Override
    public String toString() {
        return "SeatInfo{" +
                "user='" + user + '\'' +
                "seatnum='" + seatnum + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
