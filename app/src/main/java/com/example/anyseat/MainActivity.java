package com.example.anyseat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView useremail;
    private TextView userpassword;
    private CheckBox KeepLoginCheck;
    public static Context context;
    public int var;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://anyseat-4e964-default-rtdb.firebaseio.com/");
    DatabaseReference userRef = mDatabase.child("user");

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        context = this;

        FirebaseUser currentUser = mAuth.getCurrentUser();

        // 로그인 상태 유지
        if(SaveSharedPreference.getPrefUserKeepLogin(MainActivity.this)) {
            if (SaveSharedPreference.getPrefUserEmail(MainActivity.this).length() != 0 && SaveSharedPreference.getPrefUserPassword(MainActivity.this).length() != 0) {
                String Email = SaveSharedPreference.getPrefUserEmail(MainActivity.this);
                String Password = SaveSharedPreference.getPrefUserPassword(MainActivity.this);
                signIn(Email, Password);
            }
        }

        TextView register = (TextView)findViewById(R.id.register);
        Button loginbutton = (Button)findViewById(R.id.loginButton);
        useremail = (TextView) findViewById(R.id.user_email_text);
        userpassword = (TextView) findViewById(R.id.user_password_text);

        // 회원가입
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder graduateCheckDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                graduateCheckDialogBuilder.setTitle("재학 여부 확인");
                graduateCheckDialogBuilder.setMessage("졸업생이신 경우 개발자에게 연락해주시면 졸업생 전용 아이디를 발급해드리겠습니다! 재학생이신 경우 OK 를 누르고 회원가입을 진행해 주세요!");
                graduateCheckDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
                graduateCheckDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog graduateCheckDialog = graduateCheckDialogBuilder.create();
                graduateCheckDialog.show();

            }
        });


        // 로그인
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useremail.getText().toString();
                String password = userpassword.getText().toString();
                signIn(email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signIn(final String email, final String password){
        //firebase 로그인
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "로그인 성공",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            KeepLoginCheck = (CheckBox)findViewById(R.id.KeepLoginCheck);
                            if(KeepLoginCheck.isChecked()){
                                SaveSharedPreference.setUserName(MainActivity.this, email, password, true);
                            }
                            String uid = mAuth.getCurrentUser().getUid();
                            reference = FirebaseDatabase.getInstance().getReference("UserList").child(uid);
                            reference.setValue(uid);
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("Password", password);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "로그인 실패",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }

                        // ...
                    }
                });
    }

}
