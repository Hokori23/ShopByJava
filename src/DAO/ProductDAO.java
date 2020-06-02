package DAO;

import VO.Product;

import java.util.List;

public interface ProductDAO {
    //查看某一页的商品信息
    public List<Product> getProducts(int page, int opacity) throws Exception;

    //查看某一分类某一页的商品信息
    public List<Product> getProductsByCategory(int page, int opacity, String category) throws Exception;


}
