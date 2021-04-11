package com.sme.anyseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NoticeDetailActivity extends AppCompatActivity {


    ImageView fbdbackbtn;
    TextView fbdtitle;
    TextView fbdcontents;
    TextView fbdtime;
    Button del;

    DatabaseReference reference;
    DatabaseReference reference3;
    DatabaseReference reference2;
    DatabaseReference reference1;
    DatabaseReference reference12;
    FirebaseStorage storage;

    RecyclerView recyclerView;

    private ArrayList<PostItem2> list2 = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        userId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        final String postid = intent.getStringExtra("postid");

        final String alram = intent.getStringExtra("alram");
        final String alramid2 = intent.getStringExtra("alramid");
        if(alram != null && alram.equals("1")){  //알림 삭제
            reference12 = FirebaseDatabase.getInstance().getReference("Alram").child(userId).child(alramid2);
            reference12.removeValue();
        }

        del = findViewById(R.id.notice_detail_del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                builder.setMessage("삭제하시겠습니까?");

                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imagedelete2(postid);
                                finish();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });

        fbdtime = findViewById(R.id.freeboarddetail_time);
        fbdbackbtn = findViewById(R.id.notice_detail_back);
        fbdbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alram != null && alram.equals("1")){
                    Intent intent1 = new Intent(NoticeDetailActivity.this, MainActivity2.class);
                    intent1.putExtra("alram", "2");
                    startActivity(intent1);
                    finish();
                }
                else{
                    finish();
                }
            }
        });
        fbdtitle = findViewById(R.id.freeboarddetail_title);
        fbdcontents = findViewById(R.id.freeboarddetail_contents);

        recyclerView = findViewById(R.id.freeboarddetail_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        reference = FirebaseDatabase.getInstance().getReference("Notice").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();

                PostItem2 item = snapshot.getValue(PostItem2.class);
                list2.add(item);
                fbdcontents.setText(item.getContents());
                fbdtime.setText(item.getWritetime());
                fbdtitle.setText(item.getTitle());

                NoticeDetailRecyclerAdapter adapter = new NoticeDetailRecyclerAdapter(NoticeDetailActivity.this, list2);
                adapter.setOnImageClickListener(new NoticeDetailRecyclerAdapter.OnImageClickListener() {
                    @Override
                    public void onImageClick(View v, int position) {
                        Intent intent = new Intent(NoticeDetailActivity.this, OnlyImage.class);
                        intent.putExtra("position", position);
                        intent.putStringArrayListExtra("list", list2.get(0).getImageurilist());
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference1 = FirebaseDatabase.getInstance().getReference("UserInfo");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInfo item = snapshot1.getValue(UserInfo.class);

                    if(item.uid != null && item.uid.equals(userId)){
                        if(item.Master == 1){
                            del.setVisibility(View.VISIBLE);
                        }
                        else{
                            del.setVisibility(View.GONE);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void noticedelete(String postid){
        //String postid = list.get(position).getPostid();
        reference2 = FirebaseDatabase.getInstance().getReference("Notice").child(postid);
        reference2.removeValue();
    }

    private void imagedelete2(final String postid){
        reference3 = FirebaseDatabase.getInstance().getReference("Notice").child(postid);
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getImageexist().equals("1")){//이미지가 있을 때
                    for(int i=0;i<item.getImagenamelist().size();i++){
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com/").child("images/"+item.getImagenamelist().get(i));
                        storageRef.delete();
                    }


                    noticedelete(postid);
                }
                else{
                    noticedelete(postid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}