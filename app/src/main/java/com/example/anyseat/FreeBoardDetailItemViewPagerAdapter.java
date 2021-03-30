package com.example.anyseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class FreeBoardDetailItemViewPagerAdapter extends PagerAdapter {

    private Context context = null;

    private List<FreeBoardDetailItem> mDataList;

    public FreeBoardDetailItemViewPagerAdapter() {}

    public FreeBoardDetailItemViewPagerAdapter(Context context, List<FreeBoardDetailItem> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(context != null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.freeboarddetail_rv_item, container, false);

            ImageView imageView = (ImageView)view.findViewById(R.id.fbdrv_image);
            Glide.with(view).load(mDataList.get(0).getImageurilist().get(position)).into(imageView);


        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mDataList.get(0).getImageurilist().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
