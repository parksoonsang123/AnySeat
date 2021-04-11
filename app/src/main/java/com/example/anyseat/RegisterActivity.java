package com.example.anyseat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "MainAcivity";
    private FirebaseAuth mAuth;
    private TextView useremail;
    private TextView userpassword;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Context context = RegisterActivity.this;

    String name, id, password, email;
    String uid;
    int grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        final EditText nameText = (EditText)findViewById(R.id.nameText);
        userpassword = (EditText)findViewById(R.id.user_password_text);
        useremail = (EditText)findViewById(R.id.emailText);
        RadioGroup gradeRadio = (RadioGroup)findViewById(R.id.gradeGroup);
        gradeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.firstGrade:
                        grade = 1;
                        break;
                    case R.id.secondGrade:
                        grade = 2;
                        break;
                    case R.id.thirdGrade:
                        grade = 3;
                        break;
                    case R.id.forthGrade:
                        grade = 4;
                        break;
                    case R.id.fiveGrade:
                        grade = 5;
                        break;
                }
            }
        });

        if(grade==0) grade = 1;

        class signListener implements View.OnClickListener{

            @Override
            public void onClick(View view) {
                name = String.valueOf(nameText.getText());
                email = useremail.getText().toString();
                password = userpassword.getText().toString();
                signUp(name, email, password, grade);

            }
        }

        Button signButton = (Button)findViewById(R.id.signButton);
        signButton.setOnClickListener(new signListener());

        ImageView backbutton = (ImageView)findViewById(R.id.backbutton1);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void signUp(final String Name, final String Email, final String Password, final int Grade){
        String email = useremail.getText().toString();
        email = email.trim();
        final String password = userpassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = mAuth.getCurrentUser().getUid();
                            writeNewUser(Name, Email, Password, Grade, uid);
                            //ui
                        } else {
                            if(password.length() <= 5){
                                Toast.makeText(RegisterActivity.this, "비밀번호는 6자리 이상이어야 입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //ui
                        }

                        // ...
                    }
                });
    }

    private void writeNewUser(String Name, String Email, String Password, int Grade, String uid) {
        UserInfo userInfo = new UserInfo(Name, Email, Password, Grade, uid);

        mDatabase.child("UserInfo").child(uid).setValue(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "회원가입을 완료했습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(context, "회원가입을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}