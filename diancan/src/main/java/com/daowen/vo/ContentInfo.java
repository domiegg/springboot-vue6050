package com.daowen.vo;

/**
 * 资讯内容
 * @param <T>
 */
public class ContentInfo<T> {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    /**
     * 信息类型
     */
    private int type;

    private T  info;

    public ContentInfo(int type,T t){
        this.type=type;
        this.info=t;
    }

}
