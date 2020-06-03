package API;

import FILTER.XssHttpServletRequestWrapper;
import FILTER.XssJSONObject;
import FACTORY.UserFactory;
import VO.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@WebServlet("/user")
public class UserAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 注册/登录
         *
         * @param String id
         * @param String password
         * @param String (name)
         *
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper) request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temId = bodyParams.get("id");
        Object temPassword = bodyParams.get("password");
        Object temName = bodyParams.get("name");
        Rest rest = new Rest();
        try {
            // 判断参数进行业务逻辑处理
            if (temId == null || temPassword == null) {
                rest.toRestMessage(1, "参数错误, {id, password, (name)}");
            } else {
                String id = temId.toString();
                String password = temPassword.toString();
                if (temName != null) {
                    // 构造对象
                    System.out.println("注册 --- " + id);
                    String name = temName.toString();
                    User user = new User(id, password, name, 0);
                    // 调用register方法，若添加成功则返回true
                    boolean flag = UserFactory.getDAOInstance().register(user, rest);
                    // 反馈给用户处理结果
                    if (flag == true) {
                        //添加session属性
                        HttpSession session = request.getSession();
                        session.setAttribute("id", user.getId());
                    }
                } else {
                    System.out.println("登录 --- " + id);
                    // 构造对象
                    User user = new User(id, password, "", 0);
                    boolean flag = UserFactory.getDAOInstance().login(user, rest);
                    // 反馈给用户处理结果
                    if (flag == true) {
                        //添加session属性
                        HttpSession session = request.getSession();
                        session.setAttribute("id", user.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 查/遍历
         *
         * @param String (id)
         *
         */

        // 获取数据
        String id = request.getParameter("id");
        // 判断参数进行业务逻辑处理
        Rest rest = new Rest();
        try {
            if (id == null || id.isEmpty()) {
                List<User> users = UserFactory.getDAOInstance().getUsers();
                rest.toRestArray(0, users, "遍历成功");
            } else {
                User user = UserFactory.getDAOInstance().getUsersByIdWithoutPS(id);
                rest.toRestObject(0, user, "查询成功");
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 删
         * @param String password
         */

        // 获取数据
        String id = (String) request.getSession().getAttribute("id");
        String password = request.getParameter("password");

        Rest rest = new Rest();
        if (password == null || password.isEmpty()) {
            rest.toRestMessage(1, "密码错误");
        } else {
            try {
                UserFactory.getDAOInstance().removeUser(id, password, rest);
            } catch (Exception e) {
                rest.toRestMessage(1, e.getMessage());
            }
        }
        response.getWriter().write(rest.toJSONString());
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 改
         *
         * @param String old_password
         * @param String password
         * @param String name
         *
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper) request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temOldPassword = bodyParams.get("old_password");
        Object temPassword = bodyParams.get("password");
        Object temName = bodyParams.get("name");
        String id = (String) request.getSession().getAttribute("id");

        Rest rest = new Rest();
        try {
            if (temOldPassword == null || temPassword == null || temName == null) {
                rest.toRestMessage(1, "参数错误, {old_password, password, name}");
            } else {
                String oldPassword = temOldPassword.toString();
                String password = temPassword.toString();
                String name = temName.toString();

                // 构造对象
                User user = new User(id, password, name, 0);
                // 调用editUser方法，若添加成功则返回true
                UserFactory.getDAOInstance().editUser(user, oldPassword, rest);
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

}
