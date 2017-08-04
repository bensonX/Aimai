package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

public class GridAdapterLessonComp extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Lesson> results = new ArrayList<>();

    public List<Lesson> getResults() {
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
            holder.text_compdetail_title = (TextView) convertView.findViewById(R.id.text_compdetail_title);
            holder.text_compdetail_count_video = (TextView) convertView.findViewById(R.id.text_compdetail_count_video);
            holder.text_compdetail_count_watch = (TextView) convertView.findViewById(R.id.text_compdetail_count_watch);
            holder.text_compdetail_count_safe = (TextView) convertView.findViewById(R.id.text_compdetail_count_safe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Lesson lesson = results.get(position);

        GlideUtil.loadImg(holder.img_compdetail_pic, R.drawable.default_bk_img, lesson.getCover());
        holder.text_compdetail_title.setText(lesson.getCurriculumName());
        holder.text_compdetail_count_video.setText(SpannableStringUtil.create(context, new String[]{lesson.getVideoNum() + "", " 个视频课程"}, new int[]{R.color.am_blue, R.color.com_text_dark_light}));
        holder.text_compdetail_count_watch.setText(SpannableStringUtil.create(context, new String[]{StrUtil.getSize(lesson.getWatchUsers()) + "", " 位观看"}, new int[]{R.color.am_yellow, R.color.com_text_dark_light}));
        holder.text_compdetail_count_safe.setText(SpannableStringUtil.create(context, new String[]{StrUtil.getSize(lesson.getSafeUsers()) + "", " 位安全人员"}, new int[]{R.color.am_blue, R.color.com_text_dark_light}));

        return convertView;
    }

    public class ViewHolder {
        ImageView img_compdetail_pic;
        TextView text_compdetail_title;
        TextView text_compdetail_count_video;
        TextView text_compdetail_count_watch;
        TextView text_compdetail_count_safe;
    }
}
