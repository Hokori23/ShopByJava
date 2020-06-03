package SERVICE;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DBConnection.DB;
import VO.Rest;
import VO.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UTILS.Encrypt;

public class UserService {

    private DB conn = null;
    private UserDAO dao;

    public static UserService getDAOInstance() throws Exception {
        return new UserService();
    }

    public UserService() throws Exception {

        // 创建一个MySQLConnection的对象
        this.conn = new DB();
        this.dao = new UserDAOImpl(this.conn.getConnection());

    }

    // 增
    public boolean addUser(User user, Rest rest) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.addUser(user);
        } catch (SQLException e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return flag;
    }

    // 删
    public boolean removeUser(String id, String password, Rest rest) throws Exception {
        boolean flag = false;
        try {
            //加密
            password = Encrypt.hash(password, "SHA1");
            User user = this.getUserById(id);
            if (user == null) {
                rest.toRestMessage(2, "账号不存在");
            }
            if (user.getPassword().equals(password)) {
                flag = this.dao.removeUserById(id);
                if (flag) {
                    rest.toRestMessage(0, "注销成功");
                } else {
                    rest.toRestMessage(3, "注销失败（数据库操作错误）");
                }
            } else {
                rest.toRestMessage(4, "密码错误");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return flag;
    }

    // 改
    public boolean editUser(User user, String oldPassword, Rest rest) throws Exception {
        boolean flag = false;
        try {
            // 若存在则更新
            User queryUser = this.dao.getUserById(user.getId());


            if (queryUser != null) {
                //验证旧密码
                oldPassword = Encrypt.hash(oldPassword, "SHA1");
                if (oldPassword.equals(queryUser.getPassword())) {
                    //加密新密码
                    user.setPassword(Encrypt.hash(user.getPassword(), "SHA1"));
                    if (this.dao.editUser(user)) {
                        user.setPassword("");
                        flag = true;
                        rest.toRestObject(0, user, "修改信息成功");
                    } else {
                        rest.toRestMessage(2, "修改信息失败（数据库操作失败）");
                    }
                } else {
                    System.out.println("密码错误");
                    rest.toRestMessage(3, "密码错误");
                }
            } else {
                System.out.println("账号不存在");
                rest.toRestMessage(4, "账号不存在");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return flag;
    }

    // 查
    public User getUserById(String id) throws Exception {
        User user = null;
        try {
            user = this.dao.getUserById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            // 关闭数据库
            this.conn.close();
        }
        return user;
    }

    // 查（无密码）
    public User getUsersByIdWithoutPS(String id) throws Exception {
        User user = null;
        try {
            user = this.dao.getUsersByIdWithoutPS(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            // 关闭数据库
            this.conn.close();
        }
        return user;
    }

    // 显示所有员工
    public List<User> getUsers() throws Exception {
        List<User> Users = new ArrayList<User>();
        try {
            Users = this.dao.getUsers();
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return Users;
    }

    // 登录
    public boolean login(User user, Rest rest) throws Exception {
        try {
            //加密
            user.setPassword(Encrypt.hash(user.getPassword(), "SHA1"));
            User queryUser = null;
            queryUser = this.getUserById(user.getId());
            if (queryUser == null) {
                rest.toRestMessage(2, "账号不存在");
                return false;
            }
            if (user.getPassword().equals(queryUser.getPassword())) {
                user.setPassword("");
                rest.toRestObject(0, user, "登陆成功");
                return true;
            } else {
                rest.toRestMessage(3, "密码错误");
                return false;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // 关闭数据库
            this.conn.close();
        }
    }

    // 注册
    public boolean register(User user, Rest rest) throws Exception {
        try {
            //加密
            user.setPassword(Encrypt.hash(user.getPassword(), "SHA1"));

            User queryUser = null;
            queryUser = this.dao.getUsersByIdWithoutPS(user.getId());
            if (queryUser != null) {
                rest.toRestMessage(2, "账号已存在");
            }


            //默认权限为0
            user.setRole(0);
            boolean flag = addUser(user, rest);
            if (flag) {
                user.setPassword("");
                rest.toRestObject(0, user, "注册成功");
            }
            return flag;

        } catch (SQLException e) {
            throw e;
        } finally {
            // 关闭数据库
            this.conn.close();
        }
    }

}
