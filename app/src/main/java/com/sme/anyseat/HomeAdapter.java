package com.sme.anyseat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final List<PostItem> mDataList;

    public HomeAdapter(List<PostItem> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.mywrite_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem item1 = mDataList.get(position);
        holder.title.setText(item1.getTitle());
        holder.writetime.setText(item1.getContents());
        holder.goodcnt.setText(item1.getGoodcnt()+"");
        holder.commentcnt.setText(item1.getCommentcnt()+"");
        holder.imagecnt.setText(item1.getImageurilist().size()+"");

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView writetime;
        TextView goodcnt;
        TextView commentcnt;
        TextView imagecnt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_title);
            writetime = itemView.findViewById(R.id.my_writetime);
            goodcnt = itemView.findViewById(R.id.my_goodcnt);
            commentcnt = itemView.findViewById(R.id.my_commentcnt);
            imagecnt = itemView.findViewById(R.id.my_imagecnt);

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
