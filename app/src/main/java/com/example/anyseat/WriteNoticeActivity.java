package com.example.anyseat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteNoticeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private FirebaseStorage storage;

    EditText writetitle;
    EditText writecontents;
    Button completebtn;
    Button imagebtn;
    ImageView write_back;

    long time;
    long imagename;

    RecyclerView recyclerView;
    WriteBoardAdapter adapter;
    private ArrayList<WriteBoardItem> list = new ArrayList<>();
    //imageurilist
    private ArrayList<String> list2 = new ArrayList<>();
    //imagenamelist
    private ArrayList<String> list3 = new ArrayList<>();

    //업로드된 사진 갯수
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);

        writetitle = findViewById(R.id.notice_title);
        writecontents = findViewById(R.id.notice_contents);
        completebtn = findViewById(R.id.notice_complete_btn);
        write_back = findViewById(R.id.notice_back);
        write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list2.clear();
                cnt = 0;
                //이미지 업로드
                if(list.size() >= 2){   //이미지 파일 있을 때
                    storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://anyseat-4e964.appspot.com/");

                    final ProgressDialog progressDialog = new ProgressDialog(WriteNoticeActivity.this);
                    progressDialog.setTitle("이미지 업로드");
                    progressDialog.show();

                    for(int i = 0; i < list.size() - 1; i++){
                        imagename = System.currentTimeMillis();
                        list3.add(imagename+"");
                        Uri file = list.get(i).getImageuri();
                        //StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                        final StorageReference riversRef = storageRef.child("images/"+imagename);
                        UploadTask uploadTask = riversRef.putFile(file);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                cnt++;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(WriteNoticeActivity.this, "업로드 실패!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                progressDialog.setMessage("업로드 중...");
                            }
                        });

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if(!task.isSuccessful()){
                                    throw task.getException();
                                }

                                return riversRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){

                                    Uri downloadUri = task.getResult();
                                    list2.add(downloadUri.toString());

                                    if(cnt == list.size() - 1){ //이미지파일 업로드 완료 후 동작
                                        progressDialog.dismiss();
                                        postimage();
                                    }
                                }
                                else{
                                    //실패
                                }
                            }
                        });
                    }
                }
                else{   //이미지 파일 없을 때

                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("Notice").push();
                    String postid2 = databaseReference.getKey();

                    time = System.currentTimeMillis();

                    HashMap result = new HashMap<>();

                    result.put("title", writetitle.getText().toString());
                    result.put("contents", writecontents.getText().toString());
                    result.put("writetime", makeTimeStamp(time));
                    result.put("postid", postid2);
                    result.put("imageexist", "0");
                    result.put("imageurilist", list2);
                    result.put("imagenamelist", list3);

                    databaseReference.setValue(result);
                    finish();


                }
            }
        });




        recyclerView = findViewById(R.id.notice_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        list.add(new WriteBoardItem(null, Code.ViewType.SECOND));
        adapter = new WriteBoardAdapter(list, this);
        adapter.setOnPlusClickListener(new WriteBoardAdapter.OnPlusClickListener() {
            @Override
            public void onPlusClick(View v, int position) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요"), 0);
            }
        });
        adapter.setOnOtherClickListener(new WriteBoardAdapter.OnOtherClickListener() {
            @Override
            public void onOtherClick(View v, int position) {
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //이미지 선택 시 실행
        if(requestCode == 0 && resultCode == RESULT_OK){
            if(data.getClipData() == null && data.getData() != null){   //이미지 1개 선택
                int lastindex = list.size() - 1;
                if(lastindex != -1){
                    list.remove(lastindex);
                }
                list.add(new WriteBoardItem(data.getData(), Code.ViewType.FIRST));
                list.add(new WriteBoardItem(null, Code.ViewType.SECOND));
                adapter = new WriteBoardAdapter(list, this);
                adapter.setOnPlusClickListener(new WriteBoardAdapter.OnPlusClickListener() {
                    @Override
                    public void onPlusClick(View v, int position) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요"), 0);
                    }
                });
                adapter.setOnOtherClickListener(new WriteBoardAdapter.OnOtherClickListener() {
                    @Override
                    public void onOtherClick(View v, int position) {
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
                recyclerView.setAdapter(adapter);

            }
            else{   //이미지 2개이상 선택
                int lastindex = list.size() - 1;
                if(lastindex != -1){
                    list.remove(lastindex);
                }
                ClipData clipData = data.getClipData();
                for(int i = 0;i<clipData.getItemCount();i++){
                    list.add(new WriteBoardItem(clipData.getItemAt(i).getUri(), Code.ViewType.FIRST));
                }
                list.add(new WriteBoardItem(null, Code.ViewType.SECOND));
                adapter = new WriteBoardAdapter(list, this);
                adapter.setOnPlusClickListener(new WriteBoardAdapter.OnPlusClickListener() {
                    @Override
                    public void onPlusClick(View v, int position) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요"), 0);
                    }
                });
                adapter.setOnOtherClickListener(new WriteBoardAdapter.OnOtherClickListener() {
                    @Override
                    public void onOtherClick(View v, int position) {
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

        }
    }

    private String makeTimeStamp(long in){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        return format.format(in);
    }

    private void postimage(){
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("Notice").push();
        String postid2 = databaseReference.getKey();

        time = System.currentTimeMillis();

        HashMap result = new HashMap<>();

        result.put("title", writetitle.getText().toString());
        result.put("contents", writecontents.getText().toString());
        result.put("writetime", makeTimeStamp(time));
        result.put("postid", postid2);
        result.put("imageexist", "1");
        result.put("imageurilist", list2);
        result.put("imagenamelist", list3);

        databaseReference.setValue(result);
        finish();

    }



}