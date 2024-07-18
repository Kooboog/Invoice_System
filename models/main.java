package models;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        InvoiceService invoiceService = new InvoiceService();

        while (true) {
            System.out.println("1. Add Customer");
            System.out.println("2. Add Product");
            System.out.println("3. Create Invoice");
            System.out.println("4. View All Invoices");
            System.out.println("5. View Unpaid Invoices");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add Customer
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter phone:");
                    String phone = scanner.nextLine();
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setPhone(phone);
                    try {
                        customerDAO.addCustomer(customer);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // Add Product
                    System.out.println("Enter product name:");
                    String productName = scanner.nextLine();
                    System.out.println("Enter price:");
                    double price = scanner.nextDouble();
                    System.out.println("Enter stock:");
                    int stock = scanner.nextInt();
                    Product product = new Product();
                    product.setName(productName);
                    product.setPrice(price);
                    product.setStock(stock);
                    try {
                        productDAO.addProduct(product);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    // Create Invoice
                    System.out.println("Enter customer ID:");
                    int customerId = scanner.nextInt();
                    Invoice invoice = new Invoice();
                    invoice.setCustomerId(customerId);
                    // Add items to the invoice
                    // For simplicity, we'll assume the user knows the product IDs and quantities
                    // In a real application, you'd likely have a more complex UI for this
                    System.out.println("Enter number of items:");
                    int numItems = scanner.nextInt();
                    for (int i = 0; i < numItems; i++) {
                        System.out.println("Enter product ID:");
                        int productId = scanner.nextInt();
                        System.out.println("Enter quantity:");
                        int quantity = scanner.nextInt();
                        Product p = productDAO.getProductById(productId);
                        InvoiceItem item = new InvoiceItem();
                        item.setProductId(productId);
                        item.setQuantity(quantity);
                        item.setPrice(p.getPrice());
                        invoice.getItems().add(item);
                    }
                    try {
                        invoiceService.createInvoice(invoice);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    // View All Invoices
                    try {
                        List<Invoice> invoices = invoiceService.getAllInvoices();
                        invoices.forEach(System.out::println);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    // View Unpaid Invoices
                    try {
                        List<Invoice> unpaidInvoices = invoiceService.getUnpaidInvoices();
                        unpaidInvoices.forEach(System.out::println);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    // Exit
                    return;
            }
        }
    }
}
