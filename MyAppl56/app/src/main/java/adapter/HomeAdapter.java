package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.LinearLayout;

import java.util.List;

import base.BaseFragment;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 14:47
 * @Description：描述信息
 */
public class HomeAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list;

   private String[] titles=new String[]{"x","xx","xxx","xxxx"};

    public HomeAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
