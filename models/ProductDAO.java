package models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Method to add a product to the database
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO Products (name, price, stock) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getStock());
            preparedStatement.executeUpdate();
        }
    }

    // Method to get a product by its ID
    public Product getProductById(int productId) throws SQLException {
        String query = "SELECT * FROM Products WHERE product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                return product;
            }
        }
        return null;
    }

    // Method to update a product's details
    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE Products SET name = ?, price = ?, stock = ? WHERE product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getStock());
            preparedStatement.setInt(4, product.getProductId());
            preparedStatement.executeUpdate();
        }
    }

    // Method to delete a product by its ID
    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Products WHERE product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        }
    }

    // Method to get all products from the database
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                products.add(product);
            }
        }
        return products;
    }
}
