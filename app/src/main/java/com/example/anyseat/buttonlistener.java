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
    String Password;
    ArrayList<SeatInfo> seatlist = new ArrayList<>();
    final String[] tempuser = {new String()};
    DatabaseReference finalUserdata;

    buttonlistener(Context context, String Password){
        this.context = context;
        this.Password = Password;

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(Password);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserInfo.class);
                finalUserdata= FirebaseDatabase.getInstance().getReference().child("UserInfo").child(user.Password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        final ImageView imageView = (ImageView) view;
        final String username = imageView.getTag().toString();
        final int seatnum = imageView.getId();
        final String[] using = new String[1];
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
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SeatInfo");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    seatlist.clear();
                    SeatInfo seatInfo = null;
                    int c = -1;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        c++;
                        SeatInfo temp = snapshot1.getValue(SeatInfo.class);
                        if(temp.num == seatnum){
                            seatInfo = temp;
                            break;
                        }
                    }
                    final String s = Integer.toString(c);
                    if(seatInfo.user.equals(user.Name)){
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
                                databaseReference.child(s).child("status").setValue("off");
                                databaseReference.child(s).child("statusnum").setValue(0);
                                databaseReference.child(s).child("user").setValue("빈 자리");
                                imageView.setTag("빈 자리");
                                imageView.setImageResource(R.drawable.empty_seat);
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

    private void writeNewUser(View view, final int seatnum, String username) {
        String s = "new user";
        SeatInfo seatinfo = new SeatInfo(seatnum, 1, username);
        ImageView imageView = view.findViewById(view.getId());
        imageView.setTag("사용 중");
        imageView.setImageResource(R.drawable.using_seat);
        final int[] c = new int[1];
        String string = Integer.toString( c[0]);

        mDatabase.child("SeatInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = -1;
                seatlist.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    count = count + 1;
                    SeatInfo temp = snapshot1.getValue(SeatInfo.class);
                    if(temp.num == seatnum){
                        break;
                    }
                }
                c[0] = count;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("SeatInfo").child(string).setValue(seatinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
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

