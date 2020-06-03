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

    //添加商品
    @Override
    public Product createProduct(Product product) throws Exception {
        Product obj = null;

        String sql = "insert into product (name,description,price,category) values(?,?,?,?)";
        try {

            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, product.getName());
            this.pstmt.setString(2, product.getDescription());
            this.pstmt.setInt(3, product.getPrice());
            this.pstmt.setString(4, product.getCategory());


            if (this.pstmt.executeUpdate() > 0) {
                obj = product;
                obj.setId(getLastID());
            } else {
                System.out.println("添加商品失败");
            }
        } catch (SQLException e) {
            throw e;
        }
        return obj;
    }

    @Override
    public boolean deleteProduct(int id) throws Exception {
        boolean flag = false;

        String sql = "delete from product where id = ?";
        try {

            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setInt(1, id);

            if (this.pstmt.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("删除商品失败");
            }
        } catch (SQLException e) {
            throw e;
        }
        return flag;
    }

    @Override
    public Product updateProduct(Product product) throws Exception {
        Product obj = null;

        String sql = "update product set name = ?, description = ?, price = ?, category = ? where id = ?";
        try {

            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, product.getName());
            this.pstmt.setString(2, product.getDescription());
            this.pstmt.setInt(3, product.getPrice());
            this.pstmt.setString(4, product.getCategory());
            this.pstmt.setInt(5, product.getId());


            if (this.pstmt.executeUpdate() > 0) {
                obj = product;
            } else {
                System.out.println("添加商品失败");
            }
        } catch (SQLException e) {
            throw e;
        }
        return obj;
    }

    // 查看某一页的商品信息
    @Override
    public List<Product> getProducts(int page, int capacity) throws Exception {

        int startLine = (page - 1) * capacity;
        int endLine = startLine + capacity;

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
    public List<Product> getProductsByCategory(int page, int capacity, String category) throws Exception {


        int startLine = (page - 1) * capacity;
        int endLine = startLine + capacity;

        List<Product> products = new ArrayList<Product>();
        Product product = null;
        String sql = "SELECT * FROM product WHERE category = ? LIMIT ? ,?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, category);
            this.pstmt.setInt(2, startLine);
            this.pstmt.setInt(3, endLine);
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
    public int getLastID() throws SQLException {
        int id = -1;
        String sql = "select max(id) from product";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        this.pstmt.close();
        return id;
    }
}
