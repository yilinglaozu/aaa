package bean;

import java.util.List;

/**
 * @Author：刘京源
 * @E-mail： 1179348728@qq.com
 * @Date： 2019/3/29 16:01
 * @Description：描述信息
 */
public class HomeBean {

    /**
     * code : 1000
     * drawerTitle : 侧滑菜单
     * mainTitle : 天下咨询
     * msg : 成功
     * result : [{"children":[{"id":4,"title":"风景","type":2,"url":"/fengjing"},{"id":5,"title":"美女","type":2,"url":"/meinv"},{"id":6,"title":"动漫卡通","type":2,"url":"/dongma"},{"id":7,"title":"娱乐明星","type":2,"url":"/yule"},{"id":8,"title":"萌宠","type":2,"url":"/mengchong"},{"id":9,"title":"汽车","type":2,"url":"/qiche"},{"id":10,"title":"游戏","type":2,"url":"/youxi"}],"id":0,"title":"首页","type":1,"url":""},{"id":1,"title":"新闻","type":1,"url":""},{"id":2,"title":"发现","type":1,"url":""},{"id":3,"title":"我的","type":1,"url":""}]
     */

    private int code;
    private String drawerTitle;
    private String mainTitle;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDrawerTitle() {
        return drawerTitle;
    }

    public void setDrawerTitle(String drawerTitle) {
        this.drawerTitle = drawerTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * children : [{"id":4,"title":"风景","type":2,"url":"/fengjing"},{"id":5,"title":"美女","type":2,"url":"/meinv"},{"id":6,"title":"动漫卡通","type":2,"url":"/dongma"},{"id":7,"title":"娱乐明星","type":2,"url":"/yule"},{"id":8,"title":"萌宠","type":2,"url":"/mengchong"},{"id":9,"title":"汽车","type":2,"url":"/qiche"},{"id":10,"title":"游戏","type":2,"url":"/youxi"}]
         * id : 0
         * title : 首页
         * type : 1
         * url :
         */

        private int id;
        private String title;
        private int type;
        private String url;
        private List<ChildrenBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 4
             * title : 风景
             * type : 2
             * url : /fengjing
             */

            private int id;
            private String title;
            private int type;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
