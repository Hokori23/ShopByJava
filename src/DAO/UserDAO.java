package DAO;

import VO.User;
import java.util.List;

public interface UserDAO {
    // 增
    public boolean addUser(User user) throws Exception;

    // 删
    public boolean removeUserById(String id) throws Exception;

    // 改
    public boolean editUser(User user) throws Exception;

    //查
    public User getUserById(String id) throws Exception;

    //查（无密码）
    public User getUsersByIdWithoutPS(String id) throws Exception;
    
    //遍历
    public List<User> getUsers() throws Exception;
}
