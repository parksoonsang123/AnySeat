package com.example.anyseat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FreeBoardAdapter extends RecyclerView.Adapter<FreeBoardAdapter.ViewHolder> {


    private final List<PostItem> mDataList;

    public FreeBoardAdapter(List<PostItem> mDataList, String cui) {
        this.mDataList = mDataList;
        this.cui = cui;
    }

    private String cui;

    public interface OnDeleteClickListner2 {
        void onDeleteClick2(View v, int position, Button btn);
    }
    private FreeBoardAdapter.OnDeleteClickListner2 mListner = null;
    public void setOnDeleteClickListener2(FreeBoardAdapter.OnDeleteClickListner2 listener){
        this.mListner = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.freeboard_item1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem item1 = mDataList.get(position);
        holder.freeboard_title1.setText(item1.getTitle());
        holder.freeboard_contents1.setText(item1.getContents());
        holder.freeboard_time1.setText(item1.getWritetime());
        holder.freeboard_goodcnt1.setText(item1.getGoodcnt()+"");
        holder.freeboard_commentcnt1.setText(item1.getCommentcnt()+"");
        holder.freeboard_imagecnt1.setText(item1.getImageurilist().size()+"");

        /*if(mDataList.get(position).getUserid().equals(cui)){
            holder.delbtn.setVisibility(View.VISIBLE);
        }
        else{
            holder.delbtn.setVisibility(View.GONE);
        }*/

        holder.freeboard_title1.setTag(position);
        holder.freeboard_contents1.setTag(position);
        holder.freeboard_time1.setTag(position);
        holder.freeboard_goodcnt1.setTag(position);
        holder.freeboard_commentcnt1.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView freeboard_title1;
        TextView freeboard_contents1;
        TextView freeboard_time1;
        TextView freeboard_goodcnt1;
        TextView freeboard_commentcnt1;
        TextView freeboard_imagecnt1;
        //Button delbtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            freeboard_title1 = itemView.findViewById(R.id.freeboard_title1);
            freeboard_contents1 = itemView.findViewById(R.id.freeboard_content1);
            freeboard_time1 = itemView.findViewById(R.id.freeboard_time1);
            freeboard_goodcnt1 = itemView.findViewById(R.id.freeboard_goodcnt);
            freeboard_commentcnt1 = itemView.findViewById(R.id.freeboard_commentcnt);
            freeboard_imagecnt1 = itemView.findViewById(R.id.freeboard_imagecnt);
            /*delbtn = itemView.findViewById(R.id.freeboard_del_btn);
            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListner != null){
                            mListner.onDeleteClick2(v, pos, delbtn);
                        }
                    }
                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                   Intent intent = new Intent(v.getContext(), FreeBoardDetailActivity.class);
                   intent.putExtra("id", mDataList.get(pos).getPostid());
                   intent.putExtra("pos", pos);
                   v.getContext().startActivity(intent);
                }
            });

        }

    }
}
