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
import com.ins.aimai.common.AppHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnComp extends RecyclerView.Adapter<RecycleAdapterLearnComp.Holder> {

    private Context context;
    private List<User> results = new ArrayList<>();

    public List<User> getResults() {
        return results;
    }

    public RecycleAdapterLearnComp(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnComp.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_comp, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnComp.Holder holder, final int position) {
        final User user = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_item_learncomp, R.drawable.default_bk_img, user.getAvatar());
        holder.text_item_learncomp_title.setText(user.getShowName());
        holder.text_item_learncomp_address.setText(AppHelper.Gov.getCompAddressStr(user));
        holder.text_item_learncomp_count.setText(SpannableStringUtil.create(context, new String[]{"参培人员 ", user.getPeopleNum() + "", " 位"}, new int[]{R.color.com_text_dark_light, R.color.am_blue, R.color.com_text_dark_light}));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_learncomp;
        private TextView text_item_learncomp_title;
        private TextView text_item_learncomp_address;
        private TextView text_item_learncomp_count;

        public Holder(View itemView) {
            super(itemView);
            img_item_learncomp = (ImageView) itemView.findViewById(R.id.img_item_learncomp);
            text_item_learncomp_title = (TextView) itemView.findViewById(R.id.text_item_learncomp_title);
            text_item_learncomp_address = (TextView) itemView.findViewById(R.id.text_item_learncomp_address);
            text_item_learncomp_count = (TextView) itemView.findViewById(R.id.text_item_learncomp_count);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
