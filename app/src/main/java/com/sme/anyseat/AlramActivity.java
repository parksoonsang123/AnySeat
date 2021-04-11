package com.sme.anyseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlramActivity extends AppCompatActivity {

    ImageView back;

    RecyclerView recyclerView;

    private ArrayList<AlramItem> list = new ArrayList<>();

    AlramAdapater adapater;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    DatabaseReference reference;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram);

        userId = mAuth.getCurrentUser().getUid();

        back = findViewById(R.id.alram_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference("Alram").child(userId);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            MainActivity2.main_btn.setBackgroundResource(R.drawable.alram4);
                        }
                        else{
                            MainActivity2.main_btn.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        });

        recyclerView = findViewById(R.id.alram_rcv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        reference = FirebaseDatabase.getInstance().getReference("Alram").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    AlramItem item = snapshot1.getValue(AlramItem.class);
                    list.add(item);
                }

                adapater = new AlramAdapater(list);
                recyclerView.setAdapter(adapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}