package adapter.channel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.myappl56.R;

import java.util.List;

import bean.ChannelBean;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/29 19:34
 * @Description：描述信息 我的频道适配器
 *
 */
public class MyChannelAdapter extends BaseAdapter {

    private List<ChannelBean> datas;
    private Context context;

    public MyChannelAdapter(List<ChannelBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ChannelBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.channel_item, null);
            holder.tvname=convertView.findViewById(R.id.tv_name);
            holder.ivClear=convertView.findViewById(R.id.iv_clear);
            holder.ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clearListener != null) {
                        clearListener.onClearClick(position);
                    }
                }
            });
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        // 设值
        ChannelBean item = datas.get(position);
        if (!TextUtils.isEmpty(item.name)) {
            holder.tvname.setText(item.name);
        }
        if (item.isEdit) {
            holder.ivClear.setVisibility(View.VISIBLE);
        } else {
            holder.ivClear.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvname;
        ImageView ivClear;
    }
    private ClearListener clearListener;
    public void setClearListener(ClearListener clearListener) {
        this.clearListener = clearListener;
    }

    public interface ClearListener {
        void onClearClick(int position);
    }
}
