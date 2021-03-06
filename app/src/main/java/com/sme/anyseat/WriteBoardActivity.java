package com.sme.anyseat;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class WriteBoardActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private DatabaseReference databaseReference4;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

    private ArrayList<String> remainlist = new ArrayList<>();

    private ArrayList<Integer> removelist = new ArrayList<>();

    //???????????? ?????? ??????
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);

        Intent intent = getIntent();
        final String postid = intent.getStringExtra("postid");
        final String goodcnt = intent.getStringExtra("goodcnt");
        final String commentcnt = intent.getStringExtra("commentcnt");

        writetitle = findViewById(R.id.write_title);
        writecontents = findViewById(R.id.write_contents);
        completebtn = findViewById(R.id.write_complete_btn);

        write_back = findViewById(R.id.write_back);
        write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = 0;
                list2.clear();
                list3.clear();

                if(postid == null){ //?????????
                    //????????? ?????????
                    if(list.size() >= 2){   //????????? ?????? ?????? ???
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com");

                        final ProgressDialog progressDialog = new ProgressDialog(WriteBoardActivity.this, R.style.MyDialogTheme);
                        progressDialog.setTitle("????????? ?????????");
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

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WriteBoardActivity.this, "????????? ??????!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    progressDialog.setMessage("????????? ???...");
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
                                        cnt++;

                                        if(cnt == list.size() - 1){ //??????????????? ????????? ?????? ??? ??????
                                            progressDialog.dismiss();
                                            postimage(postid, goodcnt, commentcnt);
                                        }
                                    }
                                    else{
                                        //??????
                                    }
                                }
                            });
                        }
                    }
                    else{   //????????? ?????? ?????? ???
                        String userid = mAuth.getCurrentUser().getUid();
                        database = FirebaseDatabase.getInstance();
                        if(postid == null){
                            databaseReference = database.getReference("Post").push();
                            String postid2 = databaseReference.getKey();

                            time = System.currentTimeMillis();

                            HashMap result = new HashMap<>();

                            result.put("title", writetitle.getText().toString());
                            result.put("contents", writecontents.getText().toString());
                            result.put("writetime", makeTimeStamp(time));
                            result.put("goodcnt", "0");
                            result.put("commentcnt", "0");
                            result.put("postid", postid2);
                            result.put("userid", userid);
                            result.put("imageexist", "0");
                            result.put("imageurilist", list2);
                            result.put("imagenamelist", list3);

                            databaseReference.setValue(result);
                            finish();
                        }
                        else{
                            databaseReference = database.getReference("Post").child(postid);

                            time = System.currentTimeMillis();

                            HashMap result = new HashMap<>();

                            result.put("title", writetitle.getText().toString());
                            result.put("contents", writecontents.getText().toString());
                            result.put("writetime", makeTimeStamp(time));
                            result.put("goodcnt", goodcnt);
                            result.put("commentcnt", commentcnt);
                            result.put("postid", postid);
                            result.put("userid", userid);
                            result.put("imageexist", "0");
                            result.put("imageurilist", list2);
                            result.put("imagenamelist", list3);

                            databaseReference.setValue(result);
                            finish();
                        }
                    }
                }
                else{   //??????
                    remainlist.clear();
                    removelist.clear();
                    databaseReference4 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
                    databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PostItem item = snapshot.getValue(PostItem.class);
                            for(int k=0;k<item.getImagenamelist().size();k++){
                                remainlist.add(item.getImagenamelist().get(k));
                            }
                            if(list.size() >= 2){   //????????? ?????? ?????? ???
                                storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com");

                                final ProgressDialog progressDialog = new ProgressDialog(WriteBoardActivity.this, R.style.MyDialogTheme);
                                progressDialog.setTitle("????????? ?????????");
                                progressDialog.show();

                                for(int i = 0; i < list.size() - 1; i++){
                                    imagename = System.currentTimeMillis();

                                    if(list.get(i).getImageuri().toString().contains("http")){  //?????? ?????????
                                        int index = 0;
                                        for(int j=0;j<item.getImageurilist().size();j++){
                                            if(list.get(i).getImageuri().toString().equals(item.getImageurilist().get(j))){
                                                index = j;
                                                removelist.add(index);
                                                break;
                                            }
                                        }

                                        list3.add(item.getImagenamelist().get(index));
                                        list2.add(item.getImageurilist().get(index));
                                        cnt++;

                                        if(cnt == list.size() - 1){ //??????????????? ????????? ?????? ??? ??????
                                            progressDialog.dismiss();
                                            postimage(postid, goodcnt, commentcnt);
                                        }
                                    }
                                    else{   //????????? ?????????
                                        list3.add(imagename+"");
                                        Uri file = list.get(i).getImageuri();
                                        //StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                                        final StorageReference riversRef = storageRef.child("images/"+imagename);
                                        UploadTask uploadTask = riversRef.putFile(file);
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(WriteBoardActivity.this, "????????? ??????!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                                progressDialog.setMessage("????????? ???...");
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
                                                    cnt++;

                                                    if(cnt == list.size() - 1){ //??????????????? ????????? ?????? ??? ??????
                                                        progressDialog.dismiss();
                                                        postimage(postid, goodcnt, commentcnt);
                                                    }
                                                }
                                                else{
                                                    //??????
                                                }
                                            }
                                        });
                                    }
                                }

                            }
                            else{   //????????? ?????? ?????? ???

                                for(int i=0;i<item.getImagenamelist().size();i++){
                                    imagedelete2(item.getImagenamelist().get(i));
                                }

                                String userid = mAuth.getCurrentUser().getUid();
                                database = FirebaseDatabase.getInstance();
                                if(postid == null){
                                    databaseReference = database.getReference("Post").push();
                                    String postid2 = databaseReference.getKey();

                                    time = System.currentTimeMillis();

                                    HashMap result = new HashMap<>();

                                    result.put("title", writetitle.getText().toString());
                                    result.put("contents", writecontents.getText().toString());
                                    result.put("writetime", makeTimeStamp(time));
                                    result.put("goodcnt", "0");
                                    result.put("commentcnt", "0");
                                    result.put("postid", postid2);
                                    result.put("userid", userid);
                                    result.put("imageexist", "0");
                                    result.put("imageurilist", list2);
                                    result.put("imagenamelist", list3);

                                    databaseReference.setValue(result);
                                    finish();
                                }
                                else{
                                    databaseReference = database.getReference("Post").child(postid);

                                    time = System.currentTimeMillis();

                                    HashMap result = new HashMap<>();

                                    result.put("title", writetitle.getText().toString());
                                    result.put("contents", writecontents.getText().toString());
                                    result.put("writetime", makeTimeStamp(time));
                                    result.put("goodcnt", goodcnt);
                                    result.put("commentcnt", commentcnt);
                                    result.put("postid", postid);
                                    result.put("userid", userid);
                                    result.put("imageexist", "0");
                                    result.put("imageurilist", list2);
                                    result.put("imagenamelist", list3);

                                    databaseReference.setValue(result);
                                    finish();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });




        recyclerView = findViewById(R.id.write_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);


        if(postid == null){
            list.add(new WriteBoardItem(null, Code.ViewType.SECOND));
            adapter = new WriteBoardAdapter(list, this);
            adapter.setOnPlusClickListener(new WriteBoardAdapter.OnPlusClickListener() {
                @Override
                public void onPlusClick(View v, int position) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????"), 0);
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
        else{
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PostItem item = snapshot.getValue(PostItem.class);

                    writetitle.setText(item.getTitle());
                    writecontents.setText(item.getContents());

                    for(int i=0;i<item.getImageurilist().size();i++){
                        Uri uri = Uri.parse(item.getImageurilist().get(i));
                        list.add(new WriteBoardItem(uri, Code.ViewType.FIRST));
                    }
                    list.add(new WriteBoardItem(null, Code.ViewType.SECOND));
                    adapter = new WriteBoardAdapter(list, getApplicationContext());
                    adapter.setOnPlusClickListener(new WriteBoardAdapter.OnPlusClickListener() {
                        @Override
                        public void onPlusClick(View v, int position) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????"), 0);
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
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //????????? ?????? ??? ??????
        if(requestCode == 0 && resultCode == RESULT_OK){
            if(data.getClipData() == null && data.getData() != null){   //????????? 1??? ??????
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
                        startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????"), 0);
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
            else{   //????????? 2????????? ??????
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
                        startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????"), 0);
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

    private void postimage(String postid, String goodcnt, String commentcnt){
        String userid = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        if(postid == null){
            databaseReference = database.getReference("Post").push();
            String postid2 = databaseReference.getKey();

            time = System.currentTimeMillis();

            HashMap result = new HashMap<>();

            result.put("title", writetitle.getText().toString());
            result.put("contents", writecontents.getText().toString());
            result.put("writetime", makeTimeStamp(time));
            result.put("goodcnt", "0");
            result.put("commentcnt", "0");
            result.put("postid", postid2);
            result.put("userid", userid);
            result.put("imageexist", "1");
            result.put("imageurilist", list2);
            result.put("imagenamelist", list3);

            databaseReference.setValue(result);
            finish();
        }
        else{
            databaseReference = database.getReference("Post").child(postid);

            time = System.currentTimeMillis();

            HashMap result = new HashMap<>();

            result.put("title", writetitle.getText().toString());
            result.put("contents", writecontents.getText().toString());
            result.put("writetime", makeTimeStamp(time));
            result.put("goodcnt", goodcnt);
            result.put("commentcnt", commentcnt);
            result.put("postid", postid);
            result.put("userid", userid);
            result.put("imageexist", "1");
            result.put("imageurilist", list2);
            result.put("imagenamelist", list3);

            databaseReference.setValue(result);
            finish();
        }

        for(int i=0;i<remainlist.size();i++){
            int check = -1;
            for(int j=0;j<removelist.size();j++){
                if(i == removelist.get(j)){
                    check = 1;
                    break;
                }
            }
            if(check == -1){
                imagedelete2(remainlist.get(i));
            }
        }

    }

    private void imagedelete2(String imagename){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com").child("images/"+imagename);
        storageRef.delete();
    }

    private void imagedelete(final String postid){
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostItem item = snapshot.getValue(PostItem.class);
                if(item.getImageexist().equals("1")){//???????????? ?????? ???
                    for(int i=0;i<item.getImagenamelist().size();i++){
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://smee-90a2d.appspot.com").child("images/"+item.getImagenamelist().get(i));
                        storageRef.delete();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}