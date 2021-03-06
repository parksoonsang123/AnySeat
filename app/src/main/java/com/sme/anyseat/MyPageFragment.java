package com.sme.anyseat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sme.anyseat.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    DatabaseReference reference4;
    DatabaseReference reference5;

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

    int flag = 0;

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

        //??????????????????
        Button EditButton = view.findViewById(R.id.remakge);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog = new EditDialog(view.getContext(), userId);
                editDialog.show();
            }
        });

        // ????????????
        Button LogOutButton = view.findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logout = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
                logout.setTitle("????????????");
                logout.setMessage("???????????? ???????????????????");
                logout.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                        /*Intent intent = new Intent(view.getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                        startActivity(new Intent(view.getContext(), MainActivity.class)
                                .setAction(Intent.ACTION_MAIN)
                                .addCategory(Intent.CATEGORY_LAUNCHER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        //mAuth.signOut();
                        System.exit(0);

                    }
                });
                logout.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();


            }
        });


        reference3 = FirebaseDatabase.getInstance().getReference("UserList");

        //????????????
        Button SignOutButton = view.findViewById(R.id.out);
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder signout = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
                signout.setTitle("?????? ??????");
                signout.setMessage("?????? ??????????????????..?");
                signout.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(list.size()==0){
                            if(!user.using.equals("false")) {
                                reference4 = FirebaseDatabase.getInstance().getReference("SeatInfo").child(user.using);
                                reference4.child("status").setValue("off");
                                reference4.child("statusnum").setValue(0);
                                reference4.child("user").setValue("??? ??????");
                                reference5 = FirebaseDatabase.getInstance().getReference("UserInfo").child(userId);
                                reference5.child("using").setValue("false");
                            }

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
                        }
                        else {
                            for (int i = 0; i < list.size(); i++) {
                                flag = i;
                                String postid = list.get(i).getPostid();
                                commentsdelete(postid);
                            }
                        }

                    }
                });
                signout.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
        de1_reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        de1_reference.removeValue();

        if(flag == list.size()-1){

            if(!user.using.equals("false")) {
                reference4 = FirebaseDatabase.getInstance().getReference("SeatInfo").child(user.using);
                reference4.child("status").setValue("off");
                reference4.child("statusnum").setValue(0);
                reference4.child("user").setValue("??? ??????");
                reference5 = FirebaseDatabase.getInstance().getReference("UserInfo").child(userId);
                reference5.child("using").setValue("false");
            }

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
        }

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

        imagedelete(postid);
    }

    private void imagedelete(final String postid){
        de5_reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        de5_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getImageexist().equals("1")){//???????????? ?????? ???
                    for(int i=0;i<item.getImagenamelist().size();i++){
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com/").child("images/"+item.getImagenamelist().get(i));
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

                                de3_reference = FirebaseDatabase.getInstance().getReference("Good").child(item.getPostid());
                                if(de3_reference != null){
                                    de3_reference.removeValue();
                                }
                            }

                            alarmdelete(postid);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    alarmdelete(postid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //AlramItem temp;
    //String s;

    private void alarmdelete2(String postId){

        for(int i=0;i<alramidlist.size();i++){
            de8_reference = FirebaseDatabase.getInstance().getReference("Alram").child(alramuseridlist.get(i)).child(alramidlist.get(i));
            de8_reference.removeValue();
        }

        postdelete(postId);
    }

    private void alarmdelete(final String postId){
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
                                if(item.getPostid().equals(postId)){
                                    alramidlist.add(item.getAlramid());
                                    alramuseridlist.add(Userlist3.get(idx).getUID());



                                    /*reference14 = FirebaseDatabase.getInstance().getReference("Alram").child(alramuseridlist.get(idx)).child(alramidlist.get(idx));
                                    reference14.removeValue();*/
                                }
                            }
                            alarmdelete2(postId);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                if(Userlist3.size() == 0){
                    postdelete(postId);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}