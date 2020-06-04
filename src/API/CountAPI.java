package API;

import VO.Rest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/count")
public class CountAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();

        Rest rest = new Rest();
        int count = Integer.parseInt(context.getAttribute("count").toString());

        rest.toRestInt(0, count, "获取网站访问人数成功");

        response.getWriter().write(rest.toJSONString());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();

        Rest rest = new Rest();
        int count = Integer.parseInt(context.getAttribute("count").toString());
        count++;
        context.setAttribute("count", count);

        rest.toRestInt(0, count, "添加网站访问次数成功");

        response.getWriter().write(rest.toJSONString());

    }
}
