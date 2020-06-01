package SERVICE;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DBConnection.DB;
import VO.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public boolean addUser(User user) throws Exception {
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
    public boolean removeUserById(String id) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.removeUserById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return flag;
    }

    // 改
    public boolean editUser(User user) throws Exception {
        boolean flag = false;
        try {
            // 若存在则更新
            if (this.dao.getUserById(user.getId()) != null) {
                flag = this.dao.editUser(user);
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
    public boolean login(User user) throws Exception {
        User queryUser = null;
        try {
            queryUser = this.getUserById(user.getId());

            if (queryUser != null && user.getPassword().equals(queryUser.getPassword())) {
                return true;
            } else {
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
    public boolean register(User user) throws Exception {
        User queryUser = null;
        try {
            queryUser = this.dao.getUsersByIdWithoutPS(user.getId());
            if (queryUser != null && user.getId().equals(queryUser.getId())) {
                return false;
            } else {
                boolean flag = addUser(user);
                return flag;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // 关闭数据库
            this.conn.close();
        }
    }

}
