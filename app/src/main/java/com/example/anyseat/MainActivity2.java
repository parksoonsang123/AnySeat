package com.example.anyseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CrewFragment crewFragment;
    BoardFragment boardFragment;
    StudyFragment studyFragment;
    HomeFragment homeFragment;
    SeatFragment seatFragment;
    AlramFragment alramFragment;
    MyPageFragment myPageFragment;
    String Password;
    String alram;
    private TextView main_tv;
    Button main_btn;

    DatabaseReference reference1;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        userId = mAuth.getCurrentUser().getUid();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        main_tv = findViewById(R.id.main_text);
        main_btn = findViewById(R.id.main_btn);

        Password = getIntent().getStringExtra("Password");
        if(Password != null){
            seatFragment = new SeatFragment(Password);
        }
        else{
            reference1 = FirebaseDatabase.getInstance().getReference("UserInfo");
            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        UserInfo item = snapshot1.getValue(UserInfo.class);
                        if(item.uid.equals(userId)){
                            Password = item.Password;
                            break;
                        }
                    }

                    seatFragment = new SeatFragment(Password);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        crewFragment = new CrewFragment();
        boardFragment = new BoardFragment();
        studyFragment = new StudyFragment();
        homeFragment = new HomeFragment();

        alramFragment = new AlramFragment();
        myPageFragment = new MyPageFragment();

        alram = getIntent().getStringExtra("alram");
        if(alram != null && alram.equals("1")){
            main_tv.setText("게시판");
            main_btn.setVisibility(View.VISIBLE);
            main_btn.setBackgroundResource(R.drawable.ic_baseline_search_24);
            main_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity2.this, SearchActivity.class);
                    startActivity(intent);
                }
            });
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_frame_layout, boardFragment)
                    .commitAllowingStateLoss();

            bottomNavigationView.setSelectedItemId(R.id.tab4);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.tab0:
                            main_tv.setText("홈");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, homeFragment)
                                    .commitAllowingStateLoss();

                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, AlramActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab1:
                            main_tv.setText("과사 현황");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, seatFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;

                    /*case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, crewFragment)
                                .commitAllowingStateLoss();
                        return true;

                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, studyFragment)
                                .commitAllowingStateLoss();
                        return true;*/

                        case R.id.tab4:
                            main_tv.setText("게시판");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, boardFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_search_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, SearchActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab5:
                            main_tv.setText("마이 페이지");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, myPageFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;
                }
            });
        }
        else if(alram != null && alram.equals("2")){
            main_tv.setText("홈");
            main_btn.setVisibility(View.VISIBLE);
            main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
            main_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity2.this, AlramActivity.class);
                    startActivity(intent);
                }
            });
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_frame_layout, homeFragment)
                    .commitAllowingStateLoss();

            bottomNavigationView.setSelectedItemId(R.id.tab0);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.tab0:
                            main_tv.setText("홈");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, homeFragment)
                                    .commitAllowingStateLoss();

                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, AlramActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab1:
                            main_tv.setText("과사 현황");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, seatFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;

                    /*case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, crewFragment)
                                .commitAllowingStateLoss();
                        return true;

                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, studyFragment)
                                .commitAllowingStateLoss();
                        return true;*/

                        case R.id.tab4:
                            main_tv.setText("게시판");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, boardFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_search_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, SearchActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab5:
                            main_tv.setText("마이 페이지");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, myPageFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;
                }
            });
        }
        else{
            main_tv.setText("홈");
            main_btn.setVisibility(View.VISIBLE);
            main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
            main_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity2.this, AlramActivity.class);
                    startActivity(intent);
                }
            });
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_frame_layout, homeFragment)
                    .commitAllowingStateLoss();

            bottomNavigationView.setSelectedItemId(R.id.tab0);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.tab0:
                            main_tv.setText("홈");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, homeFragment)
                                    .commitAllowingStateLoss();

                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, AlramActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab1:
                            main_tv.setText("과사 현황");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, seatFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;

                    /*case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, crewFragment)
                                .commitAllowingStateLoss();
                        return true;

                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, studyFragment)
                                .commitAllowingStateLoss();
                        return true;*/

                        case R.id.tab4:
                            main_tv.setText("게시판");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, boardFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.VISIBLE);
                            main_btn.setBackgroundResource(R.drawable.ic_baseline_search_24);
                            main_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity2.this, SearchActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return true;

                        case R.id.tab5:
                            main_tv.setText("마이 페이지");
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_frame_layout, myPageFragment)
                                    .commitAllowingStateLoss();
                            main_btn.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;
                }
            });
        }



    }

    /*public static int alarmflag = 0;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(alarmflag == 1) {
            alarmflag = 0;
            finish();
        }
    }*/

}