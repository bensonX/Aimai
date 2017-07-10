package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterCountDetail;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeBanner;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment {

    private int position;
    private View rootView;

    private RecyclerView recycler;
    private DelegateAdapter delegateAdapter;
    private RecycleAdapterHomeBanner adapterBanner;
    private RecycleAdapterHomeInfo adapterInfo;

    private RecycleAdapterCountDetail adapterCount;

    public static Fragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
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
    }

    private void initView() {
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
//        VirtualLayoutManager manager = new VirtualLayoutManager(getContext());
//        recycler.setLayoutManager(manager);
//        delegateAdapter = new DelegateAdapter(manager, true);
//        recycler.setAdapter(delegateAdapter);
//        delegateAdapter.addAdapter(adapterBanner = new RecycleAdapterHomeBanner(getContext(), new LinearLayoutHelper()));
//        delegateAdapter.addAdapter(adapterInfo = new RecycleAdapterHomeInfo(getContext(), new LinearLayoutHelper()));

        adapterCount = new RecycleAdapterCountDetail(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapterCount);
    }

    private void initData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final List<Image> images = new ArrayList<>();
//                images.add(new Image("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=314457078,4213404302&fm=23&gp=0.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788965881&di=8790d2ab2e215cba5f2249ffa1500ad6&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb8389b504fc2d56269897df7e51190ef76c66c23.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788299631&di=d2044f56d47c1b8430ddd6cef72db044&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b24e5812e721a84a0d304f57795c.jpg%40900w_1l_2o_100sh.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788511720&di=4b0540ca79d584cb23da71399b47c164&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fgroup1%2FM00%2FB7%2F55%2FoYYBAFdH-ZaATQgNAAES3V-1dF8546.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788525855&di=99884ff0c24800f13cbcd7153620c25a&imgtype=0&src=http%3A%2F%2Fs0.hao123img.com%2Fres%2Fimg%2Fmoe%2F5_ydbanner4.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788538381&di=ded035ef63ed58c8502db8e051ff1ed8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01933f5812e721a84a0e282b370613.jpg%40900w_1l_2o_100sh.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788558151&di=9414a2dc93bfa1bb0e5cb83dfc12dacf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ff275812e720a84a0d304f86d193.jpg%40900w_1l_2o_100sh.jpg"));
//                images.add(new Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1282847967,3415056230&fm=23&gp=0.jpg"));
//                images.add(new Image("https://ss0.bdstatic.com/xxxx.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494383324&di=3c6442f78b6842fa5aa8cf4853bdefd6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010be257d9163b0000012e7e273943.jpg%40900w_1l_2o_100sh.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788616559&di=32a058f75708345d289e9ca95c3dccc4&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fpromote%2F99e5c45594ab4ce16757ca42566c3276.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788634301&di=d2db6ff0239325e7ac3aafbed6ece216&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fgroup1%2FM00%2FB7%2F2D%2FoYYBAFchw5-AHBQTAAEaPlUX-kg177.jpg"));
//                adapterBanner.getResults().clear();
//                adapterBanner.getResults().addAll(images);
//                adapterBanner.notifyDataSetChanged();
//
//                adapterInfo.getResults().clear();
//                adapterInfo.getResults().addAll(getInitResults("info"));
//                adapterInfo.notifyDataSetChanged();
//            }
//        },1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<Image> images = new ArrayList<>();
                images.add(new Image("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=314457078,4213404302&fm=23&gp=0.jpg"));
//                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788965881&di=8790d2ab2e215cba5f2249ffa1500ad6&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb8389b504fc2d56269897df7e51190ef76c66c23.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788299631&di=d2044f56d47c1b8430ddd6cef72db044&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b24e5812e721a84a0d304f57795c.jpg%40900w_1l_2o_100sh.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788511720&di=4b0540ca79d584cb23da71399b47c164&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fgroup1%2FM00%2FB7%2F55%2FoYYBAFdH-ZaATQgNAAES3V-1dF8546.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788525855&di=99884ff0c24800f13cbcd7153620c25a&imgtype=0&src=http%3A%2F%2Fs0.hao123img.com%2Fres%2Fimg%2Fmoe%2F5_ydbanner4.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788538381&di=ded035ef63ed58c8502db8e051ff1ed8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01933f5812e721a84a0e282b370613.jpg%40900w_1l_2o_100sh.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788558151&di=9414a2dc93bfa1bb0e5cb83dfc12dacf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ff275812e720a84a0d304f86d193.jpg%40900w_1l_2o_100sh.jpg"));
                images.add(new Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1282847967,3415056230&fm=23&gp=0.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494383324&di=3c6442f78b6842fa5aa8cf4853bdefd6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010be257d9163b0000012e7e273943.jpg%40900w_1l_2o_100sh.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788616559&di=32a058f75708345d289e9ca95c3dccc4&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fpromote%2F99e5c45594ab4ce16757ca42566c3276.jpg"));
                images.add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493788634301&di=d2db6ff0239325e7ac3aafbed6ece216&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fgroup1%2FM00%2FB7%2F2D%2FoYYBAFchw5-AHBQTAAEaPlUX-kg177.jpg"));
                adapterCount.getResultsBanner().clear();
                adapterCount.getResultsBanner().addAll(images);

                adapterCount.getResults().clear();
                adapterCount.getResults().addAll(getInitResults("xxx"));

                adapterCount.notifyDataSetChanged();
            }
        }, 1000);
    }

    private List<TestBean> getInitResults(String name) {
        ArrayList<TestBean> results = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            results.add(new TestBean(name + i));
        }
        return results;
    }
}
