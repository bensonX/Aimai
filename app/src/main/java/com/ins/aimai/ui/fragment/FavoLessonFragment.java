package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.activity.LessonDetailActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterFavoLesson;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by liaoinstan
 */
public class FavoLessonFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterFavoLesson adapter;

    private NetListHelper netListHelper;

    public static Fragment newInstance(int position) {
        FavoLessonFragment fragment = new FavoLessonFragment();
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
        rootView = inflater.inflate(R.layout.fragment_favo_lesson, container, false);
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
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterFavoLesson(getContext());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netListHelper.netQueryList(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryList(1);
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryList(2);
            }
        });
        netListHelper = new NetListHelper<Lesson>().init(loadingLayout, springView,
                new TypeToken<List<Lesson>>() {
                }.getType(),
                new NetListHelper.CallHander() {
                    @Override
                    public Call getCall(int type) {
                        Map param = netListHelper.getParam(type);
                        param.put("type", 1);
                        return NetApi.NI().queryCollect(param);
                    }
                },
                new NetListHelper.OnListLoadCallback<Lesson>() {
                    @Override
                    public void onFreshSuccess(int status, List<Lesson> beans, String msg) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoadSuccess(int status, List<Lesson> beans, String msg) {
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initData() {
        netListHelper.netQueryList(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Lesson lesson = adapter.getResults().get(viewHolder.getLayoutPosition());
        if (!lesson.enable()) ToastUtil.showToastShort("课程已停止，无法播放，请观看其他课程");
        LessonDetailActivity.startByLesson(getActivity(), lesson.getId());
    }
}
