package com.pojo;

/**
 * @Author Dell
 * @Date 2019/11/19
 * @Version V1.0
 **/
public class Permission {

    //id
    private Integer id;
    //请求的名字 如添加商品
    private String permName;
    //请求的角色 如 ROLE_ADD
    private String permTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermTag() {
        return permTag;
    }

    public void setPermTag(String permTag) {
        this.permTag = permTag;
    }

}
