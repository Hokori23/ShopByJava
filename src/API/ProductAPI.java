package API;

import FACTORY.ProductFactory;
import FILTER.XssHttpServletRequestWrapper;
import FILTER.XssJSONObject;
import SERVICE.ProductService;
import VO.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import VO.Rest;

@WebServlet("/product")
public class ProductAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 添加商品
         *
         * @param String name
         * @param String (description)
         * @param int price
         * @param String (category)
         *
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper) request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temName = bodyParams.get("name");
        Object temDescription = bodyParams.get("description");
        Object temPrice = bodyParams.get("price");
        Object temCategory = bodyParams.get("category");
        Rest rest = new Rest();
        try {
            // 判断参数进行业务逻辑处理
            if (temName == null || temPrice == null) {
                rest.toRestMessage(1, "参数错误, {name, price, (description, category)}");
            } else {
                String name = temName.toString();
                int price = Integer.parseInt(temPrice.toString());
                String description = null;
                if (temDescription != null) {
                    description = temDescription.toString();
                }
                String category = null;
                if (temCategory != null) {
                    category = temCategory.toString();
                }
                Product product = new Product(0, name, description, price, category);
                ProductFactory.getDAOInstance().createProduct(product, (String) request.getSession().getAttribute("id"), rest);
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        /**
         * 查看某一页的商品信息/查看某一分类某一页的商品信息
         *
         * @param int page
         * @param int capacity
         * @param String (category)
         *
         */
        String pageStr = request.getParameter("page");
        String capacityStr = request.getParameter("capacity");
        String category = request.getParameter("category");
        System.out.println(pageStr + "------" + capacityStr);
        // 判断参数进行业务逻辑处理
        Rest rest = new Rest();
        try {
            if ((pageStr == null || pageStr.isEmpty()) && (capacityStr == null || capacityStr.isEmpty())) {
                System.out.println("getAllProducts");
                ProductFactory.getDAOInstance().getAllProducts(rest);
            } else if (category == null || category.isEmpty()) {
                System.out.println("getProducts");
                int page = Integer.parseInt(pageStr);
                int capacity = Integer.parseInt(capacityStr);
                List<Product> products = ProductFactory.getDAOInstance().getProducts(page, capacity, (String) request.getSession().getAttribute("id"), rest);
            } else {
                System.out.println("getProductsByCategory");
                int page = Integer.parseInt(pageStr);
                int capacity = Integer.parseInt(capacityStr);
                List<Product> products = ProductFactory.getDAOInstance().getProductsByCategory(page, capacity, category, (String) request.getSession().getAttribute("id"), rest);
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
         *
         * @param String id
         *
         */

        // 获取数据
        String id = request.getParameter("id");

        Rest rest = new Rest();
        if (id == null || id.isEmpty()) {
            rest.toRestMessage(1, "参数错误, {id}");
        } else {
            try {
                ProductService.getDAOInstance().deleteProduct(Integer.parseInt(id), (String) request.getSession().getAttribute("id"), rest);
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
         * @param int id
         * @param String name
         * @param String (description)
         * @param int price
         * @param String (category)
         *
         */

        // 获取数据
        XssHttpServletRequestWrapper XssRequest = (XssHttpServletRequestWrapper) request;
        XssJSONObject bodyParams = XssRequest.postParams(new BufferedReader(new InputStreamReader(request.getInputStream())));

        Object temId = bodyParams.get("id");
        Object temName = bodyParams.get("name");
        Object temDescription = bodyParams.get("description");
        Object temPrice = bodyParams.get("price");
        Object temCategory = bodyParams.get("category");

        Rest rest = new Rest();
        try {
            // 判断参数进行业务逻辑处理
            if (temId == null || temName == null || temPrice == null) {
                rest.toRestMessage(1, "参数错误, {id, name, price, (description, category)}");
            } else {
                int id = Integer.parseInt(temId.toString());
                String name = temName.toString();
                String description = "";
                if (temDescription != null) {
                    description = temDescription.toString();
                }
                int price = Integer.parseInt(temPrice.toString());
                String category = "";
                if (temCategory != null) {
                    category = temCategory.toString();
                }
                Product product = new Product(id, name, description, price, category);
                ProductFactory.getDAOInstance().updateProduct(product, (String) request.getSession().getAttribute("id"), rest);
            }
        } catch (Exception e) {
            rest.toRestMessage(1, e.getMessage());
        }
        response.getWriter().write(rest.toJSONString());
    }

}
