package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLessonTaste extends RecyclerView.Adapter<RecycleAdapterLessonTaste.Holder> {

    private Context context;
    private List<Lesson> results = new ArrayList<>();

    public List<Lesson> getResults() {
        return results;
    }

    public RecycleAdapterLessonTaste(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLessonTaste.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lession_taste, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLessonTaste.Holder holder, final int position) {
        final Lesson bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_lession_taste, R.drawable.default_bk_img, bean.getCover());
        holder.text_lession_taste.setText(bean.getCurriculumName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_lession_taste;
        private TextView text_lession_taste;

        public Holder(View itemView) {
            super(itemView);
            img_lession_taste = (ImageView) itemView.findViewById(R.id.img_lession_taste);
            text_lession_taste = (TextView) itemView.findViewById(R.id.text_lession_taste);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
