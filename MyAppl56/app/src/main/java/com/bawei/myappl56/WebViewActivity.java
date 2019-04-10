package com.bawei.myappl56;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;
import httpUrl.NetUrl;

public class WebViewActivity extends BaseActivity {


    private WebView webView;
    private TextView fen;
    private  WebSettings settings;
    private Button jian,jia;
    private int i=100;
    private float a=1;


    @Override
    protected int initLayput() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_view);
        fen = findViewById(R.id.fen);
        jia=findViewById(R.id.jia);
        jian=findViewById(R.id.jian);


        //s设置打开网页客户端
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        //获取settings
        settings= webView.getSettings();
        //配置settings
        settings.setJavaScriptEnabled(true);
        fen();

    }


    public  void fen(){
        fen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] item = {"超小号字体","小号字体", "正常字体", "大号字体","超大号字体"};
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("改变字号")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                *     SMALLEST is 50%
                     SMALLER is 75%
                    NORMAL is 100%
                    LARGER is 150%
                    LARGEST is 200%
                      * */
                                switch (which) {
                                    case 0:
                                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                                        break;
                                    case 1:
                                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                                        break;
                                    case 2:
                                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                                        break;
                                    case 3:
                                        settings.setTextSize(WebSettings.TextSize.LARGER);
                                        break;
                                    case 4:
                                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                                        break;

                                }
                            }
                        }).show();
            }
        });



    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i("TAGG", url);
        webView.loadUrl(NetUrl.IP + NetUrl.APP + "/" + url);
        webViewXY();
        //支持手势缩放的链接
        //webView.loadUrl("http://www.oschina.net/question/54100_34836");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Toast.makeText(WebViewActivity.this, message, Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                if ("js".equals(uri.getScheme())) {
                    if ("demo".equals(uri.getAuthority())) {
                        String arg1 = uri.getQueryParameter("agr1");
                        String agr2 = uri.getQueryParameter("arg2");
                        new AlertDialog.Builder(WebViewActivity.this)
                                .setTitle("JS调用Andriod成功")
                                .setMessage(arg1 + "======" + agr2)
                                .show();
                        result.confirm("弹出的放回结果");
                    }
                    return true;

                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

        });
        //第一种代码实现
        webView.addJavascriptInterface(new JavaScriptImterface(this), "andriod");
        // 第二种JS调用Android代码实现
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if ("js".equals(uri.getScheme())) {
                    if ("webview".equals(uri.getAuthority())) {

                        String arg = uri.getQueryParameter("arg");
                        new AlertDialog.Builder(WebViewActivity.this)
                                .setTitle("JS调用Android成功，参数")
                                .setMessage(arg)
                                .show();
                    }
                    return true;
                }


                return super.shouldOverrideUrlLoading(view, url);
            }
        });


    }

    private void webViewXY() {
        //比例缩放
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);

        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a-=0.25;
                if (a<=0f){
                    a=0.25f;
                }

                webView.setScaleX(a);
                webView.setScaleY(a);
            }
        });

        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a+=0.25;
                if (a>=2f){
                    a=2f;
                }
                //webView.setInitialScale(i);
                webView.setScaleX(a);
                webView.setScaleY(a);
            }
        });
    }
}
