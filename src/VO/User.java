package VO;

import UTILS.Encrypt;

public class User {
    private String id;
    private String password;
    private String name;

    public User(String id, String password, String name) throws Exception {
        if (!checkParam(id, password, name)) {
            throw new Exception("参数错误,[id < 25, name < 15, password < 30]");
        }

        this.id = id;
        this.password = password;
        this.name = name;
    }

    public boolean checkParam(String id, String password, String name) {
        boolean flag = true;
        if (id.length() >= 25 && id != null) {
            flag = false;
        } else if (password.length() >= 160 && password != null) {//SHA1算法返回160消息摘要
            flag = false;
        } else if (name.length() >= 15 && name != null) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkPassword(String password, String realPassword) {
        String encryptPassword = Encrypt.hash(password,"SHA1");
        if(realPassword.equals(encryptPassword)){
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " 密码：" + this.password + " 昵称：" + this.name;
    }
}
