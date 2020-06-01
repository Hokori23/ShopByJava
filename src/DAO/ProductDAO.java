package DAO;

import VO.Product;

import java.util.List;

public interface ProductDAO {
    //遍历
    public List<Product> getProducts(int page, int opacity) throws Exception;
}
