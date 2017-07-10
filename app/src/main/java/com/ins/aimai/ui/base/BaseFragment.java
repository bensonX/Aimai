package com.ins.aimai.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;


/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class BaseFragment extends Fragment {

    protected Toolbar toolbar;

    public void setToolbar() {
        setToolbar(null);
    }

    public void setToolbar(String title) {
        View root = getView();
        if (root == null) {
            return;
        }
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        if (toolbar != null) {
//            toolbar.setNavigationIcon(R.drawable.icon_back);
            toolbar.setTitle("");
            //设置toobar居中文字
            TextView text_title = (TextView) root.findViewById(R.id.text_toolbar_title);
            if (text_title != null) {
                if (!TextUtils.isEmpty(title)) {
                    text_title.setText(title);
                }
            }
        }
    }
}
