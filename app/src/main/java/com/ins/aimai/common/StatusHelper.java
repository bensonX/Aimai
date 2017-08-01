package com.ins.aimai.common;

import android.content.Context;
import android.view.View;

import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;

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

}
