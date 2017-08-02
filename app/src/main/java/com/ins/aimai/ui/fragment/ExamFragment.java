package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.ui.activity.ExamActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.ui.view.QuestionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan
 */
public class ExamFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;
    private ExamActivity activity;

    private QuestionView questionView;

    public static Fragment newInstance(int position) {
        ExamFragment fragment = new ExamFragment();
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
        rootView = inflater.inflate(R.layout.fragment_exam, container, false);
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
        activity = (ExamActivity) getActivity();
    }

    private void initView() {
        questionView = (QuestionView) rootView.findViewById(R.id.questionView);
    }

    private void initCtrl() {
    }

    private void initData() {
//        String title = "党的十八大以来，一些标志性话语深刻反映了中央治国理政新理念，其中，下列标志性话语与治国理政新理念对应错误是（ ）";
//        final String option1 = "“刮骨疗毒，壮士割腕”——加强党风";
//        final String option2 = "“踏石留印，抓铁有痕”--推动国防军队改革";
//        final String option3 = "“凝聚共识，合作共赢”--发展大国外交";
//        final String option4 = "“一个都不能少”——全面建设小康社会";
//        List<String> options = new ArrayList<String>() {{
//            add(option1);
//            add(option2);
//            add(option3);
//            add(option4);
//        }};
//        QuestionBean questionBean = new QuestionBean(title, options);
        QuestionBean questionBean = activity.getQuestions().get(position);
        questionView.setData(questionBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_learntest_practice:
                break;
        }
    }
}
