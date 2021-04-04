package com.example.anyseat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.anyseat.Notifications.APIService;
import com.example.anyseat.Notifications.Client;
import com.example.anyseat.Notifications.MyResponse;
import com.example.anyseat.Notifications.NotificationData;
import com.example.anyseat.Notifications.SendData;
import com.example.anyseat.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeBoardDetailActivity extends AppCompatActivity {

    TextView fbdtime;
    //Button fbdgoodbtn;
    Button fbdremakebtn;
    Button fbddelbtn;
    ImageView fbdbackbtn;
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
    DatabaseReference reference5;
    DatabaseReference reference6;
    DatabaseReference reference7;
    DatabaseReference reference8;
    DatabaseReference reference9;
    DatabaseReference reference10;
    DatabaseReference reference11;
    DatabaseReference reference12;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    private ViewPager viewPager;
    private FreeBoardDetailItemViewPagerAdapter viewPagerAdapter;

    Button pb;
    Button nb;

    FirebaseStorage storage;

    private ArrayList<String> userlist = new ArrayList<>();

    APIService apiService;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board_detail);

        userId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        final String postid = intent.getStringExtra("id");

        final String alrampostid = intent.getStringExtra("postid");

        final String alram = intent.getStringExtra("alram");
        final String alramid2 = intent.getStringExtra("alramid");
        if(alram != null && alram.equals("1")){  //알림 삭제
            reference12 = FirebaseDatabase.getInstance().getReference("Alram").child(userId).child(alramid2);
            reference12.removeValue();
        }

        fbdtime = findViewById(R.id.freeboarddetail_time);

        //fbdgoodbtn = findViewById(R.id.detail_good);

        fbdbackbtn = findViewById(R.id.detail_back);
        fbdbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alram != null && alram.equals("1")){
                    Intent intent1 = new Intent(FreeBoardDetailActivity.this, MainActivity2.class);
                    intent1.putExtra("alram", "1");
                    startActivity(intent1);
                    finish();
                }
                else{
                    finish();
                }
            }
        });

        fbdremakebtn = findViewById(R.id.detail_remake);
        fbdremakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference8 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
                reference8.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PostItem item = snapshot.getValue(PostItem.class);
                        Intent intent1 = new Intent(FreeBoardDetailActivity.this, WriteBoardActivity.class);
                        intent1.putExtra("postid", postid);
                        intent1.putExtra("goodcnt", item.getGoodcnt());
                        intent1.putExtra("commentcnt", item.getCommentcnt());
                        startActivity(intent1);
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        fbddelbtn = findViewById(R.id.detail_del);
        fbddelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                builder.setMessage("삭제하시겠습니까?");

                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                commentsdelete(postid);
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
                final String postid = intent.getStringExtra("id");
                reference = FirebaseDatabase.getInstance().getReference("Reply").push();
                final String replyid = reference.getKey();
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

                //댓글 시 알림

                reference9 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
                reference9.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final PostItem item = snapshot.getValue(PostItem.class);
                        final String reciverid = item.getUserid();

                        reference11 = FirebaseDatabase.getInstance().getReference("Alram").child(reciverid).push();
                        final String alramid = reference11.getKey();

                        long time = System.currentTimeMillis();

                        HashMap result = new HashMap<>();

                        result.put("type", "1");
                        result.put("writetime", makeTimeStamp(time));
                        result.put("postid", postid);
                        result.put("alramid", alramid);
                        result.put("replyid", replyid);

                        reference11.setValue(result);


                        reference10 = FirebaseDatabase.getInstance().getReference("UserList").child(reciverid);
                        reference10.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final Token item1 = snapshot.getValue(Token.class);
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        APIService apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
                                        apiService.sendNotification(new NotificationData(new SendData(item.getContents(), item.getPostid(), "1", alramid, replyid), item1.getToken()))
                                                .enqueue(new Callback<MyResponse>() {
                                                    @Override
                                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                                        if(response.code() == 200){
                                                            if(response.body().success == 1){
                                                                Log.e("Notification", "success");
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<MyResponse> call, Throwable t) {

                                                    }
                                                });
                                    }
                                };


                                if(!userId.equals(reciverid)){
                                    Thread tr = new Thread(runnable);
                                    tr.start();


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        recyclerView = findViewById(R.id.free_board_recyclerview2);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        if(alrampostid != null){    //알림으로 들어왔을 때

            reference5 = FirebaseDatabase.getInstance().getReference("Post").child(alrampostid);
            reference5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PostItem item = snapshot.getValue(PostItem.class);
                    if(item.getUserid().equals(userId)){
                        fbddelbtn.setVisibility(View.VISIBLE);
                        fbdremakebtn.setVisibility(View.VISIBLE);
                    }
                    else{
                        fbddelbtn.setVisibility(View.INVISIBLE);
                        fbdremakebtn.setVisibility(View.INVISIBLE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference = FirebaseDatabase.getInstance().getReference("Post").child(alrampostid);
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
                                if(item1.getPostid().equals(alrampostid)){
                                    //FreeBoardDetailItem item1 = snapshot1.getValue(FreeBoardDetailItem.class);
                                    list.add(new FreeBoardDetailItem(item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getReplyid(), item1.getUserid(), Code.ViewType.SECOND));
                                }
                            }

                            reference3 = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(alrampostid);
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
                                        public void onGoodClick(View v, int position, ImageView btn) {
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
                                                            commentdelete(alrampostid, position);
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

                    reference4 = FirebaseDatabase.getInstance().getReference("Post").child(alrampostid);
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
                                        if(item1.getPostid().equals(alrampostid)){
                                            list.add(new FreeBoardDetailItem(item1.getContents(), item1.getWritetime(), item1.getPostid(), item1.getReplyid(), item1.getUserid(), Code.ViewType.SECOND));
                                        }
                                    }

                                    reference3 = FirebaseDatabase.getInstance().getReference("Good").child(userId).child(alrampostid);
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
                                                public void onGoodClick(View v, int position, ImageView btn) {
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
                                                                    commentdelete(alrampostid, position);
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



            return;
        }



        reference5 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getUserid().equals(userId)){
                    fbddelbtn.setVisibility(View.VISIBLE);
                    fbdremakebtn.setVisibility(View.VISIBLE);
                }
                else{
                    fbddelbtn.setVisibility(View.INVISIBLE);
                    fbdremakebtn.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                                    public void onGoodClick(View v, int position, ImageView btn) {
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
                                            public void onGoodClick(View v, int position, ImageView btn) {
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

    public void goodplus(final String postId, final String userId, ImageView btn){

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
                                //recyclerView.setAdapter(adapter);
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

                                //recyclerView.setAdapter(adapter);
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

                            //recyclerView.setAdapter(adapter);
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


    private void postdelete(String postid){
        //String postid = list.get(position).getPostid();
        reference2 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference2.removeValue();
    }

    private void commentsdelete(final String postid){
        //final String postid = list.get(position).getPostid();
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

        imagedelete(postid);
    }

    private void imagedelete(final String postid){
        reference3 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getImageexist().equals("1")){//이미지가 있을 때
                    for(int i=0;i<item.getImagenamelist().size();i++){
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://anyseat-4e964.appspot.com/").child("images/"+item.getImagenamelist().get(i));
                        storageRef.delete();
                    }
                    gooddelete(postid);
                }
                else{
                    gooddelete(postid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gooddelete(final String postid){
        reference3 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final PostItem item = snapshot.getValue(PostItem.class);
                if(!item.getGoodcnt().equals("0")){
                    reference6 = FirebaseDatabase.getInstance().getReference("UserList");
                    reference6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                userlist.add(snapshot1.getValue().toString());
                            }

                            for(int i=0; i<userlist.size(); i++){
                                reference7 = FirebaseDatabase.getInstance().getReference("Good").child(userlist.get(i)).child(item.getPostid());
                                if(reference7 != null){
                                    reference7.removeValue();
                                }
                            }

                            postdelete(postid);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    postdelete(postid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private String makeTimeStamp(long in){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        return format.format(in);
    }


    /*private void sendNotification(final String receiverId, final String senderName, final int type, final String content, final String key) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserList").child(receiverId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Token token = snapshot.getValue(Token.class);
                SendData sendData = new SendData(senderName, content, type + "", key);
                NotificationData notificationData = new NotificationData(sendData, token.getToken());
                Log.e("Token", token.getToken());
                apiService.sendNotification(notificationData)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                Log.e("code", response.message());
                                if (response.code() == 200) {
                                    if (response.body().success == 1) {
                                        Log.e("Notification", "success");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Log.e("onFailure", t.getMessage());
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}