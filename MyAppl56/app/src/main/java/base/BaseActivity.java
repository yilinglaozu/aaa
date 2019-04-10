package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 13:38
 * @Description：描述信息
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int initLayput();
    protected abstract void initView();
    protected abstract void initData();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayput());
        initView();
        initData();
    }






}
