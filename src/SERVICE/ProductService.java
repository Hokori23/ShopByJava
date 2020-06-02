package SERVICE;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import DBConnection.DB;
import VO.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements ProductDAO {

    private DB conn = null;
    private ProductDAO dao;

    public static ProductService getDAOInstance() throws Exception {
        return new ProductService();
    }

    public ProductService() throws Exception {
        // 创建一个MySQLConnection的对象
        this.conn = new DB();
        this.dao = new ProductDAOImpl(this.conn.getConnection());
    }

    @Override
    public List<Product> getProducts(int page, int opacity) throws Exception {
        List<Product> products = new ArrayList<Product>();
        try {
            products = this.dao.getProducts(page, opacity);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(int page, int opacity, String category) throws Exception {

        List<Product> products = new ArrayList<Product>();
        try {
            products = this.dao.getProductsByCategory(page, opacity, category);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return products;
    }
}
