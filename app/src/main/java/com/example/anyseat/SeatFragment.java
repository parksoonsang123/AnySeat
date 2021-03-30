package com.example.anyseat;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeatFragment extends Fragment {
    SeatInfo[] seatInfos = new SeatInfo[12];
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

    public SeatFragment(String password) {
        Password = password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_seat, container, false);

        L11 = (Button)view.findViewById(R.id.L11);
        L12 = (Button)view.findViewById(R.id.L12);
        L21 = (Button)view.findViewById(R.id.L21);
        L22 = (Button)view.findViewById(R.id.L22);
        L31 = (Button)view.findViewById(R.id.L31);
        L32 = (Button)view.findViewById(R.id.L32);
        L41 = (Button)view.findViewById(R.id.L41);
        L42 = (Button)view.findViewById(R.id.L42);
        R1 = (Button)view.findViewById(R.id.R1);
        R2 = (Button)view.findViewById(R.id.R2);
        R3 = (Button)view.findViewById(R.id.R3);
        R4 = (Button)view.findViewById(R.id.R4);

        profile_id = (TextView)view.findViewById(R.id.profile_id);

        final String[] nickName = new String[1];

        L11.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L12.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L21.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L22.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L31.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L32.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L41.setOnClickListener(new buttonlistener(view.getContext(), Password));
        L42.setOnClickListener(new buttonlistener(view.getContext(), Password));
        R1.setOnClickListener(new buttonlistener(view.getContext(), Password));
        R2.setOnClickListener(new buttonlistener(view.getContext(), Password));
        R3.setOnClickListener(new buttonlistener(view.getContext(), Password));
        R4.setOnClickListener(new buttonlistener(view.getContext(), Password));

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("SeatInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        SeatInfo seatInfo = dataSnapshot.getValue(SeatInfo.class);
                        arrayList.add(seatInfo);
                    }
                }*/

                //L1-1
                int statusnum;
                statusnum = snapshot.child("L1-1").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L11.setText("사용 중");
                else L11.setText("빈 자리");

                //L1-2
                statusnum = snapshot.child("L1-2").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L12.setText("사용 중");
                else L12.setText("빈 자리");

                //L2-1
                statusnum = snapshot.child("L2-1").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L21.setText("사용 중");
                else L21.setText("빈 자리");

                //L2-2
                statusnum = snapshot.child("L2-2").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L22.setText("사용 중");
                else L22.setText("빈 자리");

                //L3-1
                statusnum = snapshot.child("L3-1").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L31.setText("사용 중");
                else L31.setText("빈 자리");

                //L3-2
                statusnum = snapshot.child("L3-2").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L32.setText("사용 중");
                else L32.setText("빈 자리");

                //L4-1
                statusnum = snapshot.child("L4-1").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L41.setText("사용 중");
                else L41.setText("빈 자리");

                //L4-2
                statusnum = snapshot.child("L4-2").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) L42.setText("사용 중");
                else L42.setText("빈 자리");

                //R1
                statusnum = snapshot.child("R1").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) R1.setText("사용 중");
                else R1.setText("빈 자리");

                //R2
                statusnum = snapshot.child("R2").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) R2.setText("사용 중");
                else R2.setText("빈 자리");

                //R3
                statusnum = snapshot.child("R3").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) R3.setText("사용 중");
                else R3.setText("빈 자리");

                //R4
                statusnum = snapshot.child("R4").child("statusnum").getValue(Integer.class);
                if(statusnum == 1) R4.setText("사용 중");
                else R4.setText("빈 자리");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("UserInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(Password).getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //  mDatabase = FirebaseDatabase.getInstance().getReference();

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

        Button OfficeNotice_Button = (Button)view.findViewById(R.id.OfficeNotice);
        OfficeNotice_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        // 로그아웃
        Button LogOutButton = (Button)view.findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                getActivity().finish();
            }
        });


        return view;
    }
}