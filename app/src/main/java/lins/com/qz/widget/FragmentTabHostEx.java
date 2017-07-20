package lins.com.qz.widget;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;


/**
 * Created by aid on 9/6/16.
 */
public class FragmentTabHostEx extends FragmentTabHost {

    private String mCurrentTag;
    private String mNoTabChangedTag;


    public FragmentTabHostEx(Context context) {
        super(context);
    }

    public FragmentTabHostEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals(mNoTabChangedTag)) {
            setCurrentTabByTag(mCurrentTag);
        } else {
            super.onTabChanged(tabId);
            mCurrentTag = tabId;
        }
    }

    public void setNoTabChangedTag(String tag) {
        this.mNoTabChangedTag = tag;
    }

}
