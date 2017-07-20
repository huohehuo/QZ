package lins.com.qz.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import org.apache.http.HttpHost;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lins.com.qz.Config;
import lins.com.qz.thirdparty.downfile.asihttp.AsyncHttpClient;
import lins.com.qz.thirdparty.downfile.asihttp.AsyncHttpResponseHandler;
import lins.com.qz.thirdparty.downfile.asihttp.RequestParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LINS on 2017/7/18.
 */

public class UpdataUtil {


    public static void getGitHubJson(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        HttpHost hcProxyHost = null;
        AsyncHttpClient client = new AsyncHttpClient(hcProxyHost);
        client.get(url, params, responseHandler);
    }

    private static UpdataUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static UpdataUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new UpdataUtil();
        }
        return downloadUtil;
    }

    private UpdataUtil() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * @param url      下载连接
     * @param listener 下载监听
     */
    public void download(final String url, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[1024];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir() throws IOException {
        // 下载位置
        File downloadFile = new File(Config.PATH_APK);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}

