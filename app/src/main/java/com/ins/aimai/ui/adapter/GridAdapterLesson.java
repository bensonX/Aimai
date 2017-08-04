package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;

import java.util.ArrayList;
import java.util.List;

public class GridAdapterLesson extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Lesson> results = new ArrayList<>();

    public List<Lesson> getResults() {
        return results;
    }

    public GridAdapterLesson(Context context) {
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
            convertView = inflater.inflate(R.layout.item_userdetail_lesson, parent, false);
            holder = new ViewHolder();
            holder.img_userdetail_pic = (ImageView) convertView.findViewById(R.id.img_userdetail_pic);
            holder.img_userdetail_flag = (ImageView) convertView.findViewById(R.id.img_userdetail_flag);
            holder.text_userdetail_title = (TextView) convertView.findViewById(R.id.text_userdetail_title);
            holder.text_userdetail_count_video = (TextView) convertView.findViewById(R.id.text_userdetail_count_video);
            holder.text_userdetail_count_learned = (TextView) convertView.findViewById(R.id.text_userdetail_count_learned);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Lesson lesson = results.get(position);

        GlideUtil.loadImg(holder.img_userdetail_pic, R.drawable.default_bk_img, lesson.getCover());
        holder.text_userdetail_title.setText(lesson.getCurriculumName());
        holder.text_userdetail_count_video.setText(SpannableStringUtil.create(context, new String[]{lesson.getVideoNum() + "", " 个视频课程"}, new int[]{R.color.am_blue, R.color.com_text_dark_light}));
        holder.text_userdetail_count_learned.setText(SpannableStringUtil.create(context, new String[]{"已学习 ", lesson.getStudyNum() + "", " 课时"}, new int[]{R.color.com_text_blank, R.color.am_blue, R.color.com_text_blank}));
        holder.progress.setProgress((int) ((float) lesson.getStudyNum() / (float) lesson.getCourseWareNum() * 100));
        holder.img_userdetail_flag.setVisibility(lesson.getIsPass() == 1 ? View.VISIBLE : View.GONE);
        return convertView;
    }

    public class ViewHolder {
        ImageView img_userdetail_pic;
        ImageView img_userdetail_flag;
        TextView text_userdetail_title;
        TextView text_userdetail_count_video;
        TextView text_userdetail_count_learned;
        ProgressBar progress;
    }
}
