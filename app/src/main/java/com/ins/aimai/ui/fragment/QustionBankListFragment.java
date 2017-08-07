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
import com.ins.aimai.bean.ExamPractice;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.ExamActivity;
import com.ins.aimai.ui.activity.ExamResultActivity;
import com.ins.aimai.ui.activity.QuestionAnalysisActivity;
import com.ins.aimai.ui.activity.QuestionBankActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterQustionBankList;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by liaoinstan
 */
public class QustionBankListFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;

    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterQustionBankList adapter;
    private QuestionBankActivity activity;
    private int orderId;
    private String lessonName;

    public static Fragment newInstance(int position) {
        QustionBankListFragment fragment = new QustionBankListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_QUESTIONBANK_NEXT) {
            orderId = (int) event.get("orderId");
            lessonName = (String) event.get("lessonName");
            netQueryPracticeExams(0);
        } else if (event.getEvent() == EventBean.EVENT_EXAM_SUBMITED) {
            netQueryPracticeExams(0);
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
        rootView = inflater.inflate(R.layout.fragment_questionbank_list, container, false);
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
        activity = (QuestionBankActivity) getActivity();
    }

    private void initView() {
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterQustionBankList(getContext(), activity.getType());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(getContext()));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryPracticeExams(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryPracticeExams(1);
            }

            @Override
            public void onLoadmore() {
                netQueryPracticeExams(2);
            }
        });
    }

    private void initData() {
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        ExamPractice examPractice = adapter.getResults().get(viewHolder.getLayoutPosition());
        switch (activity.getType()) {
            case 0:
                if (examPractice.getExaminationNum() == 0) {
                    ToastUtil.showToastShort("该课程还没有练习题");
                }
                else if (examPractice.getIsStudy() == 0) {
                    ToastUtil.showToastShort("您还没有学完该课程");
                }
                else if (examPractice.getIsExamination() == 1) {
                    //练习题已经做完
                    ExamResultActivity.start(getActivity(), examPractice.getPaperId(), examPractice.getOrderId(), activity.getType());
                } else {
                    ExamActivity.startPractice(getActivity(), examPractice);
                }
                break;
            case 1:
                if (examPractice.getExaminationNum() == 0) {
                    ToastUtil.showToastShort("该课程没有错题");
                }
                //错题库
                QuestionAnalysisActivity.startErrorWithCate(getActivity(), orderId, examPractice.getCourseWareId(), lessonName, examPractice.getCourseWareName());
                break;
        }
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private int page;
    private final int PAGE_COUNT = 10;

    /**
     * type:0 首次加载 1:下拉刷新 2:上拉加载
     *
     * @param type
     */
    private void netQueryPracticeExams(final int type) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .put("orderId", orderId)
                .build();
        if (type == 0) loadingLayout.showLoadingView();
        Call<ResponseBody> call;
        if (activity.getType() == 0) {
            //练习题库
            call = NetApi.NI().queryPracticeExams(param);
        } else {
            //错题库
            call = NetApi.NI().queryErrorWare(param);
        }
        call.enqueue(new BaseCallback<List<ExamPractice>>(new TypeToken<List<ExamPractice>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<ExamPractice> beans, String msg) {
                if (!StrUtil.isEmpty(beans)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        adapter.getResults().clear();
                        page = 1;
                    } else {
                        page++;
                    }
                    adapter.getResults().addAll(beans);
                    adapter.notifyDataSetChanged();

                    //加载结束恢复列表
                    if (type == 0) {
                        loadingLayout.showOut();
                    } else {
                        springView.onFinishFreshAndLoad();
                    }
                } else {
                    //没有数据设置空数据页面，下拉加载不用，仅提示
                    if (type == 0 || type == 1) {
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
}
