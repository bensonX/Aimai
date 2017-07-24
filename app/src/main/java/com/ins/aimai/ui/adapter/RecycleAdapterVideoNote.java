package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterVideoNote extends RecyclerView.Adapter<RecycleAdapterVideoNote.Holder> {

    private Context context;
    private List<String> results = new ArrayList<>();

    public List<String> getResults() {
        return results;
    }

    public RecycleAdapterVideoNote(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterVideoNote.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_note, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterVideoNote.Holder holder, final int position) {
        final String url = results.get(position);
        GlideUtil.loadImg(holder.img_item_note, R.drawable.default_bk_img, url);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_note;

        public Holder(View itemView) {
            super(itemView);
            img_item_note = (ImageView) itemView.findViewById(R.id.img_item_note);
        }
    }
}
