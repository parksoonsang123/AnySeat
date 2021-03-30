package com.example.anyseat;

public class UserInfo {
    public String Name;
    public String Email;
    public String Password;
    public int Grade=1;
    public String using = "false";

    public UserInfo(){

    }

    public UserInfo(String name, String email, String password, int grade){
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Grade = grade;
        if(Grade == 0) Grade = 1;
    }

    @Override
    public String toString() {
        return "이름 : " + Name + '\n' +
                "Email : " + Email + '\n' +
                "학년 = " + Grade + '\n' +
                "좌석 사용 여부 : " + using;
    }
}
