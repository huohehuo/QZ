package lins.com.qz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.bean.PlanBean;


/**
 * Created by LINS on 2017/5/2.
 */

public class MainAdapter extends RecyclerArrayAdapter<PlanBean>{
    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(parent);
    }
    class MainHolder extends BaseViewHolder<PlanBean>{

        private TextView time;
        private TextView eesay;
        private ImageView favour;
        private TextView num;
        public MainHolder(ViewGroup parent) {
            super(parent, R.layout.item_plan);
            time = $(R.id.tv_time);
            eesay = $(R.id.tv_main_essay);
            num = $(R.id.tv_favour);
            favour = $(R.id.iv_favour);
        }

        @Override
        public void setData(PlanBean data) {
            super.setData(data);
            eesay.setText(data.getEssay());
            time.setText(data.getCreatedAt());
//            num.setText(data.getFavour().get__op());
            
            favour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
                }
            });

//            Glide.with(getContext())
//                    .load(data.getPic())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .centerCrop()
//                    .into(imageView);

        }
    }
}
