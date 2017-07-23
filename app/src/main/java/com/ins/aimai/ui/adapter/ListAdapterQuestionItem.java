package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.utils.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterQuestionItem extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<QuestionView.Option> results = new ArrayList<>();

    public List<QuestionView.Option> getResults() {
        return results;
    }

    public ListAdapterQuestionItem(Context context) {
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
            convertView = inflater.inflate(R.layout.item_question, parent, false);
            holder = new ViewHolder();
            holder.text_question_item = (TextView) convertView.findViewById(R.id.text_question_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final QuestionView.Option bean = results.get(position);

        holder.text_question_item.setText(NumUtil.intToABC(position) + "、" + bean.content);
        holder.text_question_item.setSelected(bean.isSelect());

        return convertView;
    }

    public class ViewHolder {
        TextView text_question_item;
    }
}
