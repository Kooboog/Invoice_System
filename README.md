# Invoice_System Project
Overview
This project is a simple invoice management system implemented in Java using JDBC and MySQL. The system allows the owner to manage customers, products, and invoices. It also provides functionalities to view and manage invoices and generate various sales reports.

Features
-Add and manage customers
-Add and manage products
-Create and manage invoices
-View all invoices
-View unpaid invoices
-Print invoice details
-View top-selling items in a given date range
-View highest sold product in a given range (month/year)
-View total units of product sold


# Table Designs
## Customers
customer_id (INT, Primary Key, Auto Increment)
name (VARCHAR(100))
phone (VARCHAR(15))

# Products
product_id (INT, Primary Key, Auto Increment)
name (VARCHAR(100))
price (DOUBLE)
stock (INT)

# Invoices
invoice_id (INT, Primary Key, Auto Increment)
customer_id (INT, Foreign Key)
date (TIMESTAMP)
total_amount (DOUBLE)
paid (BOOLEAN)

# InvoiceItems
invoice_item_id (INT, Primary Key, Auto Increment)
invoice_id (INT, Foreign Key)
product_id (INT, Foreign Key)
quantity (INT)
price (DOUBLE)


# Code Modules
1. Database Connection
DBConnection.java: Handles the connection to the MySQL database.

2. Model Classes
Customer.java: Represents a customer with fields customerId, name, and phone.
Product.java: Represents a product with fields productId, name, price, and stock.
Invoice.java: Represents an invoice with fields invoiceId, customerId, date, totalAmount, paid, and a list of InvoiceItem.
InvoiceItem.java: Represents an invoice item with fields invoiceItemId, invoiceId, productId, quantity, and price.

3. DAO Classes
CustomerDAO.java: Data Access Object for customer operations (add, get, update, delete, getAll).
ProductDAO.java: Data Access Object for product operations (add, get, update, delete, getAll).
InvoiceDAO.java: Data Access Object for invoice operations (add, getAll, getUnpaid, and getInvoiceItems).

4. Service Classes
InvoiceService.java: Contains business logic for creating invoices, calculating total amounts, and updating product stock.

5. Main Application
Main.java: The entry point of the application. It provides a menu-driven interface for interacting with the system.
