package com.ins.aimai.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.VideoFinishStatus;
import com.ins.aimai.ui.activity.ModelOffiActivity;
import com.ins.aimai.ui.activity.QuestionBankActivity;
import com.ins.common.utils.FontUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 确定取消弹窗
 */
public class DialogToExam extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView text_toexam_title;
    private TextView text_toexam_msg;
    private TextView text_toexam_next;
    private TextView text_toexam_practice;
    private TextView text_toexam_model;
    private TextView text_toexam_office;
    private View lay_toexam_practice;
    private View lay_toexam_model;
    private View lay_toexam_office;
    private View lay_toexam_btns;

    private String msg;
    private String title;
    private String cancelStr;
    private String sureStr;

    //拓展字段
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public DialogToExam(Context context) {
        this(context, "本课时已看完", "您可以选择开始考核课时，或者观看下个课时");
    }

    public DialogToExam(Context context, String title, String msg) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.title = title;
        this.msg = msg;
        setLoadingDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /////////获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        /////////设置高宽
        lp.width = (int) (screenWidth * 0.9); // 宽度
//        lp.height =  WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = (int) (lp.width*0.65); // 高度
        this.setCanceledOnTouchOutside(true);
    }

    private void setLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_toexam, null);

        text_toexam_title = (TextView) v.findViewById(R.id.text_toexam_title);
        text_toexam_msg = (TextView) v.findViewById(R.id.text_toexam_msg);
        text_toexam_next = (TextView) v.findViewById(R.id.text_toexam_next);
        text_toexam_practice = (TextView) v.findViewById(R.id.text_toexam_practice);
        text_toexam_model = (TextView) v.findViewById(R.id.text_toexam_model);
        text_toexam_office = (TextView) v.findViewById(R.id.text_toexam_office);
        lay_toexam_practice = v.findViewById(R.id.lay_toexam_practice);
        lay_toexam_model = v.findViewById(R.id.lay_toexam_model);
        lay_toexam_office = v.findViewById(R.id.lay_toexam_office);
        lay_toexam_btns = v.findViewById(R.id.lay_toexam_btns);

        FontUtils.boldText(text_toexam_title);
        FontUtils.boldText(text_toexam_msg);


        text_toexam_next.setOnClickListener(this);
        text_toexam_practice.setOnClickListener(this);
        text_toexam_model.setOnClickListener(this);
        text_toexam_office.setOnClickListener(this);

        freshData();

        lay_toexam_practice.setVisibility(View.GONE);
        lay_toexam_model.setVisibility(View.GONE);
        lay_toexam_office.setVisibility(View.GONE);

        super.setContentView(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_toexam_next:
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_VIDEO_START_NEXT));
                hide();
                break;
            case R.id.text_toexam_practice:
                QuestionBankActivity.startPractice(context);
                hide();
                break;
            case R.id.text_toexam_model:
                ModelOffiActivity.startModel(context);
                hide();
                break;
            case R.id.text_toexam_office:
                ModelOffiActivity.startOffi(context);
                hide();
                break;
        }
    }

    public void setData(VideoFinishStatus videoFinishStatus) {
        if (!videoFinishStatus.isShowPractice() && !videoFinishStatus.isShowModel() && !videoFinishStatus.isShowOffi()) {
            lay_toexam_btns.setVisibility(View.GONE);
        } else {
            lay_toexam_practice.setVisibility(videoFinishStatus.isShowPractice() ? View.VISIBLE : View.GONE);
            lay_toexam_model.setVisibility(videoFinishStatus.isShowModel() ? View.VISIBLE : View.GONE);
            lay_toexam_office.setVisibility(videoFinishStatus.isShowOffi() ? View.VISIBLE : View.GONE);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        freshData();
    }

    public void setMsg(String msg) {
        this.msg = msg;
        freshData();
    }

    private void freshData() {
        text_toexam_title.setText(title);
        text_toexam_msg.setText(msg);
        //设置可见性
        text_toexam_title.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        text_toexam_msg.setVisibility(!TextUtils.isEmpty(msg) ? View.VISIBLE : View.GONE);
    }

}
