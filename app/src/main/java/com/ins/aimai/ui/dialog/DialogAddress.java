package com.ins.aimai.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.VideoFinishStatus;
import com.ins.aimai.ui.activity.ModelOffiActivity;
import com.ins.aimai.ui.activity.QuestionBankActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterAddressDialog;
import com.ins.aimai.ui.adapter.RecycleAdapterFavoLesson;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 选择地址弹窗
 */
public class DialogAddress extends AppCompatDialog implements OnRecycleItemClickListener {

    private Context context;
    private RecyclerView recycler;
    private RecycleAdapterAddressDialog adapter;
    private int level;

    public DialogAddress(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        setLoadingDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /////////获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        /////////设置高宽
        lp.width = (int) (screenWidth * 0.6); // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = (int) (lp.width*0.65); // 高度
        this.setCanceledOnTouchOutside(true);
    }

    private void setLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_address, null);

        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        adapter = new RecycleAdapterAddressDialog(context);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);

        super.setContentView(v);
    }

    public void setData(int level, List<Address> addresses) {
        this.level = level;
        adapter.getResults().clear();
        adapter.getResults().addAll(addresses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Address address = adapter.getResults().get(viewHolder.getLayoutPosition());
        if (onAddressListenner != null) {
            onAddressListenner.onAddressSelect(level, address);
        }
    }

    private OnAddressListenner onAddressListenner;

    public void setOnAddressListenner(OnAddressListenner onAddressListenner) {
        this.onAddressListenner = onAddressListenner;
    }

    public interface OnAddressListenner {
        void onAddressSelect(int level, Address address);
    }
}
