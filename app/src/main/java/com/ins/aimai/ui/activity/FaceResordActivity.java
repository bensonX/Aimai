package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Msg;
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
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.PermissionsUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class FaceResordActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private View lay_facerecord_header;
    private ImageView img_facerecord_header;

    private User user;
    private String faceId;
    private String path;

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
    }

    private void initView() {
        lay_facerecord_header = findViewById(R.id.lay_facerecord_header);
        img_facerecord_header = (ImageView) findViewById(R.id.img_facerecord_header);
        lay_facerecord_header.setOnClickListener(this);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_facerecord_header:
                if (PermissionsUtil.checkCamera(this)) {
                    Intent intent = new Intent(this, CameraActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_go:
                String msg = AppVali.faceRecord(faceId, path);
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
        Map<String, Object> param = new NetParam()
                .put("faceId", faceId)
                .put("veriFaceImages", imgurl)
                .build();
        showLoadingDialog();
        NetApi.NI().updateUserWithToken(user.getToken(), param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                ToastUtil.showToastShort(msg);
                user.setFaceId(faceId);
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
}
