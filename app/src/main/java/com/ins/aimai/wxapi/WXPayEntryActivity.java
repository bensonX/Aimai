package com.ins.aimai.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.PayHelperEx;
import com.ins.aimai.ui.activity.HomeActivity;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.L;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseAppCompatActivity implements IWXAPIEventHandler, View.OnClickListener {

    private int type;
    private TextView text_payresult_title;
    private TextView text_payresult_content;
    private TextView btn_payresult_left;
    private TextView btn_payresult_right;

    public static void start(Context context) {
        Intent intent = new Intent(context, WXPayEntryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("type")) {
            type = intent.getIntExtra("type", -1);
        }
        setPayData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payresult);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", -1);
        }
    }

    private void initView() {
        text_payresult_title = (TextView) findViewById(R.id.text_payresult_title);
        text_payresult_content = (TextView) findViewById(R.id.text_payresult_content);
        btn_payresult_left = (TextView) findViewById(R.id.btn_payresult_left);
        btn_payresult_right = (TextView) findViewById(R.id.btn_payresult_right);
        btn_payresult_left.setOnClickListener(this);
        btn_payresult_right.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
        setPayData();
    }

    private void setPayData() {
        EventBus.getDefault().post(new EventBean(EventBean.EVENT_PAYRESULT));
        switch (type) {
            case 0:
                text_payresult_title.setText("支付成功");
                text_payresult_title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pay_seccess, 0, 0);
                text_payresult_content.setText("您可以在我的课程里面观看课程");
                btn_payresult_right.setText("去我的课程");
                break;
            case -1:
                text_payresult_title.setText("支付失败");
                text_payresult_title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pay_fail, 0, 0);
                text_payresult_content.setText("订单已提交，您可以在我的订单里面重新支付");
                btn_payresult_right.setText("重新支付");
                break;
            case -2:
                text_payresult_title.setText("支付已取消");
                text_payresult_title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pay_fail, 0, 0);
                text_payresult_content.setText("支付已取消，您可以在我的订单里面重新支付");
                btn_payresult_right.setText("重新支付");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_payresult_left:
                //返回首页
                HomeActivity.start(this);
                break;
            case R.id.btn_payresult_right:
                if (type == 0) {
                    //去我的课程
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_HOME_TAB_LESSON));
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_FRESH_LERNLESSON));
                    HomeActivity.start(this);
                } else {
                    //重新支付
                    PayDialogActivity.startRepay(this, PayHelperEx.orderId);
                }
                break;
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        L.d("onPayFinish, errCode = " + resp.errCode);
        L.d("onPayFinish, errStr = " + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            type = resp.errCode;
            setPayData();
            switch (resp.errCode) {
                case 0:
                    //成功
                    break;
                case -1:
                    //失败
                    break;
                case -2:
                    //用户取消
                    break;
                case -3:
                    //发送失败
                    ToastUtil.showToastShort("微信支付：发送失败");
                    break;
                case -4:
                    //授权失败
                    ToastUtil.showToastShort("微信支付：授权失败");
                    break;
                case -5:
                    //微信不支持
                    ToastUtil.showToastShort("微信支付：微信版本不支持");
                    break;
                case -6:
                    //BAN
                    ToastUtil.showToastShort("微信支付：BAN");
                    break;
            }
        }
    }
}
