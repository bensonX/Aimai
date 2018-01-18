package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterAddressDialog extends RecyclerView.Adapter<RecycleAdapterAddressDialog.Holder> {

    private Context context;
    private List<Address> results = new ArrayList<>();

    public List<Address> getResults() {
        return results;
    }

    public RecycleAdapterAddressDialog(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterAddressDialog.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterAddressDialog.Holder holder, final int position) {
        final Address bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.text_spinner_title.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_spinner_title;

        public Holder(View itemView) {
            super(itemView);
            text_spinner_title = (TextView) itemView.findViewById(R.id.text_spinner_title);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
