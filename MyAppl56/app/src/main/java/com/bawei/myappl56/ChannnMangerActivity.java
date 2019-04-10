package com.bawei.myappl56;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.channel.MyChannelAdapter;
import adapter.channel.OtherChannelAdapter;
import base.BaseActivity;
import bean.ChannelBean;

public class ChannnMangerActivity extends BaseActivity implements  MyChannelAdapter.ClearListener{


    private TextView bianji;
    private GridView my_channl,other_channl;
    private List<ChannelBean> myChannelBean=new ArrayList<>();
    private List<ChannelBean> otherChannelBean=new ArrayList<>();
    private boolean isEditStatte;
    private ImageView iv_back;

    private MyChannelAdapter myChannelAdapter;
    private OtherChannelAdapter otherChannelAdapter;

    @Override
    protected int initLayput() {
        return R.layout.activity_channn_manger;
    }

    @Override
    protected void initView() {
        bianji=findViewById(R.id.bianji);
        iv_back=findViewById(R.id.iv_back);
        my_channl=findViewById(R.id.my_channl);
        other_channl=findViewById(R.id.other_channl);
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channgeChannelState();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        other_channl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 编辑状态点击才可用
                if (isEditStatte) {
                    ChannelBean item = otherChannelAdapter.getItem(position);
                    otherChannelBean.remove(item);
                    otherChannelAdapter.notifyDataSetChanged();

                    item.isEdit = isEditStatte;
                    myChannelBean.add(item);
                    myChannelAdapter.notifyDataSetChanged();
                }
            }
        });
        my_channl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("currentPage", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * 改变频道的编辑状态
     */
    private void channgeChannelState() {
        // 改变编辑文本框
        isEditStatte = !isEditStatte;
        bianji.setText(isEditStatte ? "完成" : "编辑");
        for (int i = 0; i < myChannelBean.size(); i++) {
            myChannelBean.get(i).isEdit = isEditStatte;
        }
        myChannelAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

        //模拟我的频道
        ChannelBean channelBean=null;
        for (int i = 0; i <10 ; i++) {
            channelBean=new ChannelBean();
            channelBean.name="品势生活"+i;
            myChannelBean.add(channelBean);
        }
        myChannelAdapter=new MyChannelAdapter(myChannelBean,ChannnMangerActivity.this);
        myChannelAdapter.setClearListener(this);
        my_channl.setAdapter(myChannelAdapter);

        //模拟其他频道数据
        for (int i = 0; i < 10; i++) {
            channelBean=new ChannelBean();
            channelBean.name="美兰天空"+i;
            otherChannelBean.add(channelBean);
        }
            otherChannelAdapter=new OtherChannelAdapter(this,otherChannelBean);
         other_channl.setAdapter(otherChannelAdapter);




    }



    @Override
    public void onClearClick(int position) {
        ChannelBean item = myChannelAdapter.getItem(position);
        myChannelBean.remove(item);
        myChannelAdapter.notifyDataSetChanged();

        otherChannelBean.add(item);
        otherChannelAdapter.notifyDataSetChanged();
    }
}
