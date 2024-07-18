package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    // Method to add an invoice to the database
    public void addInvoice(Invoice invoice) throws SQLException {
        String invoiceQuery = "INSERT INTO Invoices (customer_id, total_amount, paid) VALUES (?, ?, ?)";
        String itemQuery = "INSERT INTO InvoiceItems (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement invoiceStmt = connection.prepareStatement(invoiceQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {

            connection.setAutoCommit(false); // Begin transaction
            
            // Insert invoice
            invoiceStmt.setInt(1, invoice.getCustomerId());
            invoiceStmt.setDouble(2, invoice.getTotalAmount());
            invoiceStmt.setBoolean(3, invoice.isPaid());
            invoiceStmt.executeUpdate();

            ResultSet generatedKeys = invoiceStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int invoiceId = generatedKeys.getInt(1);
                invoice.setInvoiceId(invoiceId);

                // Insert invoice items
                for (InvoiceItem item : invoice.getItems()) {
                    itemStmt.setInt(1, invoiceId);
                    itemStmt.setInt(2, item.getProductId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to get all invoices
    public List<Invoice> getAllInvoices() throws SQLException {
        String query = "SELECT * FROM Invoices";
        List<Invoice> invoices = new ArrayList<>();
        
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                invoice.setCustomerId(resultSet.getInt("customer_id"));
                invoice.setDate(resultSet.getTimestamp("date"));
                invoice.setTotalAmount(resultSet.getDouble("total_amount"));
                invoice.setPaid(resultSet.getBoolean("paid"));
                
                // Get items for this invoice
                invoice.setItems(getInvoiceItems(invoice.getInvoiceId()));
                
                invoices.add(invoice);
            }
        }
        return invoices;
    }

    // Method to get unpaid invoices
    public List<Invoice> getUnpaidInvoices() throws SQLException {
        String query = "SELECT * FROM Invoices WHERE paid = false";
        List<Invoice> invoices = new ArrayList<>();
        
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                invoice.setCustomerId(resultSet.getInt("customer_id"));
                invoice.setDate(resultSet.getTimestamp("date"));
                invoice.setTotalAmount(resultSet.getDouble("total_amount"));
                invoice.setPaid(false);
                
                // Get items for this invoice
                invoice.setItems(getInvoiceItems(invoice.getInvoiceId()));
                
                invoices.add(invoice);
            }
        }
        return invoices;
    }

    // Helper method to get items for a given invoice
    private List<InvoiceItem> getInvoiceItems(int invoiceId) throws SQLException {
        String query = "SELECT * FROM InvoiceItems WHERE invoice_id = ?";
        List<InvoiceItem> items = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, invoiceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                InvoiceItem item = new InvoiceItem();
                item.setInvoiceItemId(resultSet.getInt("invoice_item_id"));
                item.setInvoiceId(resultSet.getInt("invoice_id"));
                item.setProductId(resultSet.getInt("product_id"));
                item.setQuantity(resultSet.getInt("quantity"));
                item.setPrice(resultSet.getDouble("price"));
                items.add(item);
            }
        }
        return items;
    }
}

