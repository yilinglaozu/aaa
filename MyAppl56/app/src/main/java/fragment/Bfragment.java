package fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bawei.myappl56.ChannnMangerActivity;
import com.bawei.myappl56.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.AsyncTaskk;

import java.util.ArrayList;
import java.util.List;

import adapter.BfragmentAdapter;
import adapter.FragmentAdapter;
import adapter.HomeAdapter;
import base.BaseFragment;
import bean.NetUtilBean;
import fragment.fragment.Fragment_Four;
import fragment.fragment.Fragment_One;
import fragment.fragment.Fragment_Three;
import fragment.fragment.Fragment_Two;
import httpUrl.NetUrl;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 14:45
 * @Description：描述信息
 */
public class Bfragment extends BaseFragment {

    private  TabLayout tabLayout;
    private ViewPager viewPager;
    private List<BaseFragment> list;
    private BfragmentAdapter adapter;
    private ImageView iv_channel_manager;
    @Override
    protected int setView() {
        return R.layout.bfragment;
    }

    @Override
    protected void initeView(View view) {
        iv_channel_manager=view.findViewById(R.id.iv_channel_manager);
        tabLayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.v4viewPager);
        tabLayout.setupWithViewPager(viewPager);

        list=new ArrayList<>();
        list.add(new Fragment_One());
        list.add(new Fragment_Two());
        list.add(new Fragment_Three());
        list.add(new Fragment_Four());
        //点击加号 跳转页面
        iv_channel_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tiaozhuan de fangfa
                toChannelMannger();
            }
        });

        //适配器
        adapter=new BfragmentAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);


    }
    //跳转页面
    private void toChannelMannger() {
        //跳转页面 channel
        mActivity.startActivityForResult(new Intent(mActivity, ChannnMangerActivity.class),0x00);
    }
    @Override
    protected void initData() {

    }

}
