package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Video;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.ins.aimai.ui.view.QuestionView.TEXTSIZE_NOMAL;

public class ListAdapterDirectory extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Video> results = new ArrayList<>();

    public List<Video> getResults() {
        return results;
    }

    public ListAdapterDirectory(Context context) {
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
            convertView = inflater.inflate(R.layout.item_video_directory_comp, parent, false);
            holder = new ViewHolder();
            holder.img_directory_status = convertView.findViewById(R.id.img_directory_status);
            holder.text_directory_title = (TextView) convertView.findViewById(R.id.text_directory_title);
            holder.text_directory_time = (TextView) convertView.findViewById(R.id.text_directory_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Video video = results.get(position);

        holder.text_directory_title.setText(video.getCourseWareName() + ": " + video.getName());
        holder.text_directory_time.setText(TimeUtil.formatTimeRange(video.getHighDefinitionSeconds() * 1000, "mm:ss"));
        holder.text_directory_title.setSelected(AppHelper.VideoPlay.isVideoStatusFinish(video));
        holder.img_directory_status.setVisibility(AppHelper.VideoPlay.isVideoStatusFinish(video) ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

    public class ViewHolder {
        View img_directory_status;
        TextView text_directory_title;
        TextView text_directory_time;
    }
}
