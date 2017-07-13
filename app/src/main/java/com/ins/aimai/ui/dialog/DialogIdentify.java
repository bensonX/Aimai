package com.ins.aimai.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ins.aimai.R;

/**
 * liaoinstan
 * 注册身份选择弹窗
 */
public class DialogIdentify extends Dialog implements View.OnClickListener {
    private Context context;

    public DialogIdentify(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_identify, null);
        mView.findViewById(R.id.btn_identify_person).setOnClickListener(this);
        mView.findViewById(R.id.btn_identify_comp).setOnClickListener(this);
        mView.findViewById(R.id.btn_identify_gov).setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        super.setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
//        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_identify_person:
                if (onIdentifyListener != null) onIdentifyListener.onPersonClick(v);
                hide();
                break;
            case R.id.btn_identify_comp:
                if (onIdentifyListener != null) onIdentifyListener.onCompClick(v);
                hide();
                break;
            case R.id.btn_identify_gov:
                if (onIdentifyListener != null) onIdentifyListener.onGovClick(v);
                hide();
                break;
        }
    }

    private OnIdentifyListener onIdentifyListener;

    public void setOnIdentifyListener(OnIdentifyListener onIdentifyListener) {
        this.onIdentifyListener = onIdentifyListener;
    }

    public interface OnIdentifyListener {
        void onPersonClick(View v);

        void onCompClick(View v);

        void onGovClick(View v);
    }
}
