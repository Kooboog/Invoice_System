package models;

import java.sql.SQLException;
import java.util.List;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO;
    private final ProductDAO productDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
        this.productDAO = new ProductDAO();
    }

    public void createInvoice(Invoice invoice) throws SQLException {
        // Calculate total amount
        double totalAmount = 0.0;
        for (InvoiceItem item : invoice.getItems()) {
            Product product = productDAO.getProductById(item.getProductId());
            if (product != null) {
                item.setPrice(product.getPrice());
                totalAmount += item.getPrice() * item.getQuantity();
                // Decrease stock
                product.setStock(product.getStock() - item.getQuantity());
                productDAO.updateProduct(product);
            } else {
                throw new SQLException("Product not found with ID: " + item.getProductId());
            }
        }
        invoice.setTotalAmount(totalAmount);
        invoiceDAO.addInvoice(invoice);
    }

    public List<Invoice> getAllInvoices() throws SQLException {
        return invoiceDAO.getAllInvoices();
    }

    public List<Invoice> getUnpaidInvoices() throws SQLException {
        return invoiceDAO.getUnpaidInvoices();
    }
}
