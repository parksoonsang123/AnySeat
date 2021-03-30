package com.example.anyseat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FreeBoardDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<FreeBoardDetailItem> mDataList;

    private FirebaseAuth mAuth;

    private String cui;

    private Context context;



    public FreeBoardDetailAdapter(List<FreeBoardDetailItem> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
        //생성자에서 좋아요 눌렀는지에 대한 정보 받아오기
    }

    public FreeBoardDetailAdapter(List<FreeBoardDetailItem> mDataList, String cui, Context context) {
        this.mDataList = mDataList;
        this.cui = cui;
        this.context = context;
    }

    public interface OnGoodClickListener {
        void onGoodClick(View v, int position, Button btn);
    }
    private OnGoodClickListener mListener = null;
    public void setOnGoodClickListner(OnGoodClickListener listner){
        this.mListener = listner;
    }


    public interface OnDeleteClickListner {
        void onDeleteClick(View v, int position, Button btn);
    }
    private OnDeleteClickListner mListner2 = null;
    public void setOnDeleteClickListener(OnDeleteClickListner listener){
        this.mListner2 = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == Code.ViewType.FIRST){
            view = inflater.inflate(R.layout.freeboarddetail_item1, parent, false);
            //return 각각의 뷰홀더
            return new FirstViewHolder(view);
        }
        else {
            view = inflater.inflate(R.layout.freeboarddetail_item2, parent, false);
            //return 각각의 뷰홀더
            return  new SecondViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof FirstViewHolder){
            ((FirstViewHolder)holder).title.setText(mDataList.get(position).getTitle());
            ((FirstViewHolder)holder).contents.setText(mDataList.get(position).getContents());
            ((FirstViewHolder)holder).time.setText(mDataList.get(position).getWritetime());
            ((FirstViewHolder)holder).goodcnt.setText(mDataList.get(position).getGoodcnt()+"");
            ((FirstViewHolder)holder).commentcnt.setText(mDataList.get(position).getCommentcnt()+"");
            ((FirstViewHolder)holder).imagecnt.setText(mDataList.get(position).getImageurilist().size()+"");


            if(mDataList.get(0).getPress() == null){
                ((FirstViewHolder)holder).goodbtn.setTextColor(Color.WHITE);
            }
            else if(mDataList.get(0).getPress() != null){
                if(mDataList.get(0).getPress().equals("0")){
                    ((FirstViewHolder)holder).goodbtn.setTextColor(Color.WHITE);
                }
                else if(mDataList.get(0).getPress().equals("1")){
                    ((FirstViewHolder)holder).goodbtn.setTextColor(Color.RED);
                }
            }

            if(!mDataList.get(position).getImageexist().equals("1")){    // 이미지가 없을 때 이미지뷰 칸 제거
                //((FirstViewHolder)holder).viewPager.setVisibility(View.GONE);
                ((FirstViewHolder)holder).recyclerView.setVisibility(View.GONE);
            }


        }
        else if(holder instanceof SecondViewHolder){

            ((SecondViewHolder)holder).contents.setText(mDataList.get(position).getContents());
            ((SecondViewHolder)holder).time.setText(mDataList.get(position).getWritetime());

            if(mDataList.get(position).getUserid().equals(cui)){
                ((SecondViewHolder)holder).deletebtn.setVisibility(View.VISIBLE);
            }
            else{
                ((SecondViewHolder)holder).deletebtn.setVisibility(View.GONE);
            }

        }

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewType();
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView contents;
        TextView time;
        TextView goodcnt;
        TextView commentcnt;
        TextView imagecnt;
        Button goodbtn;
        //ImageView image;
        //ViewPager viewPager;
        //FreeBoardDetailItemViewPagerAdapter viewPagerAdapter;

        RecyclerView recyclerView;

        public FirstViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.freeboarddetail_title);
            contents = itemView.findViewById(R.id.freeboarddetail_contents);
            time = itemView.findViewById(R.id.freeboarddetail_time);
            goodcnt = itemView.findViewById(R.id.freeboarddetail_goodcnt);
            commentcnt = itemView.findViewById(R.id.freeboarddetail_commentcnt);
            imagecnt = itemView.findViewById(R.id.freeboarddetail_imagecnt);
            goodbtn = itemView.findViewById(R.id.freeboarddetail_goodbtn);
            goodbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    //Toast.makeText(itemView.getContext(), ""+pos, Toast.LENGTH_SHORT).show();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onGoodClick(v, pos, goodbtn);
                            // 내가 좋아요 인지 아닌지 list 에 저장
                        }
                    }

                }
            });

            /*viewPager = itemView.findViewById(R.id.freeboarddetail_viewpager);
            viewPagerAdapter = new FreeBoardDetailItemViewPagerAdapter(context, mDataList);
            viewPager.setAdapter(viewPagerAdapter);*/

            recyclerView = itemView.findViewById(R.id.freeboarddetail_rv);
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(manager);

            FreeBoardDetailItemRecyclerAdapter adapter = new FreeBoardDetailItemRecyclerAdapter(context, mDataList);
            adapter.setOnImageClickListener(new FreeBoardDetailItemRecyclerAdapter.OnImageClickListener() {
                @Override
                public void onImageClick(View v, int position) {
                    Intent intent = new Intent(itemView.getContext(), OnlyImage.class);
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("list", mDataList.get(0).getImageurilist());
                    itemView.getContext().startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    private class SecondViewHolder extends RecyclerView.ViewHolder{

        TextView contents;
        TextView time;
        Button deletebtn;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);

            contents = itemView.findViewById(R.id.freeboarddetailitem_contents);
            time = itemView.findViewById(R.id.freeboarddetailitem_time);
            deletebtn = itemView.findViewById(R.id.delete_btn);
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListner2 != null){
                            mListner2.onDeleteClick(v, pos, deletebtn);
                        }
                    }
                }
            });


        }
    }

}
