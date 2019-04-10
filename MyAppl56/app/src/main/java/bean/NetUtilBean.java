package bean;

import java.util.List;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/28 15:45
 * @Description：描述信息
 */
public class NetUtilBean {

    public Data data;
    public class Data{
        public List<NEWS> news;
        public  List<NEWS> tepnews;
    }
    public class NEWS{
        public boolean comment;
        public int id;
        public String imageUrl;
        public String publishAt;
        public String title;
        public String type;
        public String url;
    }

}
