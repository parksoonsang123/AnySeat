package com.example.anyseat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anyseat.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {


    RecyclerView recyclerView1;

    private ArrayList<PostItem> list = new ArrayList<>();
    private ArrayList<Token> Userlist2 = new ArrayList<>();
    private ArrayList<Token> Userlist3 = new ArrayList<>();
    private ArrayList<String> alramidlist = new ArrayList<>();
    private ArrayList<String> alramuseridlist = new ArrayList<>();

    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;

    DatabaseReference de1_reference;
    DatabaseReference de2_reference;
    DatabaseReference de3_reference;
    DatabaseReference de4_reference;
    DatabaseReference de5_reference;
    DatabaseReference de6_reference;
    DatabaseReference de7_reference;
    DatabaseReference de8_reference;
    DatabaseReference de9_reference;
    DatabaseReference de10_reference;



    FirebaseStorage storage;

    HomeAdapter adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;
    UserInfo user;

    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        // Inflate the layout for this fragment


        textView1 = view.findViewById(R.id.mypage_name);
        textView2 = view.findViewById(R.id.mypage_grade);
        textView3 = view.findViewById(R.id.mypage_id);

        recyclerView1 = view.findViewById(R.id.mypage_rcv1);

        userId = mAuth.getCurrentUser().getUid();

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
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
                    if(item.uid != null && item.uid.equals(userId)){
                        user = item;
                        textView1.setText(item.Name);
                        textView2.setText(item.Grade+"");
                        textView3.setText(item.Email);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //비밀번호변경
        Button EditButton = view.findViewById(R.id.remakge);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog = new EditDialog(view.getContext(), user.Password);
                editDialog.show();
            }
        });

        // 로그아웃
        Button LogOutButton = view.findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logout = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
                logout.setTitle("로그아웃");
                logout.setMessage("로그아웃 하시겠습니까?");
                logout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //
                    }
                });
                logout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();


            }
        });


        reference3 = FirebaseDatabase.getInstance().getReference("UserList");

        //회원탈퇴
        Button SignOutButton = view.findViewById(R.id.out);
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder signout = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
                signout.setTitle("회원 탈퇴");
                signout.setMessage("탈퇴 하시겠습니까..?");
                signout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i=0;
                        for(i=0;i<=list.size();i++){
                            if(i==list.size()) break;
                            String postid = list.get(i).getPostid();
                            commentsdelete(postid);
                        }

                        /*if(i == list.size()){
                            reference2.child(user.uid).removeValue();
                            SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                            reference3.child(user.uid).removeValue();
                            mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        System.exit(0);
                                    }
                                }
                            });
                        }*/

                    }
                });
                signout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                signout.show();
            }
        });

        return view;
    }

    private void postdelete(String postid){
        //String postid = list.get(position).getPostid();

    }

    private void commentsdelete(final String postid){
        //final String postid = list.get(position).getPostid();
        de2_reference = FirebaseDatabase.getInstance().getReference("Reply");
        de2_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ReplyItem item1 = snapshot1.getValue(ReplyItem.class);
                    if(item1.getPostid().equals(postid)){
                        de4_reference = FirebaseDatabase.getInstance().getReference("Reply").child(item1.getReplyid());
                        de4_reference.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        de5_reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        de5_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getImageexist().equals("1")){//이미지가 있을 때
                    for(int i=0;i<item.getImagenamelist().size();i++){
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://anyseat-4e964.appspot.com/").child("images/"+item.getImagenamelist().get(i));
                        storageRef.delete();
                    }
                }
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Userlist2.clear();

        de6_reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        de6_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final PostItem item = snapshot.getValue(PostItem.class);
                if(!item.getGoodcnt().equals("0")){
                    de7_reference = FirebaseDatabase.getInstance().getReference("UserList");
                    de7_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                Userlist2.add(snapshot1.getValue(Token.class));
                            }

                            for(int i=0; i<Userlist2.size(); i++){

                                de8_reference = FirebaseDatabase.getInstance().getReference("Good").child(Userlist2.get(i).getUID()).child(postid);
                                de8_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String s = snapshot.child("press").getValue(String.class);
                                        if(s==null){

                                        }
                                        else{
                                            de8_reference.removeValue();
                                        }
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
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Userlist3.clear();
        alramidlist.clear();
        alramuseridlist.clear();

        de9_reference = FirebaseDatabase.getInstance().getReference("UserList");
        de9_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Userlist3.add(snapshot1.getValue(Token.class));
                }

                for(int i = 0; i<Userlist3.size(); i++){
                    final int idx = i;
                    de10_reference = FirebaseDatabase.getInstance().getReference("Alram").child(Userlist3.get(i).getUID());
                    de10_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                AlramItem item = snapshot1.getValue(AlramItem.class);
                                if(item.getPostid().equals(postid)){
                                    alramidlist.add(item.getAlramid());
                                    alramuseridlist.add(Userlist3.get(idx).getUID());

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                if(Userlist3.size() == 0){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for(int i=0;i<alramidlist.size();i++){
            de8_reference = FirebaseDatabase.getInstance().getReference("Alram").child(alramuseridlist.get(i)).child(alramidlist.get(i));
            de8_reference.removeValue();
        }

        de1_reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        de1_reference.removeValue();
    }

    private void imagedelete(final String postid){

    }

    private void gooddelete(final String postid){


    }

    //AlramItem temp;
    //String s;

    private void alarmdelete2(String postId){



    }

    private void alarmdelete(final String postId){

    }
}