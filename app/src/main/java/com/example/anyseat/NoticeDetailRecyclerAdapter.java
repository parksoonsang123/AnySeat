package com.example.anyseat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NoticeDetailRecyclerAdapter extends RecyclerView.Adapter<NoticeDetailRecyclerAdapter.ViewHolder> {

    private Context context = null;

    private List<PostItem2> mDataList;

    public NoticeDetailRecyclerAdapter() {}

    public NoticeDetailRecyclerAdapter(Context context, List<PostItem2> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    public interface OnImageClickListener {
        void onImageClick(View v, int position);
    }
    private OnImageClickListener mListener = null;
    public void setOnImageClickListener(OnImageClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.freeboarddetail_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull NoticeDetailRecyclerAdapter.ViewHolder holder, int position) {
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.background_rounding);
        holder.imageView.setBackground(drawable);
        holder.imageView.setClipToOutline(true);
        Glide.with(context).load(mDataList.get(0).getImageurilist().get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataList.get(0).getImageurilist().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fbdrv_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onImageClick(v, pos);
                        }
                    }
                }
            });
        }
    }


}
