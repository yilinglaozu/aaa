package net;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.common.io.CharStreams;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 15:56
 * @Description：描述信息
 */
public class AsyncTaskk {

    private static  AsyncTaskk instance=new AsyncTaskk();
    private AsyncTaskk(){}
    public static  AsyncTaskk getInstance(){return instance;}
    //请求数据
    //@param server_url 请求的地址
    //     * @param callback   回调
    public  void  getData(String service_url, final AsyncCallback callback){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {
                return getDataFromServer(strings[0]);
            }
            @Override
            protected void onPostExecute(String s) {
                if (TextUtils.isEmpty(s)){
                    callback.onError(2002,"没有数据");
                }else{
                    callback.onSuccess(s);
                }
            }
        }.execute(service_url);

    }
    public interface AsyncCallback{
        void onError(int code, String msg);

        void onSuccess(String result);

    }


    //网络请求数据
    private String getDataFromServer(String server_url){
        HttpURLConnection connection;
        try {
            URL url1=new URL(server_url);
            connection= (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //panduan xiangyingma
            // 判断响应码
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 通过Guava 的 CharStream类 把流转换成字符串
                return CharStreams.toString(new InputStreamReader(connection.getInputStream()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
