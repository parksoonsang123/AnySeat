package com.example.anyseat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {
   /* SeatInfo[] seatInfos = new SeatInfo[12];
    String[] seatarray= {"L11",""};
    UserInfo user;
    SaveSharedPreference main;

    ArrayList<UserInfo> arrayList = new ArrayList<>();
    DatabaseReference mDatabase;
    private TextView profile_id;

    Button L11 ;
    Button L12 ;
    Button L21 ;
    Button L22 ;
    Button L31 ;
    Button L32 ;
    Button L41 ;
    Button L42 ;
    Button R1 ;
    Button R2 ;
    Button R3 ;
    Button R4 ;
    String Password;

    public HomeFragment(String password) {
        Password = password;
    }*/

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;

    private ArrayList<UserInfo> list3 = new ArrayList<>();

    private ArrayList<PostItem> list = new ArrayList<>();

    private ArrayList<PostItem2> list2 = new ArrayList<>();

    DatabaseReference reference2;
    DatabaseReference reference3;
    DatabaseReference reference4;
    DatabaseReference reference5;
    DatabaseReference reference6;

    TextView status_txt;
    ImageView status_img;

    Button finish;
    Button add;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String userId;

    NoticeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        status_img = view.findViewById(R.id.homepage_status_img);
        status_txt = view.findViewById(R.id.homepage_status_txt);
        finish = view.findViewById(R.id.finish_btn);
        add = view.findViewById(R.id.notice_add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), WriteNoticeActivity.class);
                startActivity(intent);
            }
        });

        recyclerView2 = view.findViewById(R.id.home_rcv2);

        userId = mAuth.getCurrentUser().getUid();

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(manager);


        reference2 = FirebaseDatabase.getInstance().getReference("UserInfo");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInfo item = snapshot1.getValue(UserInfo.class);

                    if(item.uid != null && item.uid.equals(userId)){
                        if(item.Master == 1){
                            add.setVisibility(View.VISIBLE);
                        }
                        else{
                            add.setVisibility(View.GONE);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference6 = FirebaseDatabase.getInstance().getReference("Notice");
        reference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostItem2 item = snapshot1.getValue(PostItem2.class);

                    list2.add(item);

                }
                Collections.sort(list2, new Ascending());
                adapter = new NoticeAdapter(list2);
                recyclerView2.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        reference3 = FirebaseDatabase.getInstance().getReference("UserInfo");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInfo item = snapshot1.getValue(UserInfo.class);
                    if(item.uid != null && item.uid.equals(userId)){
                        if(!item.using.equals("false")){
                            final String seatname = item.using;
                            final String pw = item.Password;
                            status_img.setImageResource(R.drawable.studyimage);
                            status_txt.setText("사용중 입니다.");
                            finish.setVisibility(View.VISIBLE);
                            finish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                                    builder.setMessage("사용을 종료하시겠습니까?");

                                    builder.setPositiveButton("확인",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    reference4 = FirebaseDatabase.getInstance().getReference("SeatInfo").child(seatname);
                                                    reference4.child("status").setValue("off");
                                                    reference4.child("statusnum").setValue(0);
                                                    reference4.child("user").setValue("빈 자리");
                                                    reference5 = FirebaseDatabase.getInstance().getReference("UserInfo").child(pw);
                                                    reference5.child("using").setValue("false");
                                                    status_img.setImageResource(0);
                                                    status_txt.setText("사용중이 아닙니다.");
                                                    finish.setVisibility(View.INVISIBLE);
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
                        }
                        else{
                            status_img.setImageResource(0);
                            status_txt.setText("사용중이 아닙니다.");
                            finish.setVisibility(View.INVISIBLE);
                        }

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // 오픈채팅
        Button OpenKakaoButton = (Button)view.findViewById(R.id.OpenChatButton);
        OpenKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChatUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("https://open.kakao.com/o/sULfouRc/"));
                startActivity(ChatUrl);
            }
        });

        // 조직도
        Button Organizatino_Button = (Button)view.findViewById(R.id.Organization_Button);
        Organizatino_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Organization.class);
                startActivity(intent);
            }
        });

        // 과사 시간표
        Button OfficeTimeTable_Button = (Button)view.findViewById(R.id.OfficeTimeTable);
        OfficeTimeTable_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.timetabledialog);
                dialog.show();
            }
        });


        //학과 홈페이지
        Button homepage_btn = (Button)view.findViewById(R.id.homepage_btn);
        homepage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        /*// 로그아웃
        Button LogOutButton = (Button)view.findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                getActivity().finish();
            }
        });*/


        return view;
    }

    class Ascending implements Comparator<PostItem2> {

        @Override
        public int compare(PostItem2 o1, PostItem2 o2) {
            return o2.getWritetime().compareTo(o1.getWritetime());
        }

    }
}