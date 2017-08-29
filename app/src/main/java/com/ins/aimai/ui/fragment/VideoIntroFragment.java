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
    private TextView text_videointro_teachername_title;
    private TextView text_videointro_teachername;
    private TextView text_videointro_teacherintro_title;
    private TextView text_videointro_teacherintro;
    private TextView text_videointro_applyperson_title;
    private TextView text_videointro_applyperson;

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
            String teacherName = (String) event.get("teacherName");
            String teacherIntro = (String) event.get("teacherIntro");
            String applyPerson = (String) event.get("applyPerson");
            setData(intro, teacherName, teacherIntro, applyPerson);
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
        text_videointro_teachername_title = (TextView) rootView.findViewById(R.id.text_videointro_teachername_title);
        text_videointro_teachername = (TextView) rootView.findViewById(R.id.text_videointro_teachername);
        text_videointro_teacherintro_title = (TextView) rootView.findViewById(R.id.text_videointro_teacherintro_title);
        text_videointro_teacherintro = (TextView) rootView.findViewById(R.id.text_videointro_teacherintro);
        text_videointro_applyperson_title = (TextView) rootView.findViewById(R.id.text_videointro_applyperson_title);
        text_videointro_applyperson = (TextView) rootView.findViewById(R.id.text_videointro_applyperson);
    }

    private void initCtrl() {
        text_videointro_describe.setText("");
    }

    private void initData() {
    }

    private void setData(String intro, String teacherName, String teacherIntro, String applyPerson) {
        text_videointro_describe.setText(StrUtil.getSpace() + intro);
        text_videointro_teachername.setText(StrUtil.getSpace() + teacherName);
        text_videointro_teacherintro.setText(StrUtil.getSpace() + teacherIntro);
        text_videointro_applyperson.setText(StrUtil.getSpace() + applyPerson);
    }

    private void setTextSize(int sizeType) {
        switch (sizeType) {
            case AppData.Constant.TEXTSIZE_BIG:
                setTextSize(R.dimen.text_big_nomal, R.dimen.text_nomal);
                break;
            case AppData.Constant.TEXTSIZE_MIDDLE:
                setTextSize(R.dimen.text_nomal, R.dimen.text_nomal_small);
                break;
            case AppData.Constant.TEXTSIZE_SMALL:
                setTextSize(R.dimen.text_nomal_small, R.dimen.text_small);
                break;
        }
    }

    private void setTextSize(int titleSize, int contentSize) {
        text_videointro_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(titleSize));
        text_videointro_teachername_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(titleSize));
        text_videointro_teacherintro_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(titleSize));
        text_videointro_applyperson_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(titleSize));
        text_videointro_describe.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(contentSize));
        text_videointro_teachername.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(contentSize));
        text_videointro_teacherintro.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(contentSize));
        text_videointro_applyperson.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(contentSize));
    }
}
