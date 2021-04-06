package com.example.anyseat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class buttonlistener implements View.OnClickListener {

    Context context;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    UserInfo user;
    String uid;
    ArrayList<SeatInfo> seatlist = new ArrayList<>();
    final String[] tempuser = {new String()};
    DatabaseReference finalUserdata;

    buttonlistener(Context context){
        this.context = context;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(uid);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserInfo.class);
                finalUserdata= FirebaseDatabase.getInstance().getReference().child("UserInfo").child(user.uid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        final ImageView imageView = (ImageView) view;
        String seat = "";
        switch (imageView.getId()){
            case R.id.L11:
                seat = "L1-1";
                break;
            /*case R.id.L12:
                seat = "L1-2";
                break;*/
            case R.id.L13:
                seat = "L1-3";
                break;
            /*case R.id.L14:
                seat = "L1-4";
                break;*/

            /*case R.id.L21:
                seat = "L2-1";
                break;*/
            case R.id.L22:
                seat = "L2-2";
                break;
           /* case R.id.L23:
                seat = "L2-3";
                break;*/
            case R.id.L24:
                seat = "L2-4";
                break;

            case R.id.L31:
                seat = "L3-1";
                break;
            /*case R.id.L32:
                seat = "L3-2";
                break;*/
            case R.id.L33:
                seat = "L3-3";
                break;
            /*case R.id.L34:
                seat = "L3-4";
                break;*/

            /*case R.id.L41:
                seat = "L4-1";
                break;*/
            case R.id.L42:
                seat = "L4-2";
                break;
            /*case R.id.L43:
                seat = "L4-3";
                break;*/
            case R.id.L44:
                seat = "L4-4";
                break;

            case R.id.L51:
                seat = "L5-1";
                break;
            /*case R.id.L52:
                seat = "L5-2";
                break;*/
            case R.id.L53:
                seat = "L5-3";
                break;
            /*case R.id.L54:
                seat = "L5-4";
                break;*/

            /*case R.id.L61:
                seat = "L6-1";
                break;*/
            case R.id.L62:
                seat = "L6-2";
                break;
            /*case R.id.L63:
                seat = "L6-3";
                break;*/
            case R.id.L64:
                seat = "L6-4";
                break;

            case R.id.L71:
                seat = "L7-1";
                break;
            /*case R.id.L72:
                seat = "L7-2";
                break;*/
            case R.id.L73:
                seat = "L7-3";
                break;
            /*case R.id.L74:
                seat = "L7-4";
                break;*/

            /*case R.id.L81:
                seat = "L8-1";
                break;*/
            case R.id.L82:
                seat = "L8-2";
                break;

            case R.id.R11:seat = "R1-1";break;
            /*case R.id.R12:seat = "R1-2";break;*/
            /*case R.id.R21:seat = "R2-1";break;*/
            case R.id.R22:seat = "R2-2";break;
            case R.id.R31:seat = "R3-1";break;
            /*case R.id.R32:seat = "R3-2";break;*/
            /*case R.id.R41:seat = "R4-1";break;*/
            case R.id.R42:seat = "R4-2";break;
            case R.id.R51:seat = "R5-1";break;
            /*case R.id.R52:seat = "R5-2";break;*/
            /*case R.id.R61:seat = "R6-1";break;*/
            case R.id.R62:seat = "R6-2";break;
            case R.id.R71:seat = "R7-1";break;
            /*case R.id.R72:seat = "R7-2";break;*/
            /*case R.id.R81:seat = "R8-1";break;*/
            case R.id.R82:seat = "R8-2";break;

            default:
                break;
        }

        if(!seat.equals("")){
            final String username = imageView.getTag().toString();
            int seatnum = imageView.getId();
            final String[] using = new String[1];
            final DatabaseReference finalUserdata = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(user.uid);
            if(user.using.equals("false")) {
                if (username.equals("빈 자리")) {
                    writeNewUser(seat, view, seatnum, user.Name);
                    Toast.makeText(context, "선택하신 자리 이용을 시작합니다.", Toast.LENGTH_SHORT).show();
                    //finalUserdata.child("using").setValue("true");
                    finalUserdata.child("using").setValue(seat);
                } else {
                    Toast.makeText(context, "다른 학우가 사용 중인 자리입니다!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SeatInfo").child(seat);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                        SeatInfo seatInfo = snapshot.getValue(SeatInfo.class);
                        if(seatInfo.user.equals(user.Name)){
                            //Toast.makeText(context, "내가 사용중", Toast.LENGTH_SHORT).show();
                            /*AlertDialog.Builder exitbuilder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                            exitbuilder.setTitle("본인이 사용중인 자리입니다!");
                            exitbuilder.setMessage("사용을 종료하시겠습니까?");
                            exitbuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            exitbuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseReference.child("status").setValue("off");
                                    databaseReference.child("statusnum").setValue(0);
                                    databaseReference.child("user").setValue("빈 자리");
                                    Toast.makeText(context, "자리 사용을 종료합니다.", Toast.LENGTH_SHORT).show();
                                    finalUserdata.child("using").setValue("false");
                                }
                            });
                            exitbuilder.show();*/
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.MyDialogTheme);

                            builder.setTitle("본인이 사용중인 자리입니다!");
                            builder.setMessage("사용을 종료하시겠습니까?");
                            builder.setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("status").setValue("off");
                                            databaseReference.child("statusnum").setValue(0);
                                            databaseReference.child("user").setValue("빈 자리");
                                            Toast.makeText(context, "자리 사용을 종료합니다.", Toast.LENGTH_SHORT).show();
                                            finalUserdata.child("using").setValue("false");
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
                        else {
                            Toast.makeText(context, "이미 사용중인 자리가 있습니다!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else{
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.MyDialogTheme);


            builder.setMessage("거리두기 좌석 입니다.");


            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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


    }

    private void writeNewUser(String s, View view, int seatnum, String username) {
        String string = s;
        SeatInfo seatinfo = new SeatInfo(seatnum, 1, username);

        mDatabase.child("SeatInfo").child(s).setValue(seatinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                    }
                });

    }
}

