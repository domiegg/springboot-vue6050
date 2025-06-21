package com.daowen.vo;

import java.util.List;

public class PageContentVo {

    private int pageId;
    private String pageName;

    private List<LanmuContentVo>  listLanmuContent;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<LanmuContentVo> getListLanmuContent() {
        return listLanmuContent;
    }

    public void setListLanmuContent(List<LanmuContentVo> listLanmuContent) {
        this.listLanmuContent = listLanmuContent;
    }
}
