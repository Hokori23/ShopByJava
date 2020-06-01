package API;

import FILTER.XssHttpServletRequestWrapper;
import FILTER.XssJSONObject;
import SERVICE.UserService;
import VO.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.alibaba.fastjson.*;

@WebServlet("/user")
public class UserAPI extends HttpServlet {
    public void responseFormat(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT,DELETE,POST,GET");
        response.setContentType("application/json;charset=UTF-8");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        responseFormat(response);
        /*
         * 增/登录
         *
         * @String id
         *
         * @String password
         *
         * [@String name]
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper)request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temId = bodyParams.get("id");
        Object temPassword = bodyParams.get("password");
        Object temName = bodyParams.get("name");
        // 判断参数进行业务逻辑处理
        JSONObject res = new JSONObject();
        if (temId == null || temPassword == null) {
            res.put("errcode", 1);
            res.put("message", "参数错误");
        } else {
            String id = temId.toString();
            String password = temPassword.toString();
            if (temName != null) {
                // 构造对象
                String name = temName.toString();
                User user = new User(id, password, name);

                try {
                    // 调用register方法，若添加成功则返回true
                    boolean flag = UserService.getDAOInstance().register(user);
                    // 反馈给用户处理结果
                    if (flag == true) {
                        res.put("errcode", 0);
                        res.put("message", "注册成功");
                        res.put("data", user);
                    } else {
                        res.put("errcode", 1);
                        res.put("message", "注册失败");
                    }
                } catch (Exception e) {
                    res.put("errcode", 1);
                    res.put("message", e.getMessage());
                }
            } else {
                // 构造对象
                User user = new User(id, password, null);

                try {
                    // 调用addUser方法，若添加成功则返回true
                    boolean flag = UserService.getDAOInstance().login(user);
                    // 反馈给用户处理结果
                    if (flag == true) {
                        res.put("errcode", 0);
                        res.put("message", "登录成功");
                        user = UserService.getDAOInstance().getUsersByIdWithoutPS(user.getId());
                        res.put("data", user);
                    } else {
                        res.put("errcode", 1);
                        res.put("message", "登录失败");
                    }
                } catch (Exception e) {
                    res.put("errcode", 1);
                    res.put("message", e.getMessage());
                }
            }
        }
        response.getWriter().write(res.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        responseFormat(response);
        /*
         * 查/遍历
         *
         * [@String id]
         */

        // 获取数据
        String id = request.getParameter("id");
        // 判断参数进行业务逻辑处理
        JSONObject jsonObject = new JSONObject();
        try {
            if (id == null || id.isEmpty()) {
                List<User> users = UserService.getDAOInstance().getUsers();
                if (users != null) {
                    jsonObject.put("errcode", 0);
                    jsonObject.put("message", "遍历成功");
                    jsonObject.put("data", users);
                } else {
                    jsonObject.put("errcode", 1);
                    jsonObject.put("message", "无用户信息");
                }
            } else {
                User user = UserService.getDAOInstance().getUsersByIdWithoutPS(id);
                if (user != null) {
                    jsonObject.put("errcode", 0);
                    jsonObject.put("message", "查询成功");
                    jsonObject.put("data", user);
                } else {
                    jsonObject.put("errcode", 1);
                    jsonObject.put("message", "无该用户信息");
                }
            }
        } catch (Exception e) {
            jsonObject.put("errcode", 1);
            jsonObject.put("message", e.getMessage());
        }
        response.getWriter().write(jsonObject.toJSONString());
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        responseFormat(response);
        /*
         * 删
         *
         * @String id
         */

        // 获取数据
        String id = request.getParameter("id");

        JSONObject jsonObject = new JSONObject();
        if (id == null || id.isEmpty()) {
            jsonObject.put("errcode", 1);
            jsonObject.put("message", "参数错误");
        } else {
            try {
                Result res = null;
                boolean flag = UserService.getDAOInstance().removeUserById(id);
                if (flag == true) {
                    jsonObject.put("errcode", 0);
                    jsonObject.put("message", "注销成功");
                } else {
                    jsonObject.put("errcode", 1);
                    jsonObject.put("message", "注销失败");
                }
            } catch (Exception e) {
                JSONObject res = new JSONObject();
                res.put("errcode", 1);
                res.put("message", e.getMessage());
            }
        }
        response.getWriter().write(jsonObject.toJSONString());
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        responseFormat(response);
        /*
         * 改
         *
         * @String id
         *
         * @String password
         *
         * @String name
         */

        // 获取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = "";
        String body = "";
        while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
            body += str;
        }
        JSONObject bodyParams = JSONObject.parseObject(body);
        Object temId = bodyParams.get("id");
        Object temPassword = bodyParams.get("password");
        Object temName = bodyParams.get("name");

        JSONObject jsonObject = new JSONObject();
        if (temId == null || temPassword == null || temName == null) {
            jsonObject.put("errcode", 1);
            jsonObject.put("message", "参数错误");
        } else {
            String id = temId.toString();
            String password = temPassword.toString();
            String name = temName.toString();

            // 构造对象
            User user = new User(id, password, name);

            try {
                // 调用editUser方法，若添加成功则返回true
                boolean flag = UserService.getDAOInstance().editUser(user);
                Result res = null;
                // 反馈给用户处理结果
                if (flag == true) {
                    jsonObject.put("errcode", 0);
                    jsonObject.put("message", "修改信息成功");
                    jsonObject.put("data", user);
                } else {
                    jsonObject.put("errcode", 1);
                    jsonObject.put("message", "修改信息失败");
                }
            } catch (Exception e) {
                jsonObject.put("errcode", 1);
                jsonObject.put("message", e.getMessage());
            }
        }
        response.getWriter().write(jsonObject.toJSONString());
    }

}
