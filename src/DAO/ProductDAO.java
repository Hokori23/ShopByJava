package DAO;

import VO.Product;

import java.util.List;

public interface ProductDAO {
    //添加商品
    public Product createProduct(Product product) throws Exception;

    //删除商品
    public boolean deleteProduct(int id) throws Exception;

    //编辑商品
    public Product updateProduct(Product product) throws Exception;

    //查看某一页的商品信息
    public List<Product> getProducts(int page, int capacity) throws Exception;

    //查看某一分类某一页的商品信息
    public List<Product> getProductsByCategory(int page, int capacity, String category) throws Exception;

    //获取最后一个商品ID
    public int getLastID() throws Exception;
}
