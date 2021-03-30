package com.example.anyseat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class buttonlistener implements View.OnClickListener {

    Context context;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    UserInfo user;
    String Password;
    final String[] tempuser = {new String()};

    buttonlistener(Context context, String Password){
        this.context = context;
        this.Password = Password;

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(Password);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        final Button button = (Button)view;
        String seat = "";
        switch (button.getId()){
            case R.id.L11:
                seat = "L1-1";
                break;
            case R.id.L12:
                seat = "L1-2";
                break;
            case R.id.L21:
                seat = "L2-1";
                break;
            case R.id.L22:
                seat = "L2-2";
                break;
            case R.id.L31:
                seat = "L3-1";
                break;
            case R.id.L32:
                seat = "L3-2";
                break;
            case R.id.L41:
                seat = "L4-1";
                break;
            case R.id.L42:
                seat = "L4-2";
                break;
            case R.id.R1:
                seat = "R1";
                break;
            case R.id.R2:
                seat = "R2";
                break;
            case R.id.R3:
                seat = "R3";
                break;
            case R.id.R4:
                seat = "R4";
                break;
        }
        final String username = button.getText().toString();
        int seatnum = button.getId();
        final String[] using = new String[1];
        final DatabaseReference finalUserdata = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(user.Password);
        if(user.using.equals("false")) {
            if (username.equals("빈 자리")) {
                writeNewUser(view, seatnum, user.Name);
                Toast.makeText(context, "선택하신 자리 이용을 시작합니다.", Toast.LENGTH_SHORT).show();
                finalUserdata.child("using").setValue("true");
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
                        Toast.makeText(context, "내가 사용중", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder exitbuilder = new AlertDialog.Builder(context);
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
                                button.setText("빈 자리");
                                Toast.makeText(context, "자리 사용을 종료합니다.", Toast.LENGTH_SHORT).show();
                                finalUserdata.child("using").setValue("false");
                            }
                        });
                        exitbuilder.show();
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

    private void writeNewUser(View view, int seatnum, String username) {
        String s = "new user";
        SeatInfo seatinfo = new SeatInfo(seatnum, 1, username);
        Button b = view.findViewById(view.getId());
        b.setText("사용 중");

        mDatabase.child("SeatInfo").child(seatinfo.seatnum).setValue(seatinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(context, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(context, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

