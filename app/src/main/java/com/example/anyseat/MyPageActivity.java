package com.example.anyseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    RecyclerView recyclerView1;

    private ArrayList<PostItem> list = new ArrayList<>();

    DatabaseReference reference;
    DatabaseReference reference2;

    HomeAdapter adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    TextView textView1;
    TextView textView2;
    TextView textView3;

    ImageView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        back = findViewById(R.id.mypage_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView1 = findViewById(R.id.mypage_name);
        textView2 = findViewById(R.id.mypage_grade);
        textView3 = findViewById(R.id.mypage_id);

        recyclerView1 = findViewById(R.id.mypage_rcv1);

        userId = mAuth.getCurrentUser().getUid();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(manager);

        reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostItem item1 = snapshot1.getValue(PostItem.class);
                    if(item1.getUserid().equals(userId)){
                        list.add(new PostItem(item1.getCommentcnt(), item1.getGoodcnt(), item1.getTitle(), item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getUserid(), item1.getImageexist(), item1.getImageurilist(), item1.getImagenamelist(), Code.ViewType.FIRST));
                    }
                }

                adapter = new HomeAdapter(list);
                recyclerView1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference2 = FirebaseDatabase.getInstance().getReference("UserInfo");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInfo item = snapshot1.getValue(UserInfo.class);
                    if(item.uid.equals(userId)){
                        textView1.setText(item.Name);
                        textView2.setText(item.Grade);
                        textView3.setText(item.Email);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        // 로그아웃
        Button LogOutButton = findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                finish();
            }
        });



    }
}