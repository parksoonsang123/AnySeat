package com.example.anyseat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AlramAdapater extends RecyclerView.Adapter<AlramAdapater.ViewHolder> {

    DatabaseReference reference1;
    DatabaseReference reference2;
    DatabaseReference reference3;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    private List<AlramItem> mDataList;

    public AlramAdapater(List<AlramItem> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.alram_item1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(mDataList.get(position).getType().equals("1")){  //댓글
            holder.title.setText("새로운 댓글이 달렸습니다.");
            holder.imageView.setImageResource(R.drawable.alram1);
            holder.time.setText(mDataList.get(position).getWritetime());

            reference1 = FirebaseDatabase.getInstance().getReference("Reply").child(mDataList.get(position).getReplyid());
            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ReplyItem item = snapshot.getValue(ReplyItem.class);
                    holder.contents.setText(item.getContents());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }
        else if(mDataList.get(position).getType().equals("2")){ //공지사항
            holder.title.setText("새로운 공지사항이 있습니다.");
            holder.imageView.setImageResource(R.drawable.alram2);
            holder.time.setText(mDataList.get(position).getWritetime());

            reference2 = FirebaseDatabase.getInstance().getReference("Notice").child(mDataList.get(position).getPostid());
            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PostItem item = snapshot.getValue(PostItem.class);
                    holder.contents.setText(item.getTitle());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView contents;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = mAuth.getCurrentUser().getUid();

            imageView = itemView.findViewById(R.id.alram_image);
            title = itemView.findViewById(R.id.alram_title);
            contents = itemView.findViewById(R.id.alram_contents);
            time = itemView.findViewById(R.id.alram_time);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(mDataList.get(pos).getType().equals("1")){
                        Intent intent = new Intent(v.getContext(), FreeBoardDetailActivity.class);
                        intent.putExtra("id", mDataList.get(pos).getPostid());
                        intent.putExtra("pos", pos);
                        v.getContext().startActivity(intent);
                        reference3 = FirebaseDatabase.getInstance().getReference("Alram").child(userId).child(mDataList.get(pos).getAlramid());
                        reference3.removeValue();
                    }
                    else if(mDataList.get(pos).getType().equals("2")){
                        Intent intent = new Intent(v.getContext(), NoticeDetailActivity.class);
                        intent.putExtra("postid", mDataList.get(pos).getPostid());
                        intent.putExtra("pos", pos);
                        v.getContext().startActivity(intent);
                        reference3 = FirebaseDatabase.getInstance().getReference("Alram").child(userId).child(mDataList.get(pos).getAlramid());
                        reference3.removeValue();
                    }
                }
            });

        }
    }

}
