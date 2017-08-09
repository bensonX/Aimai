package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.LessonHomePojo;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.interfaces.OnLessonClickListener;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.LessonDetailActivity;
import com.ins.aimai.ui.activity.LessonSearchActivity;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonCate;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonTasteBanner;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class LessonFragment extends BaseFragment implements OnLessonClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private DelegateAdapter delegateAdapter;
    private RecycleAdapterLessonTasteBanner adapterTasteBanner;
    private RecycleAdapterLessonCate adapterCate;

    public static Fragment newInstance(int position) {
        LessonFragment fragment = new LessonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_LOGIN:
            case EventBean.EVENT_LOGOUT:
                netQueryLessonHome(false);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lesson, container, false);
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
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        rootView.findViewById(R.id.lay_lesson_search).setOnClickListener(this);
    }

    private void initCtrl() {
        VirtualLayoutManager manager = new VirtualLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        delegateAdapter = new DelegateAdapter(manager, true);
        recycler.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(adapterTasteBanner = new RecycleAdapterLessonTasteBanner(getContext(), new LinearLayoutHelper()));
        delegateAdapter.addAdapter(adapterCate = new RecycleAdapterLessonCate(getContext(), new LinearLayoutHelper()));
        adapterCate.setOnLessonClickListener(this);
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryLessonHome();
            }
        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryLessonHome(false);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToastShort("没有更多数据了");
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
        adapterTasteBanner.setOnItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                Lesson lesson = adapterTasteBanner.getResults().get(viewHolder.getLayoutPosition());
                VideoActivity.start(getContext(), lesson.getId());
            }
        });
    }

    private void initData() {
        netQueryLessonHome();
    }

    @Override
    public void onLessonClick(Lesson lesson) {
        LessonDetailActivity.startByLesson(getContext(), lesson.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_lesson_search:
                LessonSearchActivity.start(getActivity());
                break;
        }
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private void netQueryLessonHome() {
        netQueryLessonHome(true);
    }

    private void netQueryLessonHome(final boolean showLoading) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", 1)
                .put("pageSize", 2)
                .build();
        if (showLoading) loadingLayout.showLoadingView();
        NetApi.NI().queryLessonHome(param).enqueue(new BaseCallback<LessonHomePojo>(LessonHomePojo.class) {
            @Override
            public void onSuccess(int status, LessonHomePojo pojo, String msg) {
                //数据结构转换
                pojo.convert();
                adapterCate.getResults().clear();
                adapterCate.getResults().addAll(pojo.getLessonCates());
                adapterCate.notifyDataSetChanged();
                StrUtil.removeNull(pojo.getFreeLessons());
                adapterTasteBanner.getResults().clear();
                adapterTasteBanner.getResults().addAll(pojo.getFreeLessons());
                adapterTasteBanner.notifyItemChanged(0);
                //FIXME:上面调用了notifyItemChanged，数据已经生成，但是UI并没生效，滚一写recyclerView才展示出刷新后的数据，有待排查优化
                recycler.scrollBy(0, 1);
                springView.onFinishFreshAndLoad();
                if (showLoading) loadingLayout.showOut();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                springView.onFinishFreshAndLoad();
                if (showLoading) loadingLayout.showFailView();
            }
        });
    }
}
