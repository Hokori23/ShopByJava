package SERVICE;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import DBConnection.DB;
import VO.Product;

import java.util.ArrayList;
import java.util.List;

import FACTORY.UserFactory;
import VO.Rest;
import VO.User;

public class ProductService {

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

    public boolean checkRole(String id, Rest rest) throws Exception {
        try {
            User user = UserFactory.getDAOInstance().getUserById(id);
            if (user.getRole() < 1) {
                rest.toRestMessage(403, "无权限, Forbidden");
                return false;
            } else {
                rest.setErrcode(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public Product createProduct(Product product, String operaterID, Rest rest) throws Exception {
        Product obj = null;
        try {
            if (checkRole(operaterID, rest)) {
                obj = this.dao.createProduct(product);
                if (obj != null) {
                    rest.toRestObject(0, obj, "添加商品成功");
                } else {
                    rest.toRestMessage(1, "添加商品失败");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return obj;
    }

    public boolean deleteProduct(int id, String operaterID, Rest rest) throws Exception {
        try {
            if (checkRole(operaterID, rest)) {
                if (this.dao.deleteProduct(id)) {
                    rest.toRestMessage(0, "删除商品成功");
                } else {
                    rest.toRestMessage(1, "删除商品失败");
                }
            }
            return false;
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
    }

    public Product updateProduct(Product product, String operaterID, Rest rest) throws Exception {
        Product obj = null;
        try {
            if (checkRole(operaterID, rest)) {
                obj = this.dao.updateProduct(product);
                if (obj != null) {
                    rest.toRestObject(0, obj, "编辑商品成功");
                } else {
                    rest.toRestMessage(1, "编辑商品失败");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return obj;
    }

    public List<Product> getProducts(int page, int opacity, String operaterID, Rest rest) throws Exception {
        List<Product> products = new ArrayList<Product>();
        try {
            if (checkRole(operaterID, rest)) {
                products = this.dao.getProducts(page, opacity);
                if (products != null) {
                    rest.toRestArray(0, products, "遍历成功");
                } else {
                    rest.toRestMessage(1, "无商品信息");
                }
            } else {
                products = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return products;
    }

    public List<Product> getProductsByCategory(int page, int opacity, String category, String operaterID,Rest rest) throws Exception {

        List<Product> products = new ArrayList<Product>();
        try {
            if (checkRole(operaterID, rest)) {
                products = this.dao.getProductsByCategory(page, opacity, category);
            } else {
                products = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return products;
    }
}
