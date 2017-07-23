package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class GridAdapterLessonComp extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public GridAdapterLessonComp(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_compdetail_lesson, parent, false);
            holder = new ViewHolder();
            holder.img_compdetail_pic = (ImageView) convertView.findViewById(R.id.img_compdetail_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TestBean bean = results.get(position);

        GlideUtil.loadImgTest(holder.img_compdetail_pic);

        return convertView;
    }

    public class ViewHolder {
        ImageView img_compdetail_pic;
    }
}
