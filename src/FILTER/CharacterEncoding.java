package FILTER;

import java.io.IOException;

import javax.servlet.*;

public class CharacterEncoding implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //替换原生Request
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("Encoding Filter");
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig conf) throws ServletException {
        System.out.println("Encoding Filter staring...");
    }

    public void destroy() {}
}