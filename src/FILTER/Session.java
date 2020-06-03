package FILTER;

import SERVICE.UserService;
import VO.Rest;
import VO.User;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Session implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //替换原生Request
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        //登录或注册
        HttpSession session = httpRequest.getSession();
        Rest rest = new Rest();
        boolean flag = false;
        System.out.println("SESSION FILTER http servletPath :"+httpRequest.getServletPath());
        if (httpRequest.getServletPath().equals("/user") && httpRequest.getMethod().equals("POST")) {
            //7天有效期
            session.setMaxInactiveInterval(60 * 60 * 24 * 7);
        } else {
            try {
                User user = UserService.getDAOInstance().getUserById((String) session.getAttribute("id"));
                if (user == null) {
                    //销毁session
                    session.invalidate();
                    rest.toRestMessage(401, "请登录, Unauthorized");
                    flag = true;
                } else {
                    System.out.println("Session 当前用户ID: "+user.getId());
                    session.setAttribute("id", (Object) user.getId());
                    session.setMaxInactiveInterval(60 * 60 * 24 * 7);
                }
            } catch (Exception e) {
                flag = true;
                rest.toRestMessage(1, e.getMessage());
            }
        }
        System.out.println("Session Filter");
        if (flag) {
            response.getWriter().write(rest.toJSONString());
            return;
        }
        filterChain.doFilter(httpRequest, response);
    }

    public void init(FilterConfig conf) throws ServletException {
        System.out.println("Session Filter staring...");
    }

    public void destroy() {
    }
}