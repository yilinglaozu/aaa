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
 * @Date： 2019/3/29 19:46
 * @Description：描述信息qita 其他频道
 */
public class OtherChannelAdapter extends BaseAdapter {
    private Context context;
    private List<ChannelBean> datas;

    public OtherChannelAdapter(Context context, List<ChannelBean> datas) {
        this.context = context;
        this.datas = datas;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
            hodler.tvName = convertView.findViewById(R.id.tv_name);
            hodler.ivClear = convertView.findViewById(R.id.iv_clear);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        // 设值
        ChannelBean item = datas.get(position);
        if (!TextUtils.isEmpty(item.name)) {
            hodler.tvName.setText(item.name);
        }
        hodler.ivClear.setVisibility(View.INVISIBLE);
        return convertView;
    }

    class ViewHodler {
        TextView tvName;
        ImageView ivClear;
    }
}
