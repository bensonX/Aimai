package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.view.RectBackTextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnUser extends RecyclerView.Adapter<RecycleAdapterLearnUser.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<User> results = new ArrayList<>();
    private int flag;

    public List<User> getResults() {
        return results;
    }

    public RecycleAdapterLearnUser(Context context, int flag) {
        this.context = context;
        this.flag = flag;
    }

    @Override
    public RecycleAdapterLearnUser.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_user, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnUser.Holder holder, final int position) {
        final User user = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadCircleImg(holder.img_learn_user_header, R.drawable.default_header_edit);
        holder.text_learn_user_name.setText(user.getShowName());
        holder.text_learn_user_phone.setText(user.getPhone());
        if (flag == 0) {
            //在观看
            holder.text_learn_user_learncount.setVisibility(View.VISIBLE);
            holder.text_learn_user_status.setVisibility(View.GONE);
            holder.text_learn_user_learncount.setText(SpannableStringUtil.create(context, new String[]{"已学习 ", user.getFinishCourseWareNum() + "", "课时"}, new int[]{R.color.com_text_dark_light, R.color.am_blue, R.color.com_text_dark_light}));
        } else {
            //已考核
            holder.text_learn_user_learncount.setVisibility(View.GONE);
            holder.text_learn_user_status.setVisibility(View.VISIBLE);
            if (user.isPass()) {
                holder.text_learn_user_status.setText("考核通过");
                holder.text_learn_user_status.setColorSrc(R.color.am_blue);
            } else {
                holder.text_learn_user_status.setText("考核未通过");
                holder.text_learn_user_status.setColorSrc(R.color.com_dark);
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_learn_user_header;
        private TextView text_learn_user_name;
        private TextView text_learn_user_phone;
        private TextView text_learn_user_learncount;
        private RectBackTextView text_learn_user_status;

        public Holder(View itemView) {
            super(itemView);
            img_learn_user_header = (ImageView) itemView.findViewById(R.id.img_learn_user_header);
            text_learn_user_name = (TextView) itemView.findViewById(R.id.text_learn_user_name);
            text_learn_user_phone = (TextView) itemView.findViewById(R.id.text_learn_user_phone);
            text_learn_user_learncount = (TextView) itemView.findViewById(R.id.text_learn_user_learncount);
            text_learn_user_status = (RectBackTextView) itemView.findViewById(R.id.text_learn_user_status);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
