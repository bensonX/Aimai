package com.ins.aimai.net;

import com.ins.aimai.R;
import com.ins.aimai.bean.CommonBean;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.GlideUtil;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/7/21.
 */

public class UploadHelper {

    public static UploadHelper newInstance(){
        return new UploadHelper();
    }

    public void netUpload(String path, final UploadCallback callback) {
        MultipartBody.Part part = new NetParam().buildFileBodyPart(path);
        NetApi.NI().uploadFile(part).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean commonBean, String msg) {
                String url = commonBean.getUrl();
                if (callback != null) callback.uploadfinish(url);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }


    public interface UploadCallback {
        void uploadfinish(String url);
    }
}
