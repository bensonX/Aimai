package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.Msg;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.VideoFinishStatus;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetFaceHelper;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.net.helper.NetUploadHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterMsg;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.fragment.RegistInfoFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.MD5Util;
import com.ins.common.utils.PermissionsUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;

public class FaceResordActivity extends BaseAppCompatActivity implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelperEx cropHelperEx;

    private View lay_facerecord_business;
    private View lay_facerecord_address;
    private View lay_facerecord_header;
    private View lay_facerecord_yyzz;
    private View lay_facerecord_jsx;
    private TextView text_facerecord_business;
    private TextView text_facerecord_address;
    private ImageView img_facerecord_header;
    private ImageView img_facerecord_yyzz;
    private ImageView img_facerecord_jsx;
    private TextView text_facerecord_note;

    //注册类型
    private int type;
    //记录选择的图片资源类型：1:营业执照，2:单位介绍信
    private int typeImg;
    //用户实体
    private User user;
    //人像采集ID
    private String faceId;
    //图片资源路径
    private String path;
    //选择行业
    private Trade trade;
    //选择地址
    private Address address;

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, FaceResordActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_CAMERA_RESULT) {
            //人脸识别相机回调
            path = (String) event.get("path");
            GlideUtil.loadCircleImg(img_facerecord_header, R.drawable.default_header_edit, path);
            checkFace(path);
        } else if (event.getEvent() == EventBean.EVENT_SELECT_TRADE) {
            trade = (Trade) event.get("trade");
            setTradeData(trade);
            ToastUtil.showToastShortDebug(trade.getTradeName());
        } else if (event.getEvent() == EventBean.EVENT_SELECT_ADDRESS) {
            address = (Address) event.get("address");
            setAddressData(address);
            ToastUtil.showToastShortDebug(address.getAddress());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facerecord);
        setToolbar();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        }
        cropHelperEx = new CropHelperEx(this, this);
    }

    private void initView() {
        lay_facerecord_business = findViewById(R.id.lay_facerecord_business);
        lay_facerecord_address = findViewById(R.id.lay_facerecord_address);
        lay_facerecord_header = findViewById(R.id.lay_facerecord_header);
        lay_facerecord_yyzz = findViewById(R.id.lay_facerecord_yyzz);
        lay_facerecord_jsx = findViewById(R.id.lay_facerecord_jsx);
        text_facerecord_business = (TextView) findViewById(R.id.text_facerecord_business);
        text_facerecord_address = (TextView) findViewById(R.id.text_facerecord_address);
        img_facerecord_header = (ImageView) findViewById(R.id.img_facerecord_header);
        img_facerecord_yyzz = (ImageView) findViewById(R.id.img_facerecord_yyzz);
        img_facerecord_jsx = (ImageView) findViewById(R.id.img_facerecord_jsx);
        text_facerecord_note = (TextView) findViewById(R.id.text_facerecord_note);
        lay_facerecord_business.setOnClickListener(this);
        lay_facerecord_address.setOnClickListener(this);
        lay_facerecord_header.setOnClickListener(this);
        lay_facerecord_yyzz.setOnClickListener(this);
        lay_facerecord_jsx.setOnClickListener(this);
        findViewById(R.id.btn_go).setOnClickListener(this);
        //设置隐藏项
        if (user.isUser()) {
            lay_facerecord_header.setVisibility(View.VISIBLE);
            lay_facerecord_business.setVisibility(View.GONE);
            lay_facerecord_yyzz.setVisibility(View.GONE);
            lay_facerecord_jsx.setVisibility(View.GONE);
            text_facerecord_note.setVisibility(View.VISIBLE);
        } else if (user.isCompUser()) {
            lay_facerecord_header.setVisibility(View.GONE);
            lay_facerecord_business.setVisibility(View.VISIBLE);
            lay_facerecord_yyzz.setVisibility(View.VISIBLE);
            lay_facerecord_jsx.setVisibility(View.GONE);
            text_facerecord_note.setVisibility(View.GONE);
        } else if (user.isGovUser()) {
            lay_facerecord_header.setVisibility(View.GONE);
            lay_facerecord_business.setVisibility(View.GONE);
            lay_facerecord_yyzz.setVisibility(View.GONE);
            lay_facerecord_jsx.setVisibility(View.VISIBLE);
            text_facerecord_note.setVisibility(View.GONE);
        }
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    private void setAddressData(Address address) {
        text_facerecord_address.setText(address.getMergerName());
    }

    private void setTradeData(Trade trade) {
        text_facerecord_business.setText(trade.getTradeName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_facerecord_business:
                TradeActivity.start(this);
                break;
            case R.id.lay_facerecord_address:
                if (AppHelper.isGov(user)) {
                    AddressActivity.startWithAll(this);
                } else {
                    AddressActivity.start(this);
                }
                break;
            case R.id.lay_facerecord_header:
                if (PermissionsUtil.checkCamera(this)) {
                    Intent intent = new Intent(this, CameraActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lay_facerecord_yyzz:
                if (PermissionsUtil.checkCamera(this)) {
                    cropHelperEx.showDefaultDialog();
                    typeImg = 1;
                }
                break;
            case R.id.lay_facerecord_jsx:
                if (PermissionsUtil.checkCamera(this)) {
                    cropHelperEx.showDefaultDialog();
                    typeImg = 2;
                }
                break;
            case R.id.btn_go:
                String msg = AppVali.faceRecord(user, faceId, path, trade, address);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    NetUploadHelper.newInstance().netUpload(path, new NetUploadHelper.UploadCallback() {
                        @Override
                        public void uploadfinish(String url) {
                            netCommit(url);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelperEx.onActivityResult(requestCode, resultCode, data);
    }

    private void checkFace(final String path) {
        AppHelper.showLoadingDialog(this);
        NetFaceHelper.getInstance().initCheck(path).netEyeCheck(new NetFaceHelper.OnFaceCheckCallback() {
            @Override
            public void onFaceCheckSuccess(String faceId) {
                ToastUtil.showToastShort("人像采集成功");
                FaceResordActivity.this.faceId = faceId;
                AppHelper.hideLoadingDialog(FaceResordActivity.this);
            }

            @Override
            public void onFaceCheckFailed(String msg) {
                FaceResordActivity.this.faceId = "";
                AppHelper.hideLoadingDialog(FaceResordActivity.this);
            }
        });
    }

    private void netCommit(final String imgurl) {
        NetParam netParam = new NetParam();
        netParam.put("cityId", address.getId());
        netParam.put("deviceToken", JPushInterface.getRegistrationID(this));
        if (user.isUser()) {
            netParam.put("veriFaceImages", imgurl);
            netParam.put("faceId", faceId);
            netParam.put("roleId", User.USER);
        } else if (user.isCompUser()) {
            netParam.put("licenseFile", imgurl);
            netParam.put("tradeId", trade.getId());
            netParam.put("roleId", User.COMPANY_USER);
        } else if (user.isGovUser()) {
            netParam.put("licenseFile", imgurl);
            netParam.put("roleId", User.GOVERNMENT_USER);
        }
        Map<String, Object> param = netParam.build();
        NetApi.NI().updateUserWithToken(user.getToken(), param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                ToastUtil.showToastShort(msg);
                user.setCityId(address.getId());
                if (user.isUser()) {
                    user.setFaceId(faceId);
                } else if (user.isCompUser()) {
                    user.setTradeId(trade.getId());
                } else if (user.isGovUser()) {
                }
                AppData.App.saveUser(user);
                AppData.App.saveToken(user.getToken());
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGIN));
                hideLoadingDialog();
                HomeActivity.start(FaceResordActivity.this);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }

    @Override
    public void cropResult(String path) {
        switch (typeImg) {
            case 1:
                GlideUtil.loadImg(img_facerecord_yyzz, R.drawable.default_bk_img, path);
                break;
            case 2:
                GlideUtil.loadImg(img_facerecord_jsx, R.drawable.default_bk_img, path);
                break;
        }
        this.path = path;
    }

    @Override
    public void cancel() {

    }
}
