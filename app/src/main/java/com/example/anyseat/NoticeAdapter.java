package com.example.anyseat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private final List<Notice_Item> mDataList;

    //클릭 구현
    public interface ClickListener{
        void onItemClicked(int position);
        void onButtonClicked(int position);
    }
    private ClickListener mListener;

    public void setOnClickListener(ClickListener listener){
        mListener = listener;
    }

    public NoticeAdapter(List<Notice_Item> dataList){
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
        Notice_Item item = mDataList.get(position);
        holder.contents.setText(item.getContents());

        if(mListener != null){
            final int pos = position;

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView contents;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.contents_text);
        }
    }
}
