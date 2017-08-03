package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.view.AnswerView;
import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterFavoExam extends RecyclerView.Adapter<RecycleAdapterFavoExam.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterFavoExam(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterFavoExam.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_favo_exam, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterFavoExam.Holder holder, final int position) {
        final TestBean bean = results.get(position);


        String title = "党的十八大以来，一些标志性话语深刻反映了中央治国理政新理念，其中，下列标志性话语与治国理政新理念对应错误是";
        final QuestionView.Option option1 = new QuestionView.Option("“刮骨疗毒，壮士割腕”——加强党风");
        final QuestionView.Option option2 = new QuestionView.Option("“踏石留印，抓铁有痕”--推动国防军队改革");
        final QuestionView.Option option3 = new QuestionView.Option("“凝聚共识，合作共赢”--发展大国外交");
        final QuestionView.Option option4 = new QuestionView.Option("“一个都不能少”——全面建设小康社会");
        List<QuestionView.Option> options = new ArrayList<QuestionView.Option>() {{
            add(option1);
            add(option2);
            add(option3);
            add(option4);
        }};
        QuestionBean questionBean = new QuestionBean(title, options);
        questionBean.setAnswer("B");
        questionBean.setPoint("常识判断、法律、其他法");
        questionBean.setAnalysis("耶和华说，我不再怜恤这地的居民。必将这民交给各人的邻舍，和他们王的手中。他们必毁灭这地，我也不救这民脱离他们的手。于是，我牧养这将宰的群羊，就是群中最困苦的羊。我拿着两根杖。一根我称为荣美，一根我称为联索。这样，我牧养了群羊。");

        holder.answerview.setData(questionBean);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private AnswerView answerview;

        public Holder(View itemView) {
            super(itemView);
            answerview = (AnswerView) itemView.findViewById(R.id.answerview);
        }
    }
}
