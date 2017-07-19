package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterVideoDirectory extends RecyclerView.Adapter<RecycleAdapterVideoDirectory.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterVideoDirectory(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterVideoDirectory.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_directory, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterVideoDirectory.Holder holder, final int position) {
        final TestBean bean = results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

//        private ImageView img_comment_header;

        public Holder(View itemView) {
            super(itemView);
//            img_comment_header = (ImageView) itemView.findViewById(R.id.img_comment_header);
        }
    }
}
