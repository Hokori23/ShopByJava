package DAO;

import VO.ProductLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductLogDAOImpl implements ProductLogDAO {

    private Connection conn = null;
    private PreparedStatement pstmt = null;

    public ProductLogDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // 购买
    @Override
    public boolean createProductLog(ProductLog productLog) throws SQLException {
        boolean flag = false;
        String sql = "INSERT INTO product_log (product_id, user_id, time, count) values(?,?,?,?)";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setInt(1, productLog.getProduct_id());
            this.pstmt.setString(2, productLog.getUser_id());
            this.pstmt.setString(3, productLog.getTime());
            this.pstmt.setInt(4, productLog.getCount());

            if (this.pstmt.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("购买失败");
            }
        } catch (SQLException e) {
            throw e;
        }
        this.pstmt.close();
        return flag;
    }

    // 查看某段时间的购买记录
    @Override
    public List<ProductLog> RetrieveAllProductLogs(String user_id) throws Exception {


        List<ProductLog> productLogs = new ArrayList<ProductLog>();
        ProductLog productLog = null;
        String sql = "SELECT * FROM product_log_view WHERE user_id = ? ";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, user_id);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                for (int i = 1; i < 9; i++) {
                    System.out.println(rs.getString(i));
                }
                productLog = new ProductLog(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8));
                productLogs.add(productLog);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }
        this.pstmt.close();
        return productLogs;
    }

    // 查看某段时间的购买记录
    @Override
    public List<ProductLog> RetrieveProductLogs(String user_id, String start_time, String end_time) throws Exception {


        List<ProductLog> productLogs = new ArrayList<ProductLog>();
        ProductLog productLog = null;
        String sql = "SELECT * FROM product_log_view WHERE user_id = ? AND (time BETWEEN ? AND ?)";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, user_id);
            this.pstmt.setString(2, start_time);
            this.pstmt.setString(3, end_time);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                for (int i = 1; i < 9; i++) {
                    System.out.println(rs.getString(i));
                }
                productLog = new ProductLog(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8));
                productLogs.add(productLog);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }
        this.pstmt.close();
        return productLogs;
    }
}
