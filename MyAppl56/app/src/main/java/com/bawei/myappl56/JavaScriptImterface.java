package com.bawei.myappl56;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/4/2 14:50
 * @Description：描述信息
 */
public class JavaScriptImterface {
    private Context context;

    public JavaScriptImterface(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public  void  share(String platform){
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("分享到"+platform+"成功")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
