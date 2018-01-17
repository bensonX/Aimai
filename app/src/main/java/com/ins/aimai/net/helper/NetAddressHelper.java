package com.ins.aimai.net.helper;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StrUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetAddressHelper {
    private static NetAddressHelper instance = null;

    private NetAddressHelper() {
    }

    public static synchronized NetAddressHelper getInstance() {
        if (instance == null) {
            instance = new NetAddressHelper();
        }
        return instance;
    }

    public void netGetAddress(int id, int levelType, final AddressCallback callback) {
        NetParam netParam = new NetParam();
        netParam.put("levelType", levelType);
        if (id != 0) netParam.put("cityId", id);
        Map<String, Object> param = netParam.build();
        NetApi.NI().queryCity(param).enqueue(new BaseCallback<List<Address>>(new TypeToken<List<Address>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Address> addressList, String msg) {
                if (!StrUtil.isEmpty(addressList)) {
                    if (callback != null) callback.onSuccess(addressList);
                } else {
                    if (callback != null) callback.onError("没有数据");
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(msg);
            }
        });
    }

    public interface AddressCallback {
        void onSuccess(List<Address> addressList);

        void onError(String msg);
    }
}
