package com.example.anyseat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Organization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        Button Professor = (Button)findViewById(R.id.Professor);
        Professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("지도교수 최두현");
                info.setMessage("Phone : 010-XXXX-XXXX\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Assistant = (Button)findViewById(R.id.Assistant);
        Assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("조교 오은희");
                info.setMessage("Phone : 010-XXXX-XXXX\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button President_Student = (Button)findViewById(R.id.President_Student);
        President_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("학생회장 이승규");
                info.setMessage("Phone : 010-2860-2175\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button VidePresident_Student = (Button)findViewById(R.id.VicePresident_Student);
        VidePresident_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("학생부회장 하혜성");
                info.setMessage("Phone : 010-2083-5880\nE-Mail : aiiready@naver.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Manager_Student = (Button)findViewById(R.id.Manager_Student);
        Manager_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("총무 안승재");
                info.setMessage("Phone : 010-8704-9786\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Planning_Student = (Button)findViewById(R.id.Planning_Student);
        Planning_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("기획부장 전지윤");
                info.setMessage("Phone : 010-3138-1578\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Officer_Student = (Button)findViewById(R.id.Officer_Student);
        Officer_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("관리부장 권민석");
                info.setMessage("Phone : 010-7232-2249\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Ad_Student = (Button)findViewById(R.id.Ad_Student);
        Ad_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("홍보부장 최인표");
                info.setMessage("Phone : 010-2093-6355\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button Academic_Student = (Button)findViewById(R.id.Academic_Student);
        Academic_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this);
                info.setTitle("학술부장 장호성");
                info.setMessage("Phone : 010-5517-1961\nE-Mail : asdf1234@mobile.com");
                info.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

    }
}