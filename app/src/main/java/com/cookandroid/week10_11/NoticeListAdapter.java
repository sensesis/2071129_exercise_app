package com.cookandroid.week10_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notice, parent, false);
        }

        TextView noticeText = (TextView) convertView.findViewById(R.id.noticeText);
        TextView nameText = (TextView) convertView.findViewById(R.id.nameText);
        TextView dateText = (TextView) convertView.findViewById(R.id.dateText);

        Notice notice = noticeList.get(position);

        noticeText.setText(notice.getNotice());
        nameText.setText(notice.getName());
        dateText.setText(notice.getDate());

        return convertView;
    }
}