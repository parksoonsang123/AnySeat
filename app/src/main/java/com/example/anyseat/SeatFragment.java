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
import android.widget.ImageView;
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
    ArrayList<SeatInfo> seatlist = new ArrayList<>();
    DatabaseReference mDatabase;
    private TextView profile_id;

    ImageView L11 ;
    ImageView L21 ;
    ImageView L31 ;
    ImageView L41 ;
    ImageView L51 ;
    ImageView L61 ;
    ImageView L71 ;
    ImageView L81 ;

    ImageView L12 ;
    ImageView L22 ;
    ImageView L32 ;
    ImageView L42 ;
    ImageView L52 ;
    ImageView L62 ;
    ImageView L72 ;
    ImageView L82 ;

    ImageView L13 ;
    ImageView L23 ;
    ImageView L33 ;
    ImageView L43 ;
    ImageView L53 ;
    ImageView L63 ;
    ImageView L73 ;

    ImageView L14 ;
    ImageView L24 ;
    ImageView L34 ;
    ImageView L44 ;
    ImageView L54 ;
    ImageView L64 ;
    ImageView L74 ;

    ImageView R11 ;
    ImageView R21 ;
    ImageView R31 ;
    ImageView R41 ;
    ImageView R51 ;
    ImageView R61 ;
    ImageView R71 ;
    ImageView R81 ;

    ImageView R12 ;
    ImageView R22 ;
    ImageView R32 ;
    ImageView R42 ;
    ImageView R52 ;
    ImageView R62 ;
    ImageView R72 ;
    ImageView R82 ;

    String Password;


    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public SeatFragment(String password) {
        Password = password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_seat, container, false);

        L11 = (ImageView) view.findViewById(R.id.L11);
        L21 = (ImageView) view.findViewById(R.id.L21);
        L31 = (ImageView) view.findViewById(R.id.L31);
        L41 = (ImageView) view.findViewById(R.id.L41);
        L51 = (ImageView) view.findViewById(R.id.L51);
        L61 = (ImageView) view.findViewById(R.id.L61);
        L71 = (ImageView) view.findViewById(R.id.L71);
        L81 = (ImageView) view.findViewById(R.id.L81);

        L12 = (ImageView) view.findViewById(R.id.L12);
        L22 = (ImageView) view.findViewById(R.id.L22);
        L32 = (ImageView) view.findViewById(R.id.L32);
        L42 = (ImageView) view.findViewById(R.id.L42);
        L52 = (ImageView) view.findViewById(R.id.L52);
        L62 = (ImageView) view.findViewById(R.id.L62);
        L72 = (ImageView) view.findViewById(R.id.L72);
        L82 = (ImageView) view.findViewById(R.id.L82);

        L13 = (ImageView) view.findViewById(R.id.L13);
        L23 = (ImageView) view.findViewById(R.id.L23);
        L33 = (ImageView) view.findViewById(R.id.L33);
        L43 = (ImageView) view.findViewById(R.id.L43);
        L53 = (ImageView) view.findViewById(R.id.L53);
        L63 = (ImageView) view.findViewById(R.id.L63);
        L73 = (ImageView) view.findViewById(R.id.L73);

        L14 = (ImageView) view.findViewById(R.id.L14);
        L24 = (ImageView) view.findViewById(R.id.L24);
        L34 = (ImageView) view.findViewById(R.id.L34);
        L44 = (ImageView) view.findViewById(R.id.L44);
        L54 = (ImageView) view.findViewById(R.id.L54);
        L64 = (ImageView) view.findViewById(R.id.L64);
        L74 = (ImageView) view.findViewById(R.id.L74);

        R11 = (ImageView) view.findViewById(R.id.R11);
        R21 = (ImageView) view.findViewById(R.id.R21);
        R31 = (ImageView) view.findViewById(R.id.R31);
        R41 = (ImageView) view.findViewById(R.id.R41);
        R51 = (ImageView) view.findViewById(R.id.R51);
        R61 = (ImageView) view.findViewById(R.id.R61);
        R71 = (ImageView) view.findViewById(R.id.R71);
        R81 = (ImageView) view.findViewById(R.id.R81);

        R12 = (ImageView) view.findViewById(R.id.R12);
        R22 = (ImageView) view.findViewById(R.id.R22);
        R32 = (ImageView) view.findViewById(R.id.R32);
        R42 = (ImageView) view.findViewById(R.id.R42);
        R52 = (ImageView) view.findViewById(R.id.R52);
        R62 = (ImageView) view.findViewById(R.id.R62);
        R72 = (ImageView) view.findViewById(R.id.R72);
        R82 = (ImageView) view.findViewById(R.id.R82);

        //profile_id = (TextView)view.findViewById(R.id.profile_id);


        databaseReference.child("SeatInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seatlist.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SeatInfo seatInfo = snapshot1.getValue(SeatInfo.class);
                    seatlist.add(seatInfo);
                }

                for(int i=0;i<seatlist.size();i++){
                    SeatInfo temp = seatlist.get(i);
                    ImageView seat;
                    seat = (ImageView)view.findViewById(temp.num);
                    if(temp.statusnum == 0) {
                        seat.setImageResource(R.drawable.seat111);
                    }
                    else {
                        seat.setImageResource(R.drawable.seat2222);
                        seat.setTag(temp.user);
                    }
                }


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



        L11.setOnClickListener(new buttonlistener(getContext(), Password));
        L21.setOnClickListener(new buttonlistener(getContext(), Password));
        L31.setOnClickListener(new buttonlistener(getContext(), Password));
        L41.setOnClickListener(new buttonlistener(getContext(), Password));
        L51.setOnClickListener(new buttonlistener(getContext(), Password));
        L61.setOnClickListener(new buttonlistener(getContext(), Password));
        L71.setOnClickListener(new buttonlistener(getContext(), Password));
        L81.setOnClickListener(new buttonlistener(getContext(), Password));

        L12.setOnClickListener(new buttonlistener(getContext(), Password));
        L22.setOnClickListener(new buttonlistener(getContext(), Password));
        L32.setOnClickListener(new buttonlistener(getContext(), Password));
        L42.setOnClickListener(new buttonlistener(getContext(), Password));
        L52.setOnClickListener(new buttonlistener(getContext(), Password));
        L62.setOnClickListener(new buttonlistener(getContext(), Password));
        L72.setOnClickListener(new buttonlistener(getContext(), Password));
        L82.setOnClickListener(new buttonlistener(getContext(), Password));

        L13.setOnClickListener(new buttonlistener(getContext(), Password));
        L23.setOnClickListener(new buttonlistener(getContext(), Password));
        L33.setOnClickListener(new buttonlistener(getContext(), Password));
        L43.setOnClickListener(new buttonlistener(getContext(), Password));
        L53.setOnClickListener(new buttonlistener(getContext(), Password));
        L63.setOnClickListener(new buttonlistener(getContext(), Password));
        L73.setOnClickListener(new buttonlistener(getContext(), Password));

        L14.setOnClickListener(new buttonlistener(getContext(), Password));
        L24.setOnClickListener(new buttonlistener(getContext(), Password));
        L34.setOnClickListener(new buttonlistener(getContext(), Password));
        L44.setOnClickListener(new buttonlistener(getContext(), Password));
        L54.setOnClickListener(new buttonlistener(getContext(), Password));
        L64.setOnClickListener(new buttonlistener(getContext(), Password));
        L74.setOnClickListener(new buttonlistener(getContext(), Password));

        R11.setOnClickListener(new buttonlistener(getContext(), Password));
        R21.setOnClickListener(new buttonlistener(getContext(), Password));
        R31.setOnClickListener(new buttonlistener(getContext(), Password));
        R41.setOnClickListener(new buttonlistener(getContext(), Password));
        R51.setOnClickListener(new buttonlistener(getContext(), Password));
        R61.setOnClickListener(new buttonlistener(getContext(), Password));
        R71.setOnClickListener(new buttonlistener(getContext(), Password));
        R81.setOnClickListener(new buttonlistener(getContext(), Password));

        R12.setOnClickListener(new buttonlistener(getContext(), Password));
        R22.setOnClickListener(new buttonlistener(getContext(), Password));
        R32.setOnClickListener(new buttonlistener(getContext(), Password));
        R42.setOnClickListener(new buttonlistener(getContext(), Password));
        R52.setOnClickListener(new buttonlistener(getContext(), Password));
        R62.setOnClickListener(new buttonlistener(getContext(), Password));
        R72.setOnClickListener(new buttonlistener(getContext(), Password));
        R82.setOnClickListener(new buttonlistener(getContext(), Password));


        String s = R11.getTag().toString();


       /* // 오픈채팅
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
        });*/


        return view;
    }
}