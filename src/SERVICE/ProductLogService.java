package SERVICE;

import DAO.ProductLogDAO;
import DAO.ProductLogDAOImpl;
import DBConnection.DB;
import VO.ProductLog;

import java.util.ArrayList;
import java.util.List;

public class ProductLogService implements ProductLogDAO {

    private DB conn = null;
    private ProductLogDAO dao;

    public static ProductLogService getDAOInstance() throws Exception {
        return new ProductLogService();
    }

    public ProductLogService() throws Exception {
        // 创建一个MySQLConnection的对象
        this.conn = new DB();
        this.dao = new ProductLogDAOImpl(this.conn.getConnection());
    }


    @Override
    public boolean createProductLog(ProductLog productLog) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.createProductLog(productLog);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return flag;
    }

    @Override
    public List<ProductLog> RetrieveAllProductLogs(String user_id) throws Exception {
        List<ProductLog> productLogs = new ArrayList<ProductLog>();
        try {
            productLogs = this.dao.RetrieveAllProductLogs(user_id);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return productLogs;
    }

    @Override
    public List<ProductLog> RetrieveProductLogs(String user_id, String start_time, String end_time) throws Exception {
        List<ProductLog> productLogs = new ArrayList<ProductLog>();
        try {
            productLogs = this.dao.RetrieveProductLogs(user_id, start_time, end_time);
        } catch (Exception e) {
            throw e;
        } finally {
            this.conn.close();
        }
        return productLogs;
    }
}
