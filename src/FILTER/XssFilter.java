package FILTER;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //替换原生Request
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        System.out.println("Xss Filter");
        filterChain.doFilter(xssRequest, response);
    }

    public void init(FilterConfig conf) throws ServletException {
        System.out.println("PreventXSSInject Filter staring...");
        System.out.println();
    }

    public void destroy() {}
}