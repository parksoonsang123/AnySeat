package com.example.anyseat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private final List<PostItem2> mDataList;

    //클릭 구현
    public interface ClickListener{
        void onItemClicked(int position);
        void onButtonClicked(int position);
    }
    private ClickListener mListener;

    public void setOnClickListener(ClickListener listener){
        mListener = listener;
    }

    public NoticeAdapter(List<PostItem2> dataList){
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_item, parent, false);
        return new ViewHolder(view);
    }

    //클릭 처리
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem2 item = mDataList.get(position);
        holder.contents.setText(item.getContents());
        holder.writetime.setText(item.getWritetime());

        if(mListener != null){
            final int pos = position;

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView contents;
        TextView writetime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.notice_content);
            writetime = itemView.findViewById(R.id.notice_writetime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), NoticeDetailActivity.class);
                    intent.putExtra("postid", mDataList.get(pos).getPostid());
                    intent.putExtra("pos", pos);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
