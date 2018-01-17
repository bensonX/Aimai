package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.Video;
import com.ins.aimai.common.AppHelper;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterAddress extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Address> results = new ArrayList<>();

    public List<Address> getResults() {
        return results;
    }

    public ListAdapterAddress(Context context) {
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
            convertView = inflater.inflate(R.layout.lay_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.text_spinner_title = (TextView) convertView.findViewById(R.id.text_spinner_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Address address = results.get(position);

        holder.text_spinner_title.setText(address.getName());

        return convertView;
    }

    public class ViewHolder {
        TextView text_spinner_title;
    }
}
