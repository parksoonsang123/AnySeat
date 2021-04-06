package com.example.anyseat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Organization extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);


        ImageView btn = findViewById(R.id.origin_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button Professor = (Button)findViewById(R.id.Professor);
        Professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("지도교수 최두현");
                info.setMessage("Phone : 010-XXXX-XXXX\nE-Mail : dhc@ee.knu.ac.kr");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("조교 오은희");
                info.setMessage("Phone : 010-XXXX-XXXX\nE-Mail : molla@mobile.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("학생회장 이승규");
                info.setMessage("Phone : 010-2860-2175\nE-Mail : skydril@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("학생부회장 하혜성");
                info.setMessage("Phone : 010-2083-5880\nE-Mail : aiiready@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("총무 안승재");
                info.setMessage("Phone : 010-8704-9786\nE-Mail : iifgii123@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("기획부장 전지윤");
                info.setMessage("Phone : 010-3138-1578\nE-Mail : wldbsdl1578@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("관리부장 권민석");
                info.setMessage("Phone : 010-7232-2249\nE-Mail : alstjr989898@gmail.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("홍보부장 최인표");
                info.setMessage("Phone : 010-2093-6355\nE-Mail : baha1909@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

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
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("학술부장 장호성");
                info.setMessage("Phone : 010-5517-1961\nE-Mail : hsv1961@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button one_Student = (Button)findViewById(R.id.one);
        one_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("1학년 대표 박태성");
                info.setMessage("Phone : 010-4942-6537\nE-Mail : bt0412@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button two_Student = (Button)findViewById(R.id.two);
        two_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("2학년 대표 박재우");
                info.setMessage("Phone : 010-9920-4620\nE-Mail : daramjing@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button three_Student = (Button)findViewById(R.id.three);
        three_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("3학년 대표 조영헌");
                info.setMessage("Phone : 010-2396-1362\nE-Mail : sonofgod@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button four_Student = (Button)findViewById(R.id.four);
        four_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("4학년 대표 이소윤");
                info.setMessage("Phone : 010-7577-4530\nE-Mail : soyoon417@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

        Button girl_Student = (Button)findViewById(R.id.girl);
        girl_Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(Organization.this, R.style.MyDialogTheme);
                info.setTitle("여학우 대표 성자민");
                info.setMessage("Phone : 010-4141-9279\nE-Mail : sjm9908@naver.com");
                info.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.setNegativeButton("",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                info.show();
            }
        });

    }
}