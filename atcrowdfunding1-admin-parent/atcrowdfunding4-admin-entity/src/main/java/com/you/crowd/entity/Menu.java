package com.you.crowd.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private Integer id;

    private String name;

    private Integer pId;

    private String url;

    private String ico;
    //指定生成节点是展开还是关闭  默认关闭
    private boolean open = true;
    //当前节点有那些子节点
    private List<Menu> children = new ArrayList<>();

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico == null ? null : ico.trim();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pId=" + pId +
                ", url='" + url + '\'' +
                ", ico='" + ico + '\'' +
                ", open=" + open +
                ", children=" + children +
                '}';
    }
}