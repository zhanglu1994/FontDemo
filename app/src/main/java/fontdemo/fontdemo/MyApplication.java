package fontdemo.fontdemo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by zhangl on 2017/4/13.
 * <p>
 * 作用：
 */

public class MyApplication extends Application {


    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();


        instance = this;
        initTbs();

    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.i("123","onDownloadFinish");
            }

            @Override
            public void onCoreInitFinished() {
                Log.i("123","onDownloadFinish");
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.i("123","onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.i("132","onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.i("Taf","onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
