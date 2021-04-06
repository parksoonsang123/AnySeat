package com.example.anyseat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    String uid;


    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public SeatFragment(String uid) {
        this.uid = uid;
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

                int statusnum;

                //L1-1
                statusnum = snapshot.child("L1-1").child("statusnum").getValue(Integer.class);
                L11.setTag( snapshot.child("L1-1").child("user").getValue(String.class));
                if(statusnum == 1) L11.setImageResource(R.drawable.seat2222);
                else L11.setImageResource(R.drawable.seat1111);
                //L2-1
                /*statusnum = snapshot.child("L2-1").child("statusnum").getValue(Integer.class);
                L21.setTag( snapshot.child("L2-1").child("user").getValue(String.class));
                if(statusnum == 1) L21.setImageResource(R.drawable.seat2222);
                else L21.setImageResource(R.drawable.seat1111);*/
                //L3-1
                statusnum = snapshot.child("L3-1").child("statusnum").getValue(Integer.class);
                L31.setTag( snapshot.child("L3-1").child("user").getValue(String.class));
                if(statusnum == 1) L31.setImageResource(R.drawable.seat2222);
                else L31.setImageResource(R.drawable.seat1111);
                //L4-1
                /*statusnum = snapshot.child("L4-1").child("statusnum").getValue(Integer.class);
                L41.setTag( snapshot.child("L4-1").child("user").getValue(String.class));
                if(statusnum == 1) L41.setImageResource(R.drawable.seat2222);
                else L41.setImageResource(R.drawable.seat1111);*/
                //L5-1
                statusnum = snapshot.child("L5-1").child("statusnum").getValue(Integer.class);
                L51.setTag( snapshot.child("L5-1").child("user").getValue(String.class));
                if(statusnum == 1) L51.setImageResource(R.drawable.seat2222);
                else L51.setImageResource(R.drawable.seat1111);
                //L6-1
                /*statusnum = snapshot.child("L6-1").child("statusnum").getValue(Integer.class);
                L61.setTag( snapshot.child("L6-1").child("user").getValue(String.class));
                if(statusnum == 1) L61.setImageResource(R.drawable.seat2222);
                else L61.setImageResource(R.drawable.seat1111);*/
                //L7-1
                statusnum = snapshot.child("L7-1").child("statusnum").getValue(Integer.class);
                L71.setTag( snapshot.child("L7-1").child("user").getValue(String.class));
                if(statusnum == 1) L71.setImageResource(R.drawable.seat2222);
                else L71.setImageResource(R.drawable.seat1111);
                //L8-1
                /*statusnum = snapshot.child("L8-1").child("statusnum").getValue(Integer.class);
                L81.setTag( snapshot.child("L8-1").child("user").getValue(String.class));
                if(statusnum == 1) L81.setImageResource(R.drawable.seat2222);
                else L81.setImageResource(R.drawable.seat1111);*/

                //L1-2
                /*statusnum = snapshot.child("L1-2").child("statusnum").getValue(Integer.class);
                L12.setTag( snapshot.child("L1-2").child("user").getValue(String.class));
                if(statusnum == 1) L12.setImageResource(R.drawable.seat2222);
                else L12.setImageResource(R.drawable.seat1111);*/
                //L2-2
                statusnum = snapshot.child("L2-2").child("statusnum").getValue(Integer.class);
                L22.setTag( snapshot.child("L2-2").child("user").getValue(String.class));
                if(statusnum == 1) L22.setImageResource(R.drawable.seat2222);
                else L22.setImageResource(R.drawable.seat1111);
                //L3-2
                /*statusnum = snapshot.child("L3-2").child("statusnum").getValue(Integer.class);
                L32.setTag( snapshot.child("L3-2").child("user").getValue(String.class));
                if(statusnum == 1) L32.setImageResource(R.drawable.seat2222);
                else L32.setImageResource(R.drawable.seat1111);*/
                //L4-2
                statusnum = snapshot.child("L4-2").child("statusnum").getValue(Integer.class);
                L42.setTag( snapshot.child("L4-2").child("user").getValue(String.class));
                if(statusnum == 1) L42.setImageResource(R.drawable.seat2222);
                else L42.setImageResource(R.drawable.seat1111);
                //L5-2
                /*statusnum = snapshot.child("L5-2").child("statusnum").getValue(Integer.class);
                L52.setTag( snapshot.child("L5-2").child("user").getValue(String.class));
                if(statusnum == 1) L52.setImageResource(R.drawable.seat2222);
                else L52.setImageResource(R.drawable.seat1111);*/
                //L6-2
                statusnum = snapshot.child("L6-2").child("statusnum").getValue(Integer.class);
                L62.setTag( snapshot.child("L6-2").child("user").getValue(String.class));
                if(statusnum == 1) L62.setImageResource(R.drawable.seat2222);
                else L62.setImageResource(R.drawable.seat1111);
                //L7-2
                /*statusnum = snapshot.child("L7-2").child("statusnum").getValue(Integer.class);
                L72.setTag( snapshot.child("L7-2").child("user").getValue(String.class));
                if(statusnum == 1) L72.setImageResource(R.drawable.seat2222);
                else L72.setImageResource(R.drawable.seat1111);*/
                //L8-2
                statusnum = snapshot.child("L8-2").child("statusnum").getValue(Integer.class);
                L82.setTag( snapshot.child("L8-2").child("user").getValue(String.class));
                if(statusnum == 1) L82.setImageResource(R.drawable.seat2222);
                else L82.setImageResource(R.drawable.seat1111);

                //L1-3
                statusnum = snapshot.child("L1-3").child("statusnum").getValue(Integer.class);
                L13.setTag( snapshot.child("L1-3").child("user").getValue(String.class));
                if(statusnum == 1) L13.setImageResource(R.drawable.seat2222);
                else L13.setImageResource(R.drawable.seat1111);
                //L2-3
                /*statusnum = snapshot.child("L2-3").child("statusnum").getValue(Integer.class);
                L23.setTag( snapshot.child("L2-3").child("user").getValue(String.class));
                if(statusnum == 1) L23.setImageResource(R.drawable.seat2222);
                else L23.setImageResource(R.drawable.seat1111);*/
                //L3-3
                statusnum = snapshot.child("L3-3").child("statusnum").getValue(Integer.class);
                L33.setTag( snapshot.child("L3-3").child("user").getValue(String.class));
                if(statusnum == 1) L33.setImageResource(R.drawable.seat2222);
                else L33.setImageResource(R.drawable.seat1111);
                //L4-3
                /*statusnum = snapshot.child("L4-3").child("statusnum").getValue(Integer.class);
                L43.setTag( snapshot.child("L4-3").child("user").getValue(String.class));
                if(statusnum == 1) L43.setImageResource(R.drawable.seat2222);
                else L43.setImageResource(R.drawable.seat1111);*/
                //L5-3
                statusnum = snapshot.child("L5-3").child("statusnum").getValue(Integer.class);
                L53.setTag( snapshot.child("L5-3").child("user").getValue(String.class));
                if(statusnum == 1) L53.setImageResource(R.drawable.seat2222);
                else L53.setImageResource(R.drawable.seat1111);
                //L6-3
                /*statusnum = snapshot.child("L6-3").child("statusnum").getValue(Integer.class);
                L63.setTag( snapshot.child("L6-3").child("user").getValue(String.class));
                if(statusnum == 1) L63.setImageResource(R.drawable.seat2222);
                else L63.setImageResource(R.drawable.seat1111);*/
                //L7-3
                statusnum = snapshot.child("L7-3").child("statusnum").getValue(Integer.class);
                L73.setTag( snapshot.child("L7-3").child("user").getValue(String.class));
                if(statusnum == 1) L73.setImageResource(R.drawable.seat2222);
                else L73.setImageResource(R.drawable.seat1111);

                //L1-4
                /*statusnum = snapshot.child("L1-4").child("statusnum").getValue(Integer.class);
                L14.setTag( snapshot.child("L1-4").child("user").getValue(String.class));
                if(statusnum == 1) L14.setImageResource(R.drawable.seat2222);
                else L14.setImageResource(R.drawable.seat1111);*/
                //L2-4
                statusnum = snapshot.child("L2-4").child("statusnum").getValue(Integer.class);
                L24.setTag( snapshot.child("L2-4").child("user").getValue(String.class));
                if(statusnum == 1) L24.setImageResource(R.drawable.seat2222);
                else L24.setImageResource(R.drawable.seat1111);
                //L3-4
                /*statusnum = snapshot.child("L3-4").child("statusnum").getValue(Integer.class);
                L34.setTag( snapshot.child("L3-4").child("user").getValue(String.class));
                if(statusnum == 1) L34.setImageResource(R.drawable.seat2222);
                else L34.setImageResource(R.drawable.seat1111);*/
                //L4-4
                statusnum = snapshot.child("L4-4").child("statusnum").getValue(Integer.class);
                L44.setTag( snapshot.child("L4-4").child("user").getValue(String.class));
                if(statusnum == 1) L44.setImageResource(R.drawable.seat2222);
                else L44.setImageResource(R.drawable.seat1111);
                //L5-4
                /*statusnum = snapshot.child("L5-4").child("statusnum").getValue(Integer.class);
                L54.setTag( snapshot.child("L5-4").child("user").getValue(String.class));
                if(statusnum == 1) L54.setImageResource(R.drawable.seat2222);
                else L54.setImageResource(R.drawable.seat1111);*/
                //L6-4
                statusnum = snapshot.child("L6-4").child("statusnum").getValue(Integer.class);
                L64.setTag( snapshot.child("L6-4").child("user").getValue(String.class));
                if(statusnum == 1) L64.setImageResource(R.drawable.seat2222);
                else L64.setImageResource(R.drawable.seat1111);
                //L7-4
                /*statusnum = snapshot.child("L7-4").child("statusnum").getValue(Integer.class);
                L74.setTag( snapshot.child("L7-4").child("user").getValue(String.class));
                if(statusnum == 1) L74.setImageResource(R.drawable.seat2222);
                else L74.setImageResource(R.drawable.seat1111);*/

                //R1-1
                statusnum = snapshot.child("R1-1").child("statusnum").getValue(Integer.class);
                R11.setTag( snapshot.child("R1-1").child("user").getValue(String.class));
                if(statusnum == 1) R11.setImageResource(R.drawable.seat2222);
                else R11.setImageResource(R.drawable.seat1111);
                //R2-1
                /*statusnum = snapshot.child("R2-1").child("statusnum").getValue(Integer.class);
                R21.setTag( snapshot.child("R2-1").child("user").getValue(String.class));
                if(statusnum == 1) R21.setImageResource(R.drawable.seat2222);
                else R21.setImageResource(R.drawable.seat1111);*/
                //R3-1
                statusnum = snapshot.child("R3-1").child("statusnum").getValue(Integer.class);
                R31.setTag( snapshot.child("R3-1").child("user").getValue(String.class));
                if(statusnum == 1) R31.setImageResource(R.drawable.seat2222);
                else R31.setImageResource(R.drawable.seat1111);
                //R4-1
                /*statusnum = snapshot.child("R4-1").child("statusnum").getValue(Integer.class);
                R41.setTag( snapshot.child("R4-1").child("user").getValue(String.class));
                if(statusnum == 1) R41.setImageResource(R.drawable.seat2222);
                else R41.setImageResource(R.drawable.seat1111);*/
                //R5-1
                statusnum = snapshot.child("R5-1").child("statusnum").getValue(Integer.class);
                R51.setTag( snapshot.child("R5-1").child("user").getValue(String.class));
                if(statusnum == 1) R51.setImageResource(R.drawable.seat2222);
                else R51.setImageResource(R.drawable.seat1111);
                //R6-1
                /*statusnum = snapshot.child("R6-1").child("statusnum").getValue(Integer.class);
                R61.setTag( snapshot.child("R6-1").child("user").getValue(String.class));
                if(statusnum == 1) R61.setImageResource(R.drawable.seat2222);
                else R61.setImageResource(R.drawable.seat1111);*/
                //R7-1
                statusnum = snapshot.child("R7-1").child("statusnum").getValue(Integer.class);
                R71.setTag( snapshot.child("R7-1").child("user").getValue(String.class));
                if(statusnum == 1) R71.setImageResource(R.drawable.seat2222);
                else R71.setImageResource(R.drawable.seat1111);
                //R8-1
                /*statusnum = snapshot.child("R8-1").child("statusnum").getValue(Integer.class);
                R81.setTag( snapshot.child("R8-1").child("user").getValue(String.class));
                if(statusnum == 1) R81.setImageResource(R.drawable.seat2222);
                else R81.setImageResource(R.drawable.seat1111);*/

                //R1-2
                /*statusnum = snapshot.child("R1-2").child("statusnum").getValue(Integer.class);
                R12.setTag( snapshot.child("R1-2").child("user").getValue(String.class));
                if(statusnum == 1) R12.setImageResource(R.drawable.seat2222);
                else R12.setImageResource(R.drawable.seat1111);*/
                //R2-2
                statusnum = snapshot.child("R2-2").child("statusnum").getValue(Integer.class);
                R22.setTag( snapshot.child("R2-2").child("user").getValue(String.class));
                if(statusnum == 1) R22.setImageResource(R.drawable.seat2222);
                else R22.setImageResource(R.drawable.seat1111);
                //R3-2
                /*statusnum = snapshot.child("R3-2").child("statusnum").getValue(Integer.class);
                R32.setTag( snapshot.child("R3-2").child("user").getValue(String.class));
                if(statusnum == 1) R32.setImageResource(R.drawable.seat2222);
                else R32.setImageResource(R.drawable.seat1111);*/
                //R4-2
                statusnum = snapshot.child("R4-2").child("statusnum").getValue(Integer.class);
                R42.setTag( snapshot.child("R4-2").child("user").getValue(String.class));
                if(statusnum == 1) R42.setImageResource(R.drawable.seat2222);
                else R42.setImageResource(R.drawable.seat1111);
                //R5-2
                /*statusnum = snapshot.child("R5-2").child("statusnum").getValue(Integer.class);
                R52.setTag( snapshot.child("R5-2").child("user").getValue(String.class));
                if(statusnum == 1) R52.setImageResource(R.drawable.seat2222);
                else R52.setImageResource(R.drawable.seat1111);*/
                //R6-2
                statusnum = snapshot.child("R6-2").child("statusnum").getValue(Integer.class);
                R62.setTag( snapshot.child("R6-2").child("user").getValue(String.class));
                if(statusnum == 1) R62.setImageResource(R.drawable.seat2222);
                else R62.setImageResource(R.drawable.seat1111);
                //R7-2
                /*statusnum = snapshot.child("R7-2").child("statusnum").getValue(Integer.class);
                R72.setTag( snapshot.child("R7-2").child("user").getValue(String.class));
                if(statusnum == 1) R72.setImageResource(R.drawable.seat2222);
                else R72.setImageResource(R.drawable.seat1111);*/
                //R8-2
                statusnum = snapshot.child("R8-2").child("statusnum").getValue(Integer.class);
                R82.setTag( snapshot.child("R8-2").child("user").getValue(String.class));
                if(statusnum == 1) R82.setImageResource(R.drawable.seat2222);
                else R82.setImageResource(R.drawable.seat1111);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        databaseReference.child("UserInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(uid).getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        L11.setOnClickListener(new buttonlistener(getContext()));
        L21.setOnClickListener(new buttonlistener(getContext()));
        L31.setOnClickListener(new buttonlistener(getContext()));
        L41.setOnClickListener(new buttonlistener(getContext()));
        L51.setOnClickListener(new buttonlistener(getContext()));
        L61.setOnClickListener(new buttonlistener(getContext()));
        L71.setOnClickListener(new buttonlistener(getContext()));
        L81.setOnClickListener(new buttonlistener(getContext()));

        L12.setOnClickListener(new buttonlistener(getContext()));
        L22.setOnClickListener(new buttonlistener(getContext()));
        L32.setOnClickListener(new buttonlistener(getContext()));
        L42.setOnClickListener(new buttonlistener(getContext()));
        L52.setOnClickListener(new buttonlistener(getContext()));
        L62.setOnClickListener(new buttonlistener(getContext()));
        L72.setOnClickListener(new buttonlistener(getContext()));
        L82.setOnClickListener(new buttonlistener(getContext()));

        L13.setOnClickListener(new buttonlistener(getContext()));
        L23.setOnClickListener(new buttonlistener(getContext()));
        L33.setOnClickListener(new buttonlistener(getContext()));
        L43.setOnClickListener(new buttonlistener(getContext()));
        L53.setOnClickListener(new buttonlistener(getContext()));
        L63.setOnClickListener(new buttonlistener(getContext()));
        L73.setOnClickListener(new buttonlistener(getContext()));

        L14.setOnClickListener(new buttonlistener(getContext()));
        L24.setOnClickListener(new buttonlistener(getContext()));
        L34.setOnClickListener(new buttonlistener(getContext()));
        L44.setOnClickListener(new buttonlistener(getContext()));
        L54.setOnClickListener(new buttonlistener(getContext()));
        L64.setOnClickListener(new buttonlistener(getContext()));
        L74.setOnClickListener(new buttonlistener(getContext()));

        R11.setOnClickListener(new buttonlistener(getContext()));
        R21.setOnClickListener(new buttonlistener(getContext()));
        R31.setOnClickListener(new buttonlistener(getContext()));
        R41.setOnClickListener(new buttonlistener(getContext()));
        R51.setOnClickListener(new buttonlistener(getContext()));
        R61.setOnClickListener(new buttonlistener(getContext()));
        R71.setOnClickListener(new buttonlistener(getContext()));
        R81.setOnClickListener(new buttonlistener(getContext()));

        R12.setOnClickListener(new buttonlistener(getContext()));
        R22.setOnClickListener(new buttonlistener(getContext()));
        R32.setOnClickListener(new buttonlistener(getContext()));
        R42.setOnClickListener(new buttonlistener(getContext()));
        R52.setOnClickListener(new buttonlistener(getContext()));
        R62.setOnClickListener(new buttonlistener(getContext()));
        R72.setOnClickListener(new buttonlistener(getContext()));
        R82.setOnClickListener(new buttonlistener(getContext()));


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