package fontdemo.fontdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.wv_mian)
    WebView wvMian;

    String url = "http://www.job168.com/3g/e/post.jsp?booth=211&date=";
    @Bind(R.id.tv_main)
    Button tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        WebSettings settings = wvMian.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击缩放
        settings.setAppCacheEnabled(false);
        settings.setAllowFileAccess(true);



        wvMian.loadUrl(url);


        wvMian.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                view.loadUrl("javascript:!function(){" + "s=document.createElement('style');s.innerHTML=" + "\"@font-face{font-family:myhyqh;src:url('**injection**/hyqh.ttf');}*{font-family:myhyqh !important;}\";"
                        + "document.getElementsByTagName('head')[0].appendChild(s);" +
                        "document.getElementsByTagName('body')[0].style.fontFamily = \"myhyqh\";}()");

            }

            //由于网页上是没有权限访问本地的asset文件夹的，因此我们需要拦截请求来加载本地的文件，我这里替换了`file:
//android_assets/`为 `**injection**/`了，我们还需要重写`shouldInterceptRequest`
//在请求为我们这个字体文件的时候，加载本地文件：
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                if (url != null && url.contains("**injection**/")) {
                    //String assertPath = url.replace("**injection**/", "");
                    String assertPath = url.substring(url.indexOf("**injection**/") + "**injection**/".length(), url.length());
                    try {
                        response = new WebResourceResponse("application/x-font-ttf", "UTF8", getAssets().open(assertPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return response;
            }


        });


    }


    @OnClick(R.id.tv_main)
    public void onViewClicked() {

        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);

    }
}
