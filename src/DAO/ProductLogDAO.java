package DAO;

import VO.ProductLog;

import java.sql.SQLException;
import java.util.List;

public interface ProductLogDAO {
    // 购买
    public boolean createProductLog(ProductLog productLog) throws SQLException;

    // 查看某段时间的购买记录
    public List<ProductLog> RetrieveProductLogs(String user_id, String start_time, String end_time);
}
