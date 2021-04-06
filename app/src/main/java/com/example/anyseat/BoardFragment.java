package com.example.anyseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;


public class BoardFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_board, container, false);

        FloatingActionButton fab = view.findViewById(R.id.freeboard_floatingactionbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), WriteBoardActivity.class);
                startActivity(intent);
            }
        });

        userId = mAuth.getCurrentUser().getUid();
        freeboardRecyclerView = view.findViewById(R.id.free_board_recyclerview1);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
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

                Collections.sort(list, new Ascending());

                adapter = new FreeBoardAdapter(list, userId);
                adapter.setOnDeleteClickListener2(new FreeBoardAdapter.OnDeleteClickListner2() {
                    @Override
                    public void onDeleteClick2(View v, int position, Button btn) {
                        //댓글 -> 이미지 -> 좋아요 -> 포스트 삭제
                        commentsdelete(position);
                    }
                });
                freeboardRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void postdelete(int position){
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
        if(list.get(position).getImageexist().equals("1")){ //이미지가 있을 때
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
                        reference6 = FirebaseDatabase.getInstance().getReference("Good").child(list.get(position).getPostid()).child(userlist.get(i));
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
    }

    class Ascending implements Comparator<PostItem> {

        @Override
        public int compare(PostItem o1, PostItem o2) {
            return o2.getWritetime().compareTo(o1.getWritetime());
        }

    }
}