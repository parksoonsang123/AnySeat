package com.sme.anyseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FreeBoardActivity extends AppCompatActivity {

    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    DatabaseReference reference4;
    DatabaseReference reference5;
    DatabaseReference reference6;


    private RecyclerView freeboardRecyclerView;
    private ArrayList<PostItem> list = new ArrayList<>();
    private FreeBoardAdapter adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    FirebaseStorage storage;

    private ArrayList<String> userlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);

        FloatingActionButton fab = findViewById(R.id.freeboard_floatingactionbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FreeBoardActivity.this, WriteBoardActivity.class);
                startActivity(intent);
            }
        });

        userId = mAuth.getCurrentUser().getUid();
        freeboardRecyclerView = findViewById(R.id.free_board_recyclerview1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        freeboardRecyclerView.setLayoutManager(manager);

        reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostItem item1 = snapshot1.getValue(PostItem.class);
                    list.add(new PostItem(item1.getCommentcnt(), item1.getGoodcnt(), item1.getTitle(), item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getUserid(), item1.getImageexist(), item1.getImageurilist(), item1.getImagenamelist(), Code.ViewType.FIRST));
                }

                adapter = new FreeBoardAdapter(list, userId);
                /*adapter.setOnDeleteClickListener2(new FreeBoardAdapter.OnDeleteClickListner2() {
                    @Override
                    public void onDeleteClick2(View v, int position, Button btn) {
                        //?????? -> ????????? -> ????????? -> ????????? ??????
                        commentsdelete(position);
                    }
                });*/
                freeboardRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   /* private void postdelete(int position){
        String postid = list.get(position).getPostid();
        reference2 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference2.removeValue();
    }

    private void commentsdelete(int position){
        final String postid = list.get(position).getPostid();
        reference3 = FirebaseDatabase.getInstance().getReference("Reply");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ReplyItem item1 = snapshot1.getValue(ReplyItem.class);
                    if(item1.getPostid().equals(postid)){
                        reference4 = FirebaseDatabase.getInstance().getReference("Reply").child(item1.getReplyid());
                        reference4.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imagedelete(position);
    }

    private void imagedelete(int position){
        if(list.get(position).getImageexist().equals("1")){ //???????????? ?????? ???
            for(int i=0;i<list.get(position).getImagenamelist().size();i++){
                storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://anyseat-4e964.appspot.com/").child("images/"+list.get(position).getImagenamelist().get(i));
                storageRef.delete();
            }
            gooddelete(position);
        }
        else{
            gooddelete(position);
        }
    }

    private void gooddelete(final int position){
        if(!list.get(position).getGoodcnt().equals("0")){
            reference5 = FirebaseDatabase.getInstance().getReference("UserList");
            reference5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        userlist.add(snapshot1.getValue().toString());
                    }

                    for(int i=0; i<userlist.size(); i++){
                        reference6 = FirebaseDatabase.getInstance().getReference("Good").child(userlist.get(i)).child(list.get(position).getPostid());
                        if(reference6 != null){
                            reference6.removeValue();
                        }
                    }

                    postdelete(position);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            postdelete(position);
        }
    }*/
}