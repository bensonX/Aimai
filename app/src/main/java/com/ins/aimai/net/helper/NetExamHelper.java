package com.ins.aimai.net.helper;

import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.utils.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetExamHelper {
    private static NetExamHelper instance = null;

    private NetExamHelper() {
    }

    public static synchronized NetExamHelper getInstance() {
        if (instance == null) {
            instance = new NetExamHelper();
        }
        return instance;
    }


    public void submitExam(int paperId, int orderId, int seconds, List<QuestionBean> questions, final OnExamSubmitCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("answers", AppHelper.Exam.getAnswerSubmitParam(questions))
                .put("paperId", paperId)
                .put("orderId", orderId)
                .put("seconds", seconds)
                .build();
        NetApi.NI().submitExam(param).enqueue(new BaseCallback<ExamResultPojo>(ExamResultPojo.class) {
            @Override
            public void onSuccess(int status, ExamResultPojo examResultPojo, String msg) {
                ToastUtil.showToastShort(msg);
                if (callback != null) callback.onSuccess(examResultPojo);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public interface OnExamSubmitCallback {
        void onSuccess(ExamResultPojo examResultPojo);
    }
}
