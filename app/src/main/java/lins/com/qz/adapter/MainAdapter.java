package lins.com.qz.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.R;
import lins.com.qz.bean.AddAdrMsg;


/**
 * Created by LINS on 2017/5/2.
 */

public class MainAdapter extends RecyclerArrayAdapter<AddAdrMsg>{
    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(parent);
    }
    class MainHolder extends BaseViewHolder<AddAdrMsg>{

        private TextView title,word;
        public MainHolder(ViewGroup parent) {
            super(parent, R.layout.item_main);
            title = $(R.id.tv_main_title);
            word = $(R.id.tv_main_word);
        }

        @Override
        public void setData(AddAdrMsg data) {
            super.setData(data);
            title.setText(data.getTitle());
            word.setText(data.getAddr());

//            Glide.with(getContext())
//                    .load(data.getPic())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .centerCrop()
//                    .into(imageView);

        }
    }
}
