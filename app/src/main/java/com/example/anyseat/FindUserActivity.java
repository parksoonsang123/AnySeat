package com.example.anyseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<UserInfo> Users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        reference.child("UserInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserInfo userInfo = snapshot1.getValue(UserInfo.class);
                    Users.add(new UserInfo(userInfo.Name,userInfo.Email,userInfo.Password,userInfo.Grade));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final EditText email_find_nameinput = (EditText)findViewById(R.id.email_find_nameinput);
        final EditText password_find_nameinput = (EditText)findViewById(R.id.password_find_nameinput);
        final EditText password_find_emailinput = (EditText)findViewById(R.id.password_find_emailinput);

        Button emailfindbutton = (Button)findViewById(R.id.email_find_button);
        emailfindbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = email_find_nameinput.getText().toString();
                String Email = null;
                int flag = 0;
                for(int i=0;i<Users.size();i++){
                    if(Name.equals(Users.get(i).Name)) {
                        flag = 1;
                        Email = Users.get(i).Email;
                        break;
                    }
                }
                if(flag == 1){
                    final AlertDialog.Builder EmailInfo = new AlertDialog.Builder(FindUserActivity.this);
                    EmailInfo.setTitle("이메일 찾기 결과");
                    EmailInfo.setMessage(Name+"님의 이메일은 "+Email+"입니다!\n 로그인 화면으로 돌아가려면 OK버튼을 선택해주세요.");
                    EmailInfo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    EmailInfo.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    EmailInfo.show();
                }
                else{
                    Toast.makeText(FindUserActivity.this, "가입되어 있지 않은 사용자 정보입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button passwordfindbutton = (Button)findViewById(R.id.password_find_button);
        passwordfindbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = password_find_nameinput.getText().toString();
                String Email = password_find_emailinput.getText().toString();
                String Password = null;

                int flag = 0;
                for(int i=0;i<Users.size();i++){
                    if(Name.equals(Users.get(i).Name) && Email.equals(Users.get(i).Email)) {
                        flag = 1;
                        Password = Users.get(i).Password;
                        break;
                    }
                }

                if(flag == 1){
                    final AlertDialog.Builder PasswordInfo = new AlertDialog.Builder(FindUserActivity.this);
                    PasswordInfo.setTitle("비밀번호 찾기 결과");
                    PasswordInfo.setMessage(Name+"님의 계정 "+Email+"의 비밀번호는 "+Password+"입니다!\n 로그인 화면으로 돌아가려면 OK버튼을 선택해주세요.");
                    PasswordInfo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    PasswordInfo.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    PasswordInfo.show();
                }
                else{
                    Toast.makeText(FindUserActivity.this, "가입되어 있지 않은 사용자 정보입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView backbutton = (ImageView)findViewById(R.id.backbutton2);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}