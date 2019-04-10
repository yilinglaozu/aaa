package bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/29 10:45
 * @Description：描述信息
 */
public class XBannerBean  extends SimpleBannerInfo {
    public  String imagerUrl;
    @Override
    public Object getXBannerUrl() {
        return imagerUrl;
    }
}
