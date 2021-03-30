package com.example.anyseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeActivity extends Activity implements NoticeAdapter.ClickListener {

    private Button homepagebtn;
    private RecyclerView recyclerView;
    private ArrayList<Notice_Item> items;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private NoticeAdapter.ClickListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice);

        //팝업창 크기 조절
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int)(dm.widthPixels * 0.9);
        int height = (int)(dm.heightPixels * 0.9);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        //리싸이클러뷰
        recyclerView = findViewById(R.id.notice_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        //가상의 데이터
        items = new ArrayList<>();
        database.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String key1 = snapshot1.getKey();
                    //선택적으로 읽는 부분 ("Notice")
                    if(!(key1.equals("Post") || key1.equals("Reply") || key1.equals("Good") || key1.equals("SeatInfo") || key1.equals("UserInfo") || key1.equals("UserList"))){
                        items.add(new Notice_Item(snapshot1.getValue().toString()));
                    }
                }

                NoticeAdapter adapter = new NoticeAdapter(items);
                recyclerView.setAdapter(adapter);

                adapter.setOnClickListener(mListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        homepagebtn = findViewById(R.id.homepage_btn);
        homepagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this,""+position+"번째 아이템",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(int position) {
        Toast.makeText(this,""+position+"번째 버튼",Toast.LENGTH_SHORT).show();
    }

    //바깥레이어 클릭시 안닫히게 설정
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}