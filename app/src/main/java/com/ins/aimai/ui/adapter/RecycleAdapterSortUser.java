package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.SortBean;
import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSortUser extends RecyclerView.Adapter<RecycleAdapterSortUser.Holder> {

    private List<User> results;
    private Context context;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

    private View selectAllView;

    public List<User> getResults() {
        return results;
    }

    public RecycleAdapterSortUser(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void addAll(List<User> results) {
        results.addAll(results);
    }

    public void add(User bean, int position) {
        results.add(position, bean);
        notifyItemInserted(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_user, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (results == null || results.size() == 0 || results.size() <= position)
            return;
        final User user = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setSelect(!user.isSelect());
                notifyItemChanged(position);
                if (selectAllView != null) selectAllView.setSelected(isAllSelect());
            }
        });
        if (user != null) {
            //TextDrawable drawable = mDrawableBuilder.build(String.valueOf(user.getSortName().charAt(0)), mColorGenerator.getColor(user.getSortName()));
            //holder.iv_img.setImageDrawable(drawable);
            GlideUtil.loadCircleImg(holder.iv_img, R.drawable.default_header_edit, user.getAvatar());
            holder.tv_name.setText(user.getSortNameSmart());
            holder.tv_phone.setText(user.getPhone());
            holder.img_check.setSelected(user.isSelect());
        }
    }

    public void setSelectAll(boolean selectAll) {
        SelectHelper.selectAllSelectBeans(results, selectAll);
        notifyDataSetChanged();
    }

    public void setSelectAllView(View selectAllView) {
        this.selectAllView = selectAllView;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public String getSelectedIds() {
        String ids = "";
        for (User user : results) {
            if (user.isSelect()) {
                ids += user.getId() + ",";
            }
        }
        ids = StrUtil.subLastChart(ids, ",");
        return ids;
    }

    public boolean isAllSelect() {
        for (User user : results) {
            if (!user.isSelect()) {
                return false;
            }
        }
        return true;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public final ImageView iv_img;
        public final TextView tv_name;
        public final TextView tv_phone;
        public final ImageView img_check;

        public Holder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            img_check = (ImageView) itemView.findViewById(R.id.img_check);
        }
    }
}
