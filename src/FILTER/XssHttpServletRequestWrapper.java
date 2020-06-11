package FILTER;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }

    public XssJSONObject postParams(BufferedReader reader) throws IOException {
        String str = "";
        String body = "";
        while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
            body += str;
        }
        return new XssJSONObject(JSONObject.parseObject(body));
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成HTML编码
     *
     * @return
     * @String s
     */
    private static String xssEncode(String s) {
        return XssFunction.xssEncode(s);
    }
}