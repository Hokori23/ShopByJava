package DAO;

import VO.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private Connection conn = null;
    private PreparedStatement pstmt = null;

    public ProductDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // 查看某一页的商品信息
    @Override
    public List<Product> getProducts(int page, int opacity) throws Exception {

        int startLine = (page - 1) * opacity;
        int endLine = startLine + opacity;

        List<Product> products = new ArrayList<Product>();
        Product product = null;
        String sql = "SELECT * FROM product LIMIT ? ,?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setInt(1, startLine);
            this.pstmt.setInt(2, endLine);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                products.add(product);
            }
        } catch (Exception e) {
            throw e;
        }
        this.pstmt.close();
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(int page, int opacity, String category) throws Exception {


        int startLine = (page - 1) * opacity;
        int endLine = startLine + opacity;

        List<Product> products = new ArrayList<Product>();
        Product product = null;
        String sql = "SELECT * FROM product LIMIT ? ,? WHERE category = ?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setInt(1, startLine);
            this.pstmt.setInt(2, endLine);
            this.pstmt.setString(3, category);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                products.add(product);
            }
        } catch (Exception e) {
            throw e;
        }
        this.pstmt.close();
        return products;
    }
}
