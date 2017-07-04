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
import lins.com.qz.bean.PlanComment;


/**
 * Created by LINS on 2017/5/2.
 */

public class CommentAdapter extends RecyclerArrayAdapter<PlanComment>{
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(parent);
    }
    class MainHolder extends BaseViewHolder<PlanComment>{

        private TextView comment;
        public MainHolder(ViewGroup parent) {
            super(parent, R.layout.item_comment);
            comment = $(R.id.tv_comment);
        }

        @Override
        public void setData(PlanComment data) {
            super.setData(data);
            comment.setText(data.getContent());
//            num.setText(data.getFavour().get__op());
            
            comment.setOnClickListener(new View.OnClickListener() {
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
