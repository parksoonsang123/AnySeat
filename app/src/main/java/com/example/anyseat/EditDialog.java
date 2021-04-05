package com.example.anyseat;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDialog extends Dialog {

    private Context context;

    public EditText Pass;
    public EditText Pass_Conf;

    public TextView OK;
    public TextView NO;

    DatabaseReference reference;

    String Password;
    UserInfo userInfo;

    public EditDialog(@NonNull Context context, String Password) {
        super(context);
        this.context = context;
        this.Password = Password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.editdialog);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserInfo");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo = snapshot.child(Password).getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Pass = findViewById(R.id.edit_password);
        Pass_Conf = findViewById(R.id.edit_password_confirm);

        OK = findViewById(R.id.edit_ok_text);
        NO = findViewById(R.id.edit_no_text);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = Pass.getText().toString();
                String c = Pass_Conf.getText().toString();
                if(p.equals(c)){
                    userInfo.Password = p;
                    reference.child(Password).removeValue();
                    user.updatePassword(p);
                    reference.child(p).setValue(userInfo);
                    SaveSharedPreference.setUserName(((MainActivity)MainActivity.context), "", "", false);
                    dismiss();
                }
                else{
                    Toast.makeText(context, "입력하신 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
