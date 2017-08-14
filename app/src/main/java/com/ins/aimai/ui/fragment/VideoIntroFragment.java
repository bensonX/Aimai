package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
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

    private TextView text_videointro_title;
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
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_TEXISIZE) {
            int sizeType = (int) event.get("sizeType");
            setTextSize(sizeType);
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
        text_videointro_title = (TextView) rootView.findViewById(R.id.text_videointro_title);
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

    private void setTextSize(int sizeType) {
        switch (sizeType) {
            case AppData.Constant.TEXTSIZE_BIG:
                text_videointro_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_big_nomal));
                text_videointro_describe.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_nomal));
                break;
            case AppData.Constant.TEXTSIZE_MIDDLE:
                text_videointro_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_nomal));
                text_videointro_describe.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_nomal_small));
                break;
            case AppData.Constant.TEXTSIZE_SMALL:
                text_videointro_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_nomal_small));
                text_videointro_describe.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_small));
                break;
        }
    }
}
