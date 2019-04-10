package adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import base.BaseFragment;
import fragment.Bfragment;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 20:50
 * @Description：描述信息
 */
public class BfragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> pagers;
    private String[] titles=new String[]{"x","xx","xxx","xxxx"};

    public BfragmentAdapter(FragmentManager fm, List<BaseFragment> pagers) {
        super(fm);
        this.pagers = pagers;
    }

    @Override
    public Fragment getItem(int i) {
        return pagers.get(i);
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
