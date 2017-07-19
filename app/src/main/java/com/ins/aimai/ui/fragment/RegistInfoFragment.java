package com.ins.aimai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ins.aimai.R;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.AddressActivity;
import com.ins.aimai.ui.activity.CameraActivity;
import com.ins.aimai.ui.activity.RegistActivity;
import com.ins.aimai.ui.activity.TradeActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.MD5Util;

import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liaoinstan
 */
public class RegistInfoFragment extends BaseFragment implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelperEx cropHelperEx;

    private ImageView img_regist_header;
    private ImageView img_regist_yyzz;
    private ImageView img_regist_jsx;

    private View lay_regist_name;
    private View lay_regist_cardnum;
    private View lay_regist_compname;
    private View lay_regist_compnum;
    private View lay_regist_business;
    private View lay_regist_govname;
    private View lay_regist_govnum;
    private View lay_regist_address;
    private View lay_regist_header;
    private View lay_regist_yyzz;
    private View lay_regist_jsx;
    private EditText edit_regist_name;
    private EditText edit_regist_cardnum;
    private EditText edit_regist_compname;
    private EditText edit_regist_compnum;
    private TextView text_regist_business;
    private EditText edit_regist_govname;
    private EditText edit_regist_govnum;
    private TextView text_regist_address;

    private int position;
    private View rootView;
    private RegistActivity activity;

    //记录选择的图片资源类型：1:营业执照，2:单位介绍信
    private int typeImg;
    //图片资源路径
    private String path;
    private int type;

    private static final int RESULT_CAMERA = 0xf101;

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
        type = activity.getType();
        cropHelperEx = new CropHelperEx(this, this);
    }

    private void initView() {
        lay_regist_name = rootView.findViewById(R.id.lay_regist_name);
        lay_regist_cardnum = rootView.findViewById(R.id.lay_regist_cardnum);
        lay_regist_compname = rootView.findViewById(R.id.lay_regist_compname);
        lay_regist_compnum = rootView.findViewById(R.id.lay_regist_compnum);
        lay_regist_business = rootView.findViewById(R.id.lay_regist_business);
        lay_regist_govname = rootView.findViewById(R.id.lay_regist_govname);
        lay_regist_govnum = rootView.findViewById(R.id.lay_regist_govnum);
        lay_regist_address = rootView.findViewById(R.id.lay_regist_address);
        lay_regist_govname = rootView.findViewById(R.id.lay_regist_govname);
        lay_regist_header = rootView.findViewById(R.id.lay_regist_header);
        lay_regist_yyzz = rootView.findViewById(R.id.lay_regist_yyzz);
        lay_regist_jsx = rootView.findViewById(R.id.lay_regist_jsx);

        edit_regist_name = (EditText) rootView.findViewById(R.id.edit_regist_name);
        edit_regist_cardnum = (EditText) rootView.findViewById(R.id.edit_regist_cardnum);
        edit_regist_compname = (EditText) rootView.findViewById(R.id.edit_regist_compname);
        edit_regist_compnum = (EditText) rootView.findViewById(R.id.edit_regist_compnum);
        text_regist_business = (TextView) rootView.findViewById(R.id.text_regist_business);
        edit_regist_govname = (EditText) rootView.findViewById(R.id.edit_regist_govname);
        edit_regist_govnum = (EditText) rootView.findViewById(R.id.edit_regist_govnum);

        text_regist_address = (TextView) rootView.findViewById(R.id.text_regist_address);

        img_regist_header = (ImageView) rootView.findViewById(R.id.img_regist_header);
        img_regist_yyzz = (ImageView) rootView.findViewById(R.id.img_regist_yyzz);
        img_regist_jsx = (ImageView) rootView.findViewById(R.id.img_regist_jsx);
        lay_regist_header.setOnClickListener(this);
        lay_regist_yyzz.setOnClickListener(this);
        lay_regist_jsx.setOnClickListener(this);
        rootView.findViewById(R.id.btn_go).setOnClickListener(this);

        setType();
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    //根据不同注册身份对选项进行显示隐藏
    private void setType() {
        switch (activity.getType()) {
            case 0:
                lay_regist_name.setVisibility(View.VISIBLE);
                lay_regist_cardnum.setVisibility(View.VISIBLE);
                lay_regist_header.setVisibility(View.VISIBLE);
                lay_regist_compname.setVisibility(View.GONE);
                lay_regist_compnum.setVisibility(View.GONE);
                lay_regist_business.setVisibility(View.GONE);
                lay_regist_yyzz.setVisibility(View.GONE);
                lay_regist_govname.setVisibility(View.GONE);
                lay_regist_govnum.setVisibility(View.GONE);
                lay_regist_jsx.setVisibility(View.GONE);
                break;
            case 1:
                lay_regist_name.setVisibility(View.GONE);
                lay_regist_cardnum.setVisibility(View.GONE);
                lay_regist_header.setVisibility(View.GONE);
                lay_regist_compname.setVisibility(View.VISIBLE);
                lay_regist_compnum.setVisibility(View.VISIBLE);
                lay_regist_business.setVisibility(View.VISIBLE);
                lay_regist_yyzz.setVisibility(View.VISIBLE);
                lay_regist_govname.setVisibility(View.GONE);
                lay_regist_govnum.setVisibility(View.GONE);
                lay_regist_jsx.setVisibility(View.GONE);
                break;
            case 2:
                lay_regist_name.setVisibility(View.GONE);
                lay_regist_cardnum.setVisibility(View.GONE);
                lay_regist_header.setVisibility(View.GONE);
                lay_regist_compname.setVisibility(View.GONE);
                lay_regist_compnum.setVisibility(View.GONE);
                lay_regist_business.setVisibility(View.GONE);
                lay_regist_yyzz.setVisibility(View.GONE);
                lay_regist_govname.setVisibility(View.VISIBLE);
                lay_regist_govnum.setVisibility(View.VISIBLE);
                lay_regist_jsx.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_regist_business:
                TradeActivity.start(getContext());
                break;
            case R.id.lay_regist_address:
                AddressActivity.start(getContext());
                break;
            case R.id.lay_regist_header:
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, RESULT_CAMERA);
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
                String phone = activity.getRegister().getPhone();
                String pwd = activity.getRegister().getPwd();

                String u_name = edit_regist_name.getText().toString();
                String u_num = edit_regist_cardnum.getText().toString();
                String c_name = edit_regist_compname.getText().toString();
                String c_num = edit_regist_compnum.getText().toString();
                int c_tradeid = text_regist_business.getTag() != null ? (int) text_regist_business.getTag() : 0;
                c_tradeid = 1;
                String g_name = edit_regist_govname.getText().toString();
                String g_num = edit_regist_govnum.getText().toString();
                int cityid = text_regist_address.getTag() != null ? (int) text_regist_address.getTag() : 0;
                cityid = 1;

                String msg = AppVali.regist_info(type, phone, pwd, path, u_name, u_num, c_name, c_num, c_tradeid, g_name, g_num, cityid);
                if (msg == null) {
                    netResist(type, phone, pwd, path, u_name, u_num, c_name, c_num, c_tradeid, g_name, g_num, cityid);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelperEx.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CAMERA:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra("path");
                    if (!TextUtils.isEmpty(path)) {
                        //保存当前照片路径
                        this.path = path;
                        //打印测试
                        ToastUtil.showToastShortDebug(path);
                        GlideUtil.loadCircleImg(img_regist_header, R.drawable.default_header_edit, path);
                    } else {
                        ToastUtil.showToastShort("图像采集异常");
                    }
                } else {
                    // 失败
                }
                break;
        }
    }

    @Override
    public void cropResult(String path) {
        switch (typeImg) {
            case 1:
                GlideUtil.loadImg(img_regist_yyzz, R.drawable.default_bk_img, path);
                break;
            case 2:
                GlideUtil.loadImg(img_regist_jsx, R.drawable.default_bk_img, path);
                break;
        }
        this.path = path;
    }

    @Override
    public void cancel() {
    }

    private void netResist(int type, String phone, String psw, String path, String u_name, String u_num, String c_name, String c_num, int c_tradeid, String g_name, String g_num, int cityid) {
        NetParam netParam = new NetParam();
        netParam.put("phone", phone);
        netParam.put("pwd", MD5Util.md5(psw));
        netParam.put("cityid", cityid);
        if (type == 0) {
            netParam.put("veriFaceImages", path);
            netParam.put("showName", u_name);
            netParam.put("pid", u_num);
        } else if (type == 1) {
            netParam.put("licenseFile", path);
            netParam.put("showName", c_name);
            netParam.put("pid", c_num);
            netParam.put("tradeId", c_tradeid);
        } else if (type == 2) {
            netParam.put("licenseFile", path);
            netParam.put("showName", g_name);
            netParam.put("pid", g_num);
        }
        Map<String, Object> param = netParam.build();

//        NetApi.NI().register(param).enqueue(new BaseCallback<User>(User.class) {
//            @Override
//            public void onSuccess(int status, User user, String msg) {
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                ToastUtil.showToastShort(msg);
//            }
//        });
    }
}
