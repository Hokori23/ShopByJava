package VO;

import UTILS.Encrypt;

public class User {
    private String id;
    private String password;
    private String name;
    private int role;

    public User(String id, String password, String name, int role) throws Exception {
        if (!checkParam(id, password, name, role)) {
            throw new Exception("参数错误,[id < 25, name < 15, password < 30, role < 2]");
        }

        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public boolean checkParam(String id, String password, String name, int role) {
        boolean flag = true;
        if (id != null && id.length() >= 25) {
            System.out.println("id不合法，值 --- " + id);
            flag = false;
        } else if (password != null && password.length() >= 255) {//SHA1算法返回160消息摘要
            System.out.println("password不合法，值 --- " + password);
            flag = false;
        } else if (name != null && name.length() >= 15) {
            System.out.println("name不合法，值 --- " + name);
            flag = false;
        } else if ("0".equals(String.valueOf(role)) && Integer.toString(role).length() >= 2) {
            System.out.println("role不合法，值 --- " + role);
            flag = false;
        }
        return flag;
    }

    public static boolean checkPassword(String password, String realPassword) throws Exception {
        try {
            String encryptPassword = Encrypt.hash(password, "SHA1");
            return realPassword.equals(encryptPassword);
        } catch (Exception e) {
            throw e;
        }
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " 密码：" + this.password + " 昵称：" + this.name;
    }
}
