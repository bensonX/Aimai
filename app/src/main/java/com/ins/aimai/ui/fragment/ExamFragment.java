package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.ExamCountDownTimer;
import com.ins.aimai.net.helper.NetExamHelper;
import com.ins.aimai.ui.activity.ExamActivity;
import com.ins.aimai.ui.activity.ExamResultActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.aimai.ui.view.QuestionView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan
 */
public class ExamFragment extends BaseFragment implements View.OnClickListener, QuestionView.OnOptionSelectListener {

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
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EXAM_TEXISIZE) {
            int size = (int) event.get("size");
            questionView.setTextSize(size);
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
        questionView.setOnOptionSelectListener(this);
        //恢复上次选择的字体大小
        int textSize = AppData.App.getTextSizeExam();
        switch (textSize) {
            case QuestionView.TEXTSIZE_BIG:
                questionView.setTextSize(textSize);
                break;
            case QuestionView.TEXTSIZE_NOMAL:
                //默认大小，不用设置
                break;
            case QuestionView.TEXTSIZE_SMALL:
                questionView.setTextSize(textSize);
                break;
        }
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

    private DialogSureAimai dialogSureCommit;

    @Override
    public void onOptionSelect(QuestionView.Option option, int position) {
        //判断dialog为null是为了只弹窗一次
        if (dialogSureCommit == null && AppHelper.Exam.checkIsFinishQuestions(activity.getQuestions())) {
            dialogSureCommit = new DialogSureAimai(getActivity(), "您已打完所有考题！是否提交考卷？", null, "提交并查看", "返回检阅");
            dialogSureCommit.setOnCancleListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showLoadingDialog();
                    NetExamHelper.getInstance().submitExam(activity.getPaperId(), activity.getOrderId(), ExamCountDownTimer.useTime, activity.getQuestions(), new NetExamHelper.OnExamSubmitCallback() {
                        @Override
                        public void onSuccess(ExamResultPojo examResultPojo) {
                            EventBus.getDefault().post(new EventBean(EventBean.EVENT_EXAM_SUBMITED));
                            ExamResultActivity.start(activity, activity.getPaperId(), activity.getOrderId(), activity.getType());
                            activity.finish();
                        }
                    });
                }
            });
            dialogSureCommit.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_learntest_practice:
                break;
        }
    }
}
