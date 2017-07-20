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
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.activity.AddressActivity;
import com.ins.aimai.ui.activity.CompDetailActivity;
import com.ins.aimai.ui.activity.EmployAddActivity;
import com.ins.aimai.ui.activity.EmploySearchActivity;
import com.ins.aimai.ui.activity.UserDetailActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLearnComp;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by liaoinstan
 */
public class LearnCompFragment extends BaseFragment implements OnRecycleItemClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private TextView text_toolbar_title;
    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLearnComp adapter;

    public static Fragment newInstance(int position) {
        LearnCompFragment fragment = new LearnCompFragment();
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
        rootView = inflater.inflate(R.layout.fragment_learn_comp, container, false);
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
        text_toolbar_title = (TextView) rootView.findViewById(R.id.text_toolbar_title);
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        rootView.findViewById(R.id.lay_learnpeople_search).setOnClickListener(this);
        text_toolbar_title.setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLearnComp(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getResults().add(new TestBean());
                        adapter.getResults().add(new TestBean());
                        adapter.notifyDataSetChanged();
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        loadingLayout.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.getResults().clear();
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.notifyDataSetChanged();
                loadingLayout.showOut();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        CompDetailActivity.start(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_toolbar_title:
                AddressActivity.start(getContext());
                break;
            case R.id.lay_learnpeople_search:
                EmploySearchActivity.start(getContext());
                break;
        }
    }
}
