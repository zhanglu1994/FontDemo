package fontdemo.fontdemo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Main2Activity extends Activity {

//    @Bind(R.id.tx_webview)
//    WebView webView;
    com.tencent.smtt.sdk.WebView webView;

    String url = "http://www.job168.com/3g/e/post.jsp?booth=211&date=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        webView = new com.tencent.smtt.sdk.WebView(this);

        webView = (WebView) findViewById(R.id.tx_webview);
        int width = webView.getView().getWidth();


        loadUrl(url);



    }

    private void loadUrl(String url) {

        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
//                Log.i("打印日志","网页加载失败");
            }


            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

                webView.loadUrl("javascript:!function(){" + "s=document.createElement('style');s.innerHTML=" + "\"@font-face{font-family:myhyqh;src:url('**injection**/hyqh.ttf');}*{font-family:myhyqh !important;}\";"
                        + "document.getElementsByTagName('head')[0].appendChild(s);" +
                        "document.getElementsByTagName('body')[0].style.fontFamily = \"myhyqh\";}()");



            }






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
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
//                    Log.i("打印日志","加载完成");
                }
            }
        });
    }

}
