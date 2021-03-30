package com.example.anyseat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CrewFragment crewFragment;
    BoardFragment boardFragment;
    StudyFragment studyFragment;
    HomeFragment homeFragment;
    SeatFragment seatFragment;
    AlramFragment alramFragment;
    String Password;
    
    private TextView main_tv;
    Button refresh;
    Button search;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Password = getIntent().getStringExtra("Password");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        main_tv = findViewById(R.id.main_text);
        refresh = findViewById(R.id.refresh);
        search = findViewById(R.id.search);

        crewFragment = new CrewFragment();
        boardFragment = new BoardFragment();
        studyFragment = new StudyFragment();
        homeFragment = new HomeFragment();
        seatFragment = new SeatFragment(Password);
        alramFragment = new AlramFragment();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.main_frame_layout, homeFragment)
                .commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab0:
                        main_tv.setText("홈");
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, homeFragment)
                                .commitAllowingStateLoss();
                        refresh.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        return true;

                    case R.id.tab1:
                        main_tv.setText("과사 현황");
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, seatFragment)
                                .commitAllowingStateLoss();
                        refresh.setVisibility(View.VISIBLE);
                        search.setVisibility(View.INVISIBLE);
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
                        refresh.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);

                        return true;

                    case R.id.tab5:
                        main_tv.setText("알림");
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_frame_layout, alramFragment)
                                .commitAllowingStateLoss();
                        refresh.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);


                        return true;
                }
                return false;
            }
        });

    }
}