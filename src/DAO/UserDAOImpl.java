package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import VO.User;

public class UserDAOImpl implements UserDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // 增
    @Override
    public boolean addUser(User user) throws Exception {
        boolean flag = false;
        // 预编译sql，防止sql注入

        String sql = "insert into user (id,password,name) values(?,?,?)";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, user.getId());
            this.pstmt.setString(2, user.getPassword());
            this.pstmt.setString(3, user.getName());

            if (this.pstmt.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            throw e;
        }
        this.pstmt.close();
        return flag;
    }

    // 删
    @Override
    public boolean removeUserById(String id) throws Exception {
        boolean flag = false;
        String sql = "delete from user where id = ?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1, id);
        if (this.pstmt.executeUpdate() > 0) {
            flag = true;
        } else {
            System.out.println("删除失败");
        }
        this.pstmt.close();
        return flag;
    }

    // 改
    @Override
    public boolean editUser(User user) throws Exception {
        boolean flag = false;

        String sql = "update user set password = ?, name = ? where id = ?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, user.getPassword());
            this.pstmt.setString(2, user.getName());
            this.pstmt.setString(3, user.getId());

            if (this.pstmt.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            throw e;
        }

        this.pstmt.close();
        return flag;
    }

    // 查
    @Override
    public User getUserById(String id) throws Exception {
        User user = null;
        String sql = "select * from user where id = ?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, id);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw e;
        }
        this.pstmt.close();
        return user;
    }

    // 查（无密码）
    @Override
    public User getUsersByIdWithoutPS(String id) throws Exception {
        User user = null;
        String sql = "select id,name from user where id = ?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, id);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString(1), null, rs.getString(2));
            }
        } catch (SQLException e) {
            throw e;
        }
        this.pstmt.close();
        return user;
    }

    // 显示所有员工
    @Override
    public List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<User>();
        User user = null;
        String sql = "select * from user";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
                users.add(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        this.pstmt.close();
        return users;
    }
}
