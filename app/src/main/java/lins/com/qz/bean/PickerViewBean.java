package lins.com.qz.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Hmei on 17/1/9.
 */

public class PickerViewBean implements IPickerViewData {
    private String str;

    public PickerViewBean(String str) {
        this.str = str;
    }

    @Override
    public String getPickerViewText() {
        return str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
