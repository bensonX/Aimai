package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.ExamModelOffi;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.ExamCountDownTimer;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.RecycleAdapterModelOffi;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ClearCacheUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

//type 1:模拟开始 2：正式考试
public class ModelOffiActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterModelOffi adapter;


    private int type;

    public static void startModel(Context context) {
        start(context, 1);
    }

    public static void startOffi(Context context) {
        start(context, 2);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ModelOffiActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EXAM_SUBMITED) {
            netQueryModelPapers(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 1);
        }
        setToolbar(type == 1 ? "模拟题库" : "正式考试");
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterModelOffi(this, type);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryModelPapers(0);
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryModelPapers(1);
            }

            @Override
            public void onLoadmore() {
                netQueryModelPapers(2);
            }
        });
    }

    private void initData() {
        netQueryModelPapers(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        final ExamModelOffi exam = adapter.getResults().get(viewHolder.getLayoutPosition());
        switch (type) {
            //模拟考试
            case 1:
                if (exam.getExaminationNum() == 0) {
                    ToastUtil.showToastShort("该课程还没有模拟题");
                    return;
                } else if (exam.getIsFinish() == 1) {
                    //已模拟
                    ExamResultActivity.start(this, exam.getPaperId(), exam.getOrderId(), type);
                    return;
                } else {
                    ExamActivity.startMoldel(this, exam);
                    break;
                }
                //正式考试
            case 2:
                if (exam.getExaminationNum() == 0) {
                    ToastUtil.showToastShort("该课程还没有考试题");
                    return;
                } else if (exam.getPassNum() > 0) {
                    //已通过
                    ExamResultActivity.start(this, exam.getPaperId(), exam.getOrderId(), type);
                    return;
                } else if (exam.getUnPassNum() > 2) {
                    //已挂科
                    ToastUtil.showToastShort("您已经挂科");
                    ExamResultActivity.start(this, exam.getPaperId(), exam.getOrderId(), type);
                    return;
                } else {
                    DialogSure.showDialog(this, "您确定开始正式考试吗，中途退出或有意外情况均视为交卷，请准备好再进行！", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                            ExamActivity.startOfficial(ModelOffiActivity.this, exam);
                        }
                    });
                    break;
                }
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
    private void netQueryModelPapers(final int type) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .build();
        if (type == 0) loadingLayout.showLoadingView();
        Call<ResponseBody> call;
        if (this.type == 1) {
            //模拟考试
            call = NetApi.NI().queryModelPapers(param);
        } else {
            //正式考试
            call = NetApi.NI().queryOffiPapers(param);
        }
        call.enqueue(new BaseCallback<List<ExamModelOffi>>(new TypeToken<List<ExamModelOffi>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<ExamModelOffi> exams, String msg) {
                if (!StrUtil.isEmpty(exams)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        adapter.getResults().clear();
                        page = 1;
                    } else {
                        page++;
                    }
                    adapter.getResults().addAll(exams);
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
