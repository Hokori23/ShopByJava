package API;

import FACTORY.ProductLogFactory;
import FILTER.XssHttpServletRequestWrapper;
import FILTER.XssJSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.concurrent.atomic.AtomicBoolean;

import VO.ProductLog;
import VO.Rest;
import com.alibaba.fastjson.JSONObject;

@WebServlet("/productlog")
public class ProductLogAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 购买
         *
         * @param Array products
         *
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper) request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temProducts = bodyParams.get("products");
        Rest rest = new Rest();
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("id");
        try {
            // 判断参数进行业务逻辑处理
            if (temProducts == null) {
                rest.toRestMessage(1, "参数错误, {products:Array}");
            } else if (userID == null) {
                rest.toRestMessage(401, "请登录, Unauthorized");
            } else {
//                String ProductsJSON = JSONObject.toJSONString(temProducts);
                String ProductsJSON = temProducts.toString();
                System.out.println(ProductsJSON);
                ProductsJSON = ProductsJSON.replace("&quot;", "\"");
                System.out.println(ProductsJSON);
                ObjectMapper mapper = new ObjectMapper();
                List<ProductLog> productLogs = mapper.readValue(ProductsJSON, new TypeReference<List<ProductLog>>() {
                });


                AtomicBoolean flag = new AtomicBoolean(true);
                productLogs.forEach(item -> {
                    try {
                        item.setUser_id(userID);
                        ProductLogFactory.getDAOInstance().createProductLog(item);
                    } catch (Exception e) {
                        flag.set(false);
                        rest.toRestMessage(1, e.getMessage());
                    }
                });
                if (flag.get()) {
                    rest.toRestMessage(0, "购买成功");
                }
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        /**
         * 查看某段时间的购买记录
         *
         * @param String (start_time)
         * @param String (end_time)
         *
         */

        // 获取数据
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("id");

        // 判断参数进行业务逻辑处理
        Rest rest = new Rest();
        try {
            if (userID == null) {
                rest.toRestMessage(401, "请登录, Unauthorized");
            } else if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
                List<ProductLog> productLogs = ProductLogFactory.getDAOInstance().RetrieveAllProductLogs(userID);
                rest.toRestArray(0, productLogs, "查询购买记录成功");
            } else {
                List<ProductLog> productLogs = ProductLogFactory.getDAOInstance().RetrieveProductLogs(userID, startTime, endTime);
                rest.toRestArray(0, productLogs, "查询购买记录成功");
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

}
