package com.example.lik.gps;


import android.view.View;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    public class ListContents {
        String msg;
        int type;

        ListContents(String msg, int type) {
            this.msg = msg;
            this.type = type;
        }
    }

    private ArrayList<ListContents> list;

    public CustomAdapter() {
        list = new ArrayList<ListContents>();
    }

    public void add(String msg, int type) {
        list.add(new ListContents(msg,type));
    }

    public void remove(int position) {
        list.remove(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        CustomHolder holder = null;
        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);

            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            text = (TextView) convertView.findViewById(R.id.text);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);

            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            convertView.setTag(holder);
        }
        else {
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
        }

        text.setText(list.get(position).msg);
        if( list.get(position).type == 0 ) {
            text.setBackgroundResource(R.drawable.inbox2);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }
        else if(list.get(position).type  == 1) {
            text.setBackgroundResource(R.drawable.outbox2);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class CustomHolder {
        TextView m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
    }
}
