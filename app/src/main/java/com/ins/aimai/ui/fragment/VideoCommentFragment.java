package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Comment;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterVideoCommet;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.helper.SwipeHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class VideoCommentFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private int lessonId;
    private View rootView;

    private ImageView img_comment_headerme;
    private View lay_comment_send;
    private EditText edit_comment_detail;
    private View btn_comment_commit;

    private SwipeRefreshLayout swip;
    private RecyclerView recycler;
    private RecycleAdapterVideoCommet adapter;

    public static Fragment newInstance(int position, int lessonId) {
        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("lessonId", lessonId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_VIDEO_TEXISIZE) {
            int sizeType = (int) event.get("sizeType");
            setTextSize(sizeType);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
        this.lessonId = getArguments().getInt("lessonId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_videocomment, container, false);
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
        lay_comment_send = rootView.findViewById(R.id.lay_comment_send);
        edit_comment_detail = (EditText) rootView.findViewById(R.id.edit_comment_detail);
        swip = (SwipeRefreshLayout) rootView.findViewById(R.id.swip);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        img_comment_headerme = (ImageView) rootView.findViewById(R.id.img_comment_headerme);
        btn_comment_commit = rootView.findViewById(R.id.btn_comment_commit);
        btn_comment_commit.setOnClickListener(this);

        if (getActivity() instanceof VideoActivity) {
            if (AppData.App.getUser() == null) {
                lay_comment_send.setVisibility(View.GONE);
            } else {
                lay_comment_send.setVisibility(View.VISIBLE);
            }
        } else {
            lay_comment_send.setVisibility(View.GONE);
        }
        btn_comment_commit.setEnabled(false);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterVideoCommet(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(getContext(), ItemDecorationDivider.VERTICAL_LIST));
        recycler.setAdapter(adapter);
        SwipeHelper.setSwipeListener(swip, recycler, new SwipeHelper.OnSwiperFreshListener() {
            @Override
            public void onRefresh() {
                netQueryComments(1);
            }

            @Override
            public void onLoadmore() {
                netQueryComments(2);
            }
        });
        //加载头像
        User user = AppData.App.getUser();
        if (user != null) {
            GlideUtil.loadCircleImg(img_comment_headerme, R.drawable.default_header_edit, user.getAvatar());
        }
        edit_comment_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (AppVali.content(s.toString()) != null) {
                    btn_comment_commit.setEnabled(false);
                } else {
                    btn_comment_commit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
        netQueryComments(1);
    }

    private void setTextSize(int sizeType) {
        adapter.setTextSize(sizeType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_commit:
                String content = edit_comment_detail.getText().toString();
                netCommitComnet(content);
                break;
        }
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private int page;
    private final int PAGE_COUNT = 10;

    /**
     * type: 1:下拉刷新 2:上拉加载
     *
     * @param type
     */
    private void netQueryComments(final int type) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .put("curriculumId", lessonId)
                .build();
        NetApi.NI().queryComments(param).enqueue(new BaseCallback<List<Comment>>(new TypeToken<List<Comment>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Comment> beans, String msg) {
                if (!StrUtil.isEmpty(beans)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 1) {
                        adapter.getResults().clear();
                        page = 1;
                    } else {
                        page++;
                    }
                    adapter.getResults().addAll(beans);
                    adapter.notifyDataSetChanged();

                    swip.setRefreshing(false);
                } else {
                    //这里不提示了
                    //ToastUtil.showToastShort("没有更多的评论了");
                    swip.setRefreshing(false);
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                swip.setRefreshing(false);
            }
        });
    }

    private void netCommitComnet(String content) {
        Map<String, Object> param = new NetParam()
                .put("curriculumId", lessonId)
                .put("content", content)
                .build();
        AppHelper.showLoadingDialog(getActivity());
        NetApi.NI().addComment(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                edit_comment_detail.setText("");
                AppHelper.hideLoadingDialog(getActivity());
                initData();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                AppHelper.hideLoadingDialog(getActivity());
            }
        });
    }
}
