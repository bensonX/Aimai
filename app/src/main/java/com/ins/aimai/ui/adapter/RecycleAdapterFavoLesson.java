package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterFavoLesson extends RecyclerView.Adapter<RecycleAdapterFavoLesson.Holder> {

    private Context context;
    private List<Lesson> results = new ArrayList<>();

    public List<Lesson> getResults() {
        return results;
    }

    public RecycleAdapterFavoLesson(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterFavoLesson.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_favo_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterFavoLesson.Holder holder, final int position) {
        final Lesson lesson = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_item_favolesson_header, R.drawable.default_bk_img, lesson.getCover());
        holder.text_item_favolesson_title.setText(lesson.getCurriculumName());
        holder.text_item_favolesson_count.setText(lesson.getVideoNum() + "");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_favolesson_header;
        private TextView text_item_favolesson_title;
        private TextView text_item_favolesson_count;

        public Holder(View itemView) {
            super(itemView);
            img_item_favolesson_header = (ImageView) itemView.findViewById(R.id.img_item_favolesson_header);
            text_item_favolesson_title = (TextView) itemView.findViewById(R.id.text_item_favolesson_title);
            text_item_favolesson_count = (TextView) itemView.findViewById(R.id.text_item_favolesson_count);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
