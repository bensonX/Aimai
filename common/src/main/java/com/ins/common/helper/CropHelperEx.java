package com.ins.common.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.ins.common.ui.dialog.DialogPopupPhoto;
import com.ins.common.utils.BitmapUtil;
import com.ins.common.utils.FileUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.UriUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by liaoinstan on 2016/1/19.
 * Update 2017/6/19
 * CropHelper的拓展类，增加了默认弹窗的功能，如果弹窗不需要定制可以使用这个
 * 详细请参见基类 {@link CropHelper(Object, CropInterface)}
 */
public class CropHelperEx extends CropHelper {

    private DialogPopupPhoto dialogPopupPhoto;

    public CropHelperEx(Object activityOrfragment, CropInterface cropInterface) {
        super(activityOrfragment, cropInterface);
        dialogPopupPhoto = new DialogPopupPhoto(context);
        dialogPopupPhoto.setOnStartListener(new DialogPopupPhoto.OnStartListener() {
            @Override
            public void onPhoneClick(View v) {
                startPhoto();
            }

            @Override
            public void onCameraClick(View v) {
                startCamera();
            }
        });
    }

    public void showDefaultDialog() {
        dialogPopupPhoto.show();
    }

    public void hideDefaultDialog() {
        dialogPopupPhoto.hide();
    }

    public void dismissDefaultDialog() {
        dialogPopupPhoto.dismiss();
    }
}