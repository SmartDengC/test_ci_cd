package com.pojo;

/**
 * @Author Dell
 * @Date 2019/11/19
 * @Version V1.0
 **/
public class Role {
    //id
    private Integer id;
    //role名字 如:管理员
    private String roleName;
    //role的描述 如:拥有的权利 Role_add
    private String roleDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

}
