package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StrUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liaoinstan
 */
public class VideoIntroFragment extends BaseFragment {

    private int position;
    private View rootView;

    private TextView text_videointro_describe;

    public static Fragment newInstance(int position) {
        VideoIntroFragment fragment = new VideoIntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LESSONDETAIL_INTRO) {
            String intro = (String) event.get("intro");
            setData(intro);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_videointro, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        text_videointro_describe = (TextView) rootView.findViewById(R.id.text_videointro_describe);
    }

    private void initCtrl() {
        text_videointro_describe.setText("");
    }

    private void initData() {
    }

    private void setData(String intro) {
        text_videointro_describe.setText(StrUtil.getSpace() + intro);
    }
}
