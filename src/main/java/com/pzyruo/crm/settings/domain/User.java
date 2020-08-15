package com.pzyruo.crm.settings.domain;

public class User {

    /*
        关于字符串中表现的日期及时间
        年月日：
            yyyy-MM-dd 10位字符串
        日期+时间：年月日时分秒  19位字符串
            yyyy-MM-dd HH:mm:ss


    */


    /*
        关于登陆：
          验证账号和密码
            1.只能拿到账号和密码：
            select count(*) from tbl_user where loginAct=? and loginPwd=?
                0:没有这个用户
                1：正确
                >1:有垃圾数据

           2. 包括失效时间，ip地址等信息
              User user = 执行这条SQL语句 select count(*) from tbl_user where loginAct=? and loginPwd=?

                user对象为null，账号密码错误

                如果不为空，说明账号密码正确
                需要继续向下验证其他的字段信息
                从user中get到
                expireTime  验证实效时间
                lockState   验证锁定状态
                allowIps    验证IP地址是否有效

    */
   private String  id ;       //主键
   private String  loginAct;  // 登陆账号
   private String  name ;    //真实姓名
   private String  loginPwd; //密码
   private String  email;
   private String  expireTime;  //失效时间
   private String  lockState ;   //锁定状态  0：锁定，1：启用
   private String  deptno;   //部门编号
   private String  allowIps;   //运行访问的ip地址
   private String  createTime;    //创建的时间  19位
   private String  createBy;   //创建人
   private String  editTime;   //修改时间   19位
   private String  editBy;     //修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
