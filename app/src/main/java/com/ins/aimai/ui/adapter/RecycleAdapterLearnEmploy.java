package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.RectBackTextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnEmploy extends RecyclerView.Adapter<RecycleAdapterLearnEmploy.Holder> {

    private Context context;
    private List<User> results = new ArrayList<>();

    public List<User> getResults() {
        return results;
    }

    public RecycleAdapterLearnEmploy(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnEmploy.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_employ, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnEmploy.Holder holder, final int position) {
        final User user = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadCircleImg(holder.img_learn_employ_header, R.drawable.default_header_edit, user.getAvatar());
        holder.text_learn_employ_name.setText(user.getShowName());
        //TODO:课程学习信息还暂时没有
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_learn_employ_header;
        private RectBackTextView text_learn_employ_status;
        private TextView text_learn_employ_name;
        private TextView text_learn_employ_lesson;
        private TextView text_learn_employ_time;
        private TextView text_learn_employ_count;
        private View lay_learn_employ_leaned;

        public Holder(View itemView) {
            super(itemView);
            img_learn_employ_header = (ImageView) itemView.findViewById(R.id.img_learn_employ_header);
            text_learn_employ_status = (RectBackTextView) itemView.findViewById(R.id.text_learn_employ_status);
            text_learn_employ_name = (TextView) itemView.findViewById(R.id.text_learn_employ_name);
            text_learn_employ_lesson = (TextView) itemView.findViewById(R.id.text_learn_employ_lesson);
            text_learn_employ_time = (TextView) itemView.findViewById(R.id.text_learn_employ_time);
            lay_learn_employ_leaned = itemView.findViewById(R.id.lay_learn_employ_leaned);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
