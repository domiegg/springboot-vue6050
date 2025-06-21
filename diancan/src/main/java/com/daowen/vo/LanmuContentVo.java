package com.daowen.vo;

import com.daowen.entity.Lanmu;

import java.util.List;

public class LanmuContentVo<T> {

    private int id;
    private String name;

    private String bannerurl;
    /**
     * 类型编号
     * 1 资讯
     * 2 视频
     */
    private int type;
    /**
     * 显示风格
     */
    private String moduleName="ImageTextGroup";

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    private List<T>  content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }

    public LanmuContentVo(Lanmu lanmu, List<T> content){
        this.id= lanmu.getId();
        this.name= lanmu.getName();
        this.type=lanmu.getType();
        this.content=content;
        this.bannerurl=lanmu.getBannerurl();


    }
}
