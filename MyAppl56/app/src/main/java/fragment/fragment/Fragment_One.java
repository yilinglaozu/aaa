package fragment.fragment;

import android.content.Intent;
import android.renderscript.Long4;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bawei.myappl56.R;
import com.bawei.myappl56.WebViewActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import net.AsyncTaskk;

import java.util.ArrayList;
import java.util.List;

import adapter.FragmentAdapter;
import base.BaseFragment;
import bean.HomeBean;
import bean.NetUtilBean;
import bean.XBannerBean;
import httpUrl.NetUrl;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 20:32
 * @Description：描述信息
 */
public class Fragment_One extends BaseFragment {
    private PullToRefreshListView pullToRefreshListView;
    private  int page=1;
    private List<NetUtilBean.NEWS> data=new ArrayList<>();
    private  List<XBannerBean> bannerBeans=new ArrayList<>();
    private FragmentAdapter adapter;
    private XBanner bannere;

    @Override
    protected int setView() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initeView(View view) {
        pullToRefreshListView=view.findViewById(R.id.pull);
        pullToRefreshListView.setMode(PullToRefreshListView.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                refresh()
                ;
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载
                loadMore();
            }
        });
        //适配器
        adapter=new FragmentAdapter(data,mActivity);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String urll=adapter.getItem(position).url;
                Intent intent=new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("url",urll);
                startActivity(intent);
                Log.i("TAG",urll);
            }
        });

        //添加布局
        View v=View.inflate(mActivity,R.layout.xbanner_layout,null);
        bannere=v.findViewById(R.id.banner);
        ListView listView=pullToRefreshListView.getRefreshableView();
        listView.addHeaderView(v);


        //设置数据
        bannere.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(mActivity).load(((XBannerBean)model).getXBannerUrl()).into((ImageView) view);
            }
        });
        //样式
        bannere.setPageTransformer(Transformer.Accordion);
        bannere.setPageChangeDuration(3000);

    }
    //下拉刷新
    private void refresh() {
        page=1;
        loadData();
    }
    //上拉加载
    private void loadMore() {
        page++;
        loadData();
    }

    @Override
    protected void initData() {

        loadData();
    }
    private void loadData() {

        // NetUrl.SERVER_duan + NetUrl.CATEGORY_meimv + "?page"
        //       + page,
        AsyncTaskk.getInstance().getData( NetUrl.IP+NetUrl.APP+NetUrl.NEWSAPI+"?page"+page, new AsyncTaskk.AsyncCallback() {

            private XBannerBean bannerBean;
            @Override
            public void onError(int code, String msg) {

            }
            @Override
            public void onSuccess(String result) {



                Gson gson=new Gson();
                NetUtilBean netUtilBean = gson.fromJson(result, NetUtilBean.class);
                if (page==1){
                    data.clear();
                }
                if (bannerBeans.size()<=0){
                    for (int i = 0; i <netUtilBean.data.news.size() ; i++) {

                        bannerBean=new XBannerBean();
                        bannerBean.imagerUrl=NetUrl.IP+NetUrl.APP+"/"+netUtilBean.data.news.get(i).imageUrl;
                        bannerBeans.add(bannerBean);
                    }
                    bannere.setBannerData(bannerBeans);
                }else{
                    bannere.setBannerData(bannerBeans);
                }

                //集合添加数据
                data.addAll(netUtilBean.data.news);
                adapter.notifyDataSetChanged();
                //停止刷新
                pullToRefreshListView.onRefreshComplete();


            }
        });
    }
}
