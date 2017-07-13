package com.ins.aimai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.ui.activity.RegistActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.ui.dialog.DialogPopupPhoto;
import com.ins.common.utils.GlideUtil;

/**
 * Created by liaoinstan
 */
public class RegistInfoFragment extends BaseFragment implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelperEx cropHelperEx;

    private ImageView img_regist_header;
    private ImageView img_regist_yyzz;
    private ImageView img_regist_jsx;

    private int position;
    private View rootView;
    private RegistActivity activity;

    //记录选择的图片资源类型：0:个人头像，1:营业执照，2:单位介绍信
    private int typeImg = 0;

    public static Fragment newInstance(int position) {
        RegistInfoFragment fragment = new RegistInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_regist_info, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        activity = (RegistActivity) getActivity();
        cropHelperEx = new CropHelperEx(this, this);
    }

    private void initView() {
        img_regist_header = (ImageView) rootView.findViewById(R.id.img_regist_header);
        img_regist_yyzz = (ImageView) rootView.findViewById(R.id.img_regist_yyzz);
        img_regist_jsx = (ImageView) rootView.findViewById(R.id.img_regist_jsx);
        rootView.findViewById(R.id.lay_regist_header).setOnClickListener(this);
        rootView.findViewById(R.id.lay_regist_yyzz).setOnClickListener(this);
        rootView.findViewById(R.id.lay_regist_jsx).setOnClickListener(this);
        rootView.findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_regist_header:
                cropHelperEx.showDefaultDialog();
                typeImg = 0;
                break;
            case R.id.lay_regist_yyzz:
                cropHelperEx.showDefaultDialog();
                typeImg = 1;
                break;
            case R.id.lay_regist_jsx:
                cropHelperEx.showDefaultDialog();
                typeImg = 2;
                break;
            case R.id.btn_go:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelperEx.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void cropResult(String path) {
        switch (typeImg) {
            case 0:
                GlideUtil.loadCircleImg(img_regist_header, R.drawable.default_header_edit, path);
                break;
            case 1:
                GlideUtil.loadImg(img_regist_yyzz, R.drawable.default_bk_img, path);
                break;
            case 2:
                GlideUtil.loadImg(img_regist_jsx, R.drawable.default_bk_img, path);
                break;
        }
    }

    @Override
    public void cancel() {
    }
}
