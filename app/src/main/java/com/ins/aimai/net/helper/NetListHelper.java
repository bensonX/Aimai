package com.ins.aimai.net.helper;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.bean.Info;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.widget.SpringView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/7/28.
 */

public class NetListHelper<T> {

    private int page;
    private final int PAGE_COUNT = 10;

    private CallHander callHander;
    private LoadingLayout loadingLayout;
    private SpringView springView;
    private OnListLoadCallback callback;
    private Type classType;

    public NetListHelper init(LoadingLayout loadingLayout, SpringView springView, Type classType,CallHander callHander, OnListLoadCallback<T> callback) {
        this.loadingLayout = loadingLayout;
        this.springView = springView;
        this.classType = classType;
        this.callHander = callHander;
        this.callback = callback;
        return this;
    }

    public Map<String, Object> getParam(int type) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .build();
        return param;
    }

    /**
     * type:0 首次加载 1:下拉刷新 2:上拉加载
     *
     * @param type
     */
    public void netQueryList(final int type) {

        if (type == 0) loadingLayout.showLoadingView();
        callHander.getCall(type).enqueue(new BaseCallback<List<T>>(classType) {
            @Override
            public void onSuccess(int status, List<T> beans, String msg) {
                if (!StrUtil.isEmpty(beans)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        page = 1;
                        if (callback != null) callback.onFreshSuccess(status, beans, msg);
                    } else {
                        page++;
                        if (callback != null) callback.onLoadSuccess(status, beans, msg);
                    }

                    //加载结束恢复列表
                    if (type == 0) {
                        loadingLayout.showOut();
                    } else {
                        springView.onFinishFreshAndLoad();
                    }
                } else {
                    //没有数据设置空数据页面，下拉加载不用，仅提示
                    if (type == 0) {
                        loadingLayout.showLackView();
                    } else {
                        springView.onFinishFreshAndLoad();
                        ToastUtil.showToastShort("没有更多的数据了");
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                //首次加载发生异常设置error页面，其余仅提示
                if (type == 0) {
                    loadingLayout.showFailView();
                } else {
                    springView.onFinishFreshAndLoad();
                }
            }
        });
    }

    public interface CallHander {
        Call getCall(int type);
    }

    public interface OnListLoadCallback<T> {
        void onFreshSuccess(int status, List<T> beans, String msg);

        void onLoadSuccess(int status, List<T> beans, String msg);
    }

}
