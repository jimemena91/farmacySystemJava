package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductsDao {
    //instanciar la conexion

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

//registrar productos
    public boolean registerProductQuery(Products product) {
        String query = "INSERT INTO products (code, name, description, unit_price, created, updated, category_id, category_name)"
                + "VALUES (?,?,?,?,?,?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.setString(8, product.getCategory_name());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto");
            return false;
        }
    }

//listar productos
    public List listProductsQuery(String value) {
        List<Products> list_products = new ArrayList();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca"
                + "ON pro.category_id = ca.id WHERE pro.name LIKE ´%" + value + "%´";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return list_products;
    }

//modificar productos
    public boolean updateProductQuery(Products product) {
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, update = ?"
                + "category_id = ? WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del producto");
            return false;
        }
    }

//eliminar productos
    public boolean deleteProductQuery(int id) {
        String query = "DELETE FRom products WHERE id = " + id;

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar un producto que tenga relacion con otra tabla");
            return false;
        }
    }

//buscar producto
    public Products searchProduct(int id) {
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro "
                + "INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return product;
    }

//buscar producto por codigo
    public Products searchCode(int code) {
        String query = "SELECT pro.id, pro.name FROMS products pro WHERE pro.code = ?";
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return product;
    }

//Traer la cantidad de productos
    public Products searchId(int id) {
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                product.setProduct_quantity(rs.getInt("product_quantity"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }

//Actualizar stock
    public boolean updateStockQuery(int amount, int product_id) {
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }

    }
}
