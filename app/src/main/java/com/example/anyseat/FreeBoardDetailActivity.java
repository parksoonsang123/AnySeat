package com.example.anyseat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FreeBoardDetailActivity extends AppCompatActivity {

    TextView fbdtime;
    Button fbdgoodbtn;
    TextView fbdtitle;
    TextView fbdcontents;
    TextView fbdgoodcnt;
    TextView fbdcommentcnt;

    EditText fbdedit;
    Button fbdsendbtn;

    private ArrayList<FreeBoardDetailItem> list = new ArrayList<>();

    RecyclerView recyclerView;
    FreeBoardDetailAdapter adapter;

    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    DatabaseReference reference4;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    private ViewPager viewPager;
    private FreeBoardDetailItemViewPagerAdapter viewPagerAdapter;

    Button pb;
    Button nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board_detail);



        userId = mAuth.getCurrentUser().getUid();

        fbdtime = findViewById(R.id.freeboarddetail_time);
        fbdgoodbtn = findViewById(R.id.freeboarddetail_goodbtn);

        fbdtitle = findViewById(R.id.freeboarddetail_title);
        fbdcontents = findViewById(R.id.freeboarddetail_contents);
        fbdgoodcnt = findViewById(R.id.freeboarddetail_goodcnt);
        fbdcommentcnt = findViewById(R.id.freeboarddetail_commentcnt);

        fbdedit = findViewById(R.id.freeboard_edit);
        fbdsendbtn = findViewById(R.id.freeboard_sendbtn);
        fbdsendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String postid = intent.getStringExtra("id");
                reference = FirebaseDatabase.getInstance().getReference("Reply").push();
                String replyid = reference.getKey();
                long time = System.currentTimeMillis();

                HashMap result = new HashMap<>();

                result.put("contents", fbdedit.getText().toString());
                result.put("writetime", makeTimeStamp(time));
                result.put("postid", postid);
                result.put("replyid", replyid);
                result.put("userid", userId);

                reference.setValue(result);

                //업데이트
                list.add(new FreeBoardDetailItem(fbdedit.getText().toString(), makeTimeStamp(time), postid, replyid, userId, 2));

                adapter.notifyItemInserted(list.size()-1);

                //댓글 수 1 증가
                commentplus(postid);

                //버튼 클릭 후 edittext 초기화 & 키보드 내리기
                fbdedit.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(fbdedit.getWindowToken(),0);
            }
        });

        recyclerView = findViewById(R.id.free_board_recyclerview2);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        Intent intent = getIntent();
        final String postid = intent.getStringExtra("id");

        reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                PostItem item = snapshot.getValue(PostItem.class);
                list.add(new FreeBoardDetailItem(item.getCommentcnt(), item.getGoodcnt(), item.getTitle(), item.getContents(), item.getWritetime(), postid, userId, item.getImageexist(), item.getImageurilist(), item.getImagenamelist(), Code.ViewType.FIRST));
                reference2 = FirebaseDatabase.getInstance().getReference("Reply");
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            ReplyItem item1 = snapshot1.getValue(ReplyItem.class);
                            if(item1.getPostid().equals(postid)){
                                //FreeBoardDetailItem item1 = snapshot1.getValue(FreeBoardDetailItem.class);
                                list.add(new FreeBoardDetailItem(item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getReplyid(), item1.getUserid(), Code.ViewType.SECOND));
                            }
                        }

                        reference3 = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postid);
                        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //Toast.makeText(FreeBoardDetailActivity.this, "실행", Toast.LENGTH_SHORT).show();
                                GoodItem item = snapshot.getValue(GoodItem.class);
                                if(item == null){
                                    list.get(0).setPress("0");
                                }
                                else{
                                    list.get(0).setPress(item.getPress());
                                }

                                adapter = new FreeBoardDetailAdapter(list, userId, FreeBoardDetailActivity.this);
                                adapter.setOnGoodClickListner(new FreeBoardDetailAdapter.OnGoodClickListener() {
                                    @Override
                                    public void onGoodClick(View v, int position, Button btn) {
                                        Intent intent = getIntent();
                                        String postId = intent.getStringExtra("id");
                                        goodplus(postId, userId, btn);
                                    }
                                });
                                adapter.setOnDeleteClickListener(new FreeBoardDetailAdapter.OnDeleteClickListner() {
                                    @Override
                                    public void onDeleteClick(View v, final int position, Button btn) {
                                        Intent intent = getIntent();
                                        final String postId = intent.getStringExtra("id");

                                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);


                                        builder.setMessage("삭제하시겠습니까?");


                                        builder.setPositiveButton("확인",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        commentdelete(postId, position);
                                                    }
                                                });
                                        builder.setNegativeButton("취소",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });
                                        builder.show();

                                        //commentminus(postid);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



        reference.addChildEventListener(new ChildEventListener() {  //댓글,좋아요 개수 변화
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                PostItem item = new PostItem();
                if(snapshot.getKey().equals("commentcnt")){
                    item.setCommentcnt(snapshot.getValue().toString());
                    list.get(0).setCommentcnt(item.getCommentcnt());
                }
                if(snapshot.getKey().equals("goodcnt")){
                    item.setGoodcnt(snapshot.getValue().toString());
                    list.get(0).setGoodcnt(item.getGoodcnt());
                }

                reference4 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        PostItem item = snapshot.getValue(PostItem.class);
                        list.add(new FreeBoardDetailItem(item.getCommentcnt(), item.getGoodcnt(), item.getTitle(), item.getContents(), item.getWritetime(), postid, userId, item.getImageexist(), item.getImageurilist(), item.getImagenamelist(), Code.ViewType.FIRST));
                        reference2 = FirebaseDatabase.getInstance().getReference("Reply");
                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    ReplyItem item1 = snapshot1.getValue(ReplyItem.class);
                                    if(item1.getPostid().equals(postid)){
                                        list.add(new FreeBoardDetailItem(item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getReplyid(), item1.getUserid(), Code.ViewType.SECOND));
                                    }
                                }

                                reference3 = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postid);
                                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        GoodItem item = snapshot.getValue(GoodItem.class);
                                        if(item == null){
                                            list.get(0).setPress("0");
                                        }
                                        else{
                                            list.get(0).setPress(item.getPress());
                                        }

                                        adapter = new FreeBoardDetailAdapter(list, userId, FreeBoardDetailActivity.this);
                                        adapter.setOnGoodClickListner(new FreeBoardDetailAdapter.OnGoodClickListener() {
                                            @Override
                                            public void onGoodClick(View v, int position, Button btn) {
                                                Intent intent = getIntent();
                                                String postId = intent.getStringExtra("id");
                                                goodplus(postId, userId, btn);
                                            }
                                        });
                                        adapter.setOnDeleteClickListener(new FreeBoardDetailAdapter.OnDeleteClickListner() {
                                            @Override
                                            public void onDeleteClick(View v, final int position, Button btn) {
                                                Intent intent = getIntent();
                                                final String postId = intent.getStringExtra("id");

                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                                                builder.setMessage("삭제하시겠습니까?");


                                                builder.setPositiveButton("확인",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                commentdelete(postId, position);
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
                                        recyclerView.setAdapter(adapter);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void commentdelete(String postid, int position){
        String replyid = list.get(position).getReplyid();
        reference = FirebaseDatabase.getInstance().getReference("Reply").child(replyid);
        reference.removeValue();

        commentminus(postid);
    }

    private void commentminus(String postId){
        reference = FirebaseDatabase.getInstance().getReference("Post").child(postId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                String commentcnt = item.getCommentcnt();
                int cmtcnt = Integer.parseInt(commentcnt) - 1;
                String commentcnt2 = Integer.toString(cmtcnt);
                item.setCommentcnt(commentcnt2);
                reference.setValue(item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void commentplus(String postId){

        reference = FirebaseDatabase.getInstance().getReference("Post").child(postId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                String commentcnt = item.getCommentcnt();
                int cmtcnt = Integer.parseInt(commentcnt) + 1;
                String commentcnt2 = Integer.toString(cmtcnt);
                item.setCommentcnt(commentcnt2);
                reference.setValue(item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goodplus(final String postId, final String userId, Button btn){

        reference2 = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postId);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GoodItem item2 = snapshot.getValue(GoodItem.class);
                if(item2 != null){
                    if(item2.getPress().equals("1")){
                        reference = FirebaseDatabase.getInstance().getReference("Post").child(postId);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                PostItem item = snapshot.getValue(PostItem.class);
                                String goodcnt = item.getGoodcnt();
                                int gcnt = Integer.parseInt(goodcnt) - 1;
                                String goodcnt2 = Integer.toString(gcnt);
                                item.setGoodcnt(goodcnt2);

                                reference.setValue(item);

                                reference = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postId);
                                HashMap result = new HashMap<>();
                                result.put("press", "0");

                                reference.setValue(result);
                                list.get(0).setPress("0");
                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else if(item2.getPress().equals("0")){
                        reference = FirebaseDatabase.getInstance().getReference("Post").child(postId);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                PostItem item = snapshot.getValue(PostItem.class);
                                String goodcnt = item.getGoodcnt();
                                int gcnt = Integer.parseInt(goodcnt) + 1;
                                String goodcnt2 = Integer.toString(gcnt);
                                item.setGoodcnt(goodcnt2);
                                reference.setValue(item);

                                reference = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postId);
                                HashMap result = new HashMap<>();
                                result.put("press", "1");
                                reference.setValue(result);
                                list.get(0).setPress("1");

                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                else if(item2 == null){
                    reference = FirebaseDatabase.getInstance().getReference("Post").child(postId);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PostItem item = snapshot.getValue(PostItem.class);
                            String goodcnt = item.getGoodcnt();
                            int gcnt = Integer.parseInt(goodcnt) + 1;
                            String goodcnt2 = Integer.toString(gcnt);
                            item.setGoodcnt(goodcnt2);
                            reference.setValue(item);

                            reference = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(postId);
                            HashMap result = new HashMap<>();
                            result.put("press", "1");
                            reference.setValue(result);
                            list.get(0).setPress("1");

                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String makeTimeStamp(long in){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return format.format(in);
    }
}