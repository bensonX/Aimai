package com.ins.aimai.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.ExamModelOffi;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.RectBackTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class StatusHelper {

    //返回：0，购买，1：分配，2：无
    public static int lessonDetailBtn(int type) {
        if (type == 0) {
            return 0;
        } else {
            User user = AppData.App.getUser();
            if (user != null && user.getRoleId() == User.USER) {
                return 0;
            } else if (user != null && user.getRoleId() == User.COMPANY_USER) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public static class Exam {

        //设置模拟题的状态
        public static void setModelStatus(RectBackTextView textView, ExamModelOffi exam) {
            if (exam.getIsFinish() == 1) {
                textView.setVisibility(View.VISIBLE);
                textView.setColorSrc(R.color.com_dark);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
                textView.setText("已模拟");
            } else {
                textView.setVisibility(View.GONE);
            }
        }


        //设置正式考试题的状态
        public static void setOffiStatus(RectBackTextView textView, ExamModelOffi exam) {
            if (exam.getPassNum() == 1) {
                //已通过
                textView.setVisibility(View.VISIBLE);
                textView.setColorSrc(R.color.am_blue);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
                textView.setText("已通过");
            } else {
                if (exam.getUnPassNum() == 0) {
                    //未考试
                    textView.setVisibility(View.GONE);
                } else if (exam.getUnPassNum() == 1) {
                    //未通过
                    textView.setVisibility(View.VISIBLE);
                    textView.setColorSrc(R.color.com_dark);
                    textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
                    textView.setText("未通过");
                } else {
                    //多次未通过
                    textView.setVisibility(View.VISIBLE);
                    textView.setColorSrc(R.color.com_dark);
                    textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
                    textView.setText(exam.getUnPassNum() + "次未通过");
                }
            }
        }

        //判断正式考试题是否可以重新考试
        public static boolean needOffiRedeal(ExamModelOffi exam) {
            if (exam.getPassNum() == 0 && exam.getUnPassNum() > 0 && exam.getUnPassNum() < 3) {
                return true;
            } else {
                return false;
            }
        }
    }
}
