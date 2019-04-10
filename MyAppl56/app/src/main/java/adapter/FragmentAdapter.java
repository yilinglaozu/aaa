package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.myappl56.R;
import com.bumptech.glide.Glide;

import java.util.List;

import bean.NetUtilBean;
import httpUrl.NetUrl;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 16:00
 * @Description：描述信息
 */
public class FragmentAdapter extends BaseAdapter {

    private List<NetUtilBean.NEWS> list;
    private Context context;


    public FragmentAdapter(List<NetUtilBean.NEWS> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public NetUtilBean.NEWS getItem(int position) {

        return list.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder();
            // 绑定控件
            convertView = LayoutInflater.from(context).inflate(R.layout.zibuju, null);
            holder.imageView = convertView.findViewById(R.id.zibuju_image);
            holder.textView = convertView.findViewById(R.id.zibuju_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.textView.setText(list.get(position).title);

        if (!TextUtils.isEmpty(list.get(position).imageUrl)){
            Glide.with(context).load(NetUrl.IP+NetUrl.APP+"/"+list.get(position).imageUrl).into(holder.imageView);
        }


        return convertView;
    }
  class ViewHolder{
        ImageView imageView;
        TextView textView;
  }
}
