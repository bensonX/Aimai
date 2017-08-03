package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.StatisLearn;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.ModelOffiActivity;
import com.ins.aimai.ui.activity.OfficialActivity;
import com.ins.aimai.ui.activity.QuestionBankActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class LearnTestFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;

    private TextView text_learntest_prictice_done;
    private TextView text_learntest_prictice_lv;
    private TextView text_learntest_error_count;
    private TextView text_learntest_model_done;
    private TextView text_learntest_model_lv;
    private TextView text_learntest_official_pass;
    private TextView text_learntest_official_unpass;

    public static Fragment newInstance(int position) {
        LearnTestFragment fragment = new LearnTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_learn_test, container, false);
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
        text_learntest_prictice_done = (TextView) rootView.findViewById(R.id.text_learntest_prictice_done);
        text_learntest_prictice_lv = (TextView) rootView.findViewById(R.id.text_learntest_prictice_lv);
        text_learntest_error_count = (TextView) rootView.findViewById(R.id.text_learntest_error_count);
        text_learntest_model_done = (TextView) rootView.findViewById(R.id.text_learntest_model_done);
        text_learntest_model_lv = (TextView) rootView.findViewById(R.id.text_learntest_model_lv);
        text_learntest_official_pass = (TextView) rootView.findViewById(R.id.text_learntest_official_pass);
        text_learntest_official_unpass = (TextView) rootView.findViewById(R.id.text_learntest_official_unpass);
        rootView.findViewById(R.id.lay_learntest_practice).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_error).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_model).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_official).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
        netStatisLearn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_learntest_practice:
                QuestionBankActivity.startPractice(getContext());
                break;
            case R.id.lay_learntest_error:
                QuestionBankActivity.startError(getContext());
                break;
            case R.id.lay_learntest_model:
                ModelOffiActivity.startModel(getContext());
                break;
            case R.id.lay_learntest_official:
                ModelOffiActivity.startOffi(getContext());
                break;
        }
    }

    private void setData(StatisLearn statisLearn) {
        if (statisLearn != null) {
            text_learntest_prictice_done.setText(statisLearn.getCountNum() + "");
            text_learntest_prictice_lv.setText(AppHelper.formatPercent(statisLearn.getCorrectPercent() * 100));
            text_learntest_error_count.setText("(" + statisLearn.getErrorNum() + ")");
            text_learntest_model_done.setText(statisLearn.getSimulationCountNum() + "");
            text_learntest_model_lv.setText(AppHelper.formatPercent(statisLearn.getSimulationCorrectPercent() * 100));
            text_learntest_official_pass.setText(statisLearn.getPassNum() + "");
            text_learntest_official_unpass.setText(statisLearn.getNonPassNum() + "");
        }
    }

    private void netStatisLearn() {
        Map<String, Object> param = new NetParam().build();
        NetApi.NI().statisLearn(param).enqueue(new BaseCallback<StatisLearn>(StatisLearn.class) {
            @Override
            public void onSuccess(int status, StatisLearn statisLearn, String msg) {
                setData(statisLearn);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
