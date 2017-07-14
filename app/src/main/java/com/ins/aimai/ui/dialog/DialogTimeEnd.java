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
import com.ins.common.utils.FontUtils;


/**
 * 确定取消弹窗
 */
public class DialogTimeEnd extends Dialog {

    private Context context;
    private TextView text_dialog_sure;
    private TextView text_dialog_title;
    private TextView text_go;

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

    public DialogTimeEnd(Context context) {
        this(context, "您的答题时间已到！", "5秒之后我们将自动为您提交答案", "提交并查看");
    }

    public DialogTimeEnd(Context context, String title, String msg, String sureStr) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.title = title;
        this.msg = msg;
        this.sureStr = sureStr;
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
        this.setCanceledOnTouchOutside(false);
    }

    private void setLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_timeend, null);

        text_dialog_sure = (TextView) v.findViewById(R.id.text_dialog_sure);
        text_dialog_title = (TextView) v.findViewById(R.id.text_dialog_title);
        text_go = (TextView) v.findViewById(R.id.text_go);

        FontUtils.boldText(text_dialog_sure);
        FontUtils.boldText(text_dialog_title);


        text_go.setOnClickListener(listener);

        setData();

        super.setContentView(v);
    }

    public void setTitle(String title) {
        this.title = title;
        setData();
    }

    public void setMsg(String msg) {
        this.msg = msg;
        setData();
    }

    private void setData() {
        text_dialog_title.setText(title);
        text_dialog_sure.setText(msg);
        text_go.setText(sureStr);
    }

    public void setOnOkListener(View.OnClickListener listener) {
        text_go.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
