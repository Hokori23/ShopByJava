package DAO;

import VO.ProductLog;

import java.sql.SQLException;
import java.util.List;

public interface ProductLogDAO {
    // 购买
    public boolean createProductLog(ProductLog productLog) throws Exception;

    // 查看所有购买记录
    public List<ProductLog> RetrieveAllProductLogs(String user_id) throws Exception;

    // 查看某段时间的购买记录
    public List<ProductLog> RetrieveProductLogs(String user_id, String start_time, String end_time) throws Exception;
}
