package lins.com.qz.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import lins.com.qz.utils.piccut.CropImageActivity;


/**
 * Created by sky on 2017/2/7.
 */

public class SelectPicUtil {
    public static void showSelectPhoto(final Activity activity, final int reusetCode, final String path, final SelectPhotoDialog selectPhotoDialog){

        selectPhotoDialog.setDialogCallBack(new SelectPhotoDialog.SelectPhoteDialogCallBack() {
            @Override
            public void OnclickLiseten(int id) {
                switch (id){
                    case SelectPhotoDialog.BN_TAKEPHOTO:
                        CropImageActivity.startActivity(activity, path, true, reusetCode);
                        selectPhotoDialog.hide();
                        break;
                    case SelectPhotoDialog.BN_SELECTPHOTO:
                        CropImageActivity.startActivity(activity, path, false, reusetCode);
                        selectPhotoDialog.hide();
                        break;
                    case SelectPhotoDialog.BN_CANCEL:
                        selectPhotoDialog.hide();
                        break;
                }
            }
        });
        selectPhotoDialog.show();
    }

    public static void fShowSelectPhoto(final Fragment fragment, final int resultCode, final String path, final SelectPhotoDialog selectPhotoDialog){
        selectPhotoDialog.setDialogCallBack(new SelectPhotoDialog.SelectPhoteDialogCallBack() {
            @Override
            public void OnclickLiseten(int id) {
                switch (id){
                    case SelectPhotoDialog.BN_TAKEPHOTO:
                        CropImageActivity.startActivity(fragment, path, true, resultCode);
                        break;
                    case SelectPhotoDialog.BN_SELECTPHOTO:
                        CropImageActivity.startActivity(fragment, path, false, resultCode);
                        break;
                    case SelectPhotoDialog.BN_CANCEL:
                        selectPhotoDialog.hide();
                        break;
                }
            }
        });
        selectPhotoDialog.show();
    }
}
