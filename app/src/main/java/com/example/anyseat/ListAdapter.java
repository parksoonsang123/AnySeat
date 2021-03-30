package com.example.anyseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<String> questions =new ArrayList<>();

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context c = viewGroup.getContext();
        if(view == null){
            LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.dialoglistview, viewGroup, false);
        }

        TextView que = view.findViewById(R.id.textView3);
        CheckBox ch = view.findViewById(R.id.checkBox);


        return null;
    }
}
