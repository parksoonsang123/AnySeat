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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {


    RecyclerView recyclerView1;

    private ArrayList<PostItem> list = new ArrayList<>();

    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;

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
                SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //회원탈퇴
        Button SignOutButton = view.findViewById(R.id.out);
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder signout = new AlertDialog.Builder(view.getContext());
                signout.setTitle("회원탈퇴");
                signout.setMessage("한번 회원 탈퇴한 계정의 정보는 개발자도 복구시켜주지 않습니다. 정말 삭제하실건가요....?");
                signout.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference2.child(user.Password).removeValue();
                        SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.exit(0);
                                }
                            }
                        });
                    }
                });
                signout.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                signout.show();
            }
        });

        return view;
    }
}