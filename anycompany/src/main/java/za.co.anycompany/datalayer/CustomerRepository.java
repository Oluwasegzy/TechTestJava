package za.co.anycompany.datalayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import za.co.anycompany.model.Customer;

import java.sql.*;
import za.co.anycompany.model.Order;


public class CustomerRepository extends BaseRepository{

    public static Customer load(long customerId,boolean lazy) {
        Connection con = getDBConnection();
        PreparedStatement prpstmt = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Customer customer = new Customer();
        try {
            statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMER (customer_Id bigint primary key not null, name varchar(120), country varchar(120), date_of_birth date)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ORDER` (order_Id int primary key not null, amount decimal(10,2), vat decimal(3,1), customer_id bigint)");
            prpstmt = con.prepareStatement("select * from CUSTOMER where customer_Id = ?");
            prpstmt.setLong(1, customerId);
            resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(customerId);
                customer.setName(resultSet.getString("NAME"));
                customer.setCountry(resultSet.getString("COUNTRY"));
                customer.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
            }
             if (!lazy) {
                 prpstmt = con.prepareStatement("select * from `ORDER` where customer_Id = ?");
                 prpstmt.setLong(1, customerId);
                 resultSet = prpstmt.executeQuery();
                 List<Order> orders = new ArrayList<Order>();
                 while (resultSet.next()) {
                     Order order  = new Order();
                     order.setAmount(resultSet.getDouble("AMOUNT"));
                     order.setVAT(resultSet.getDouble("VAT"));
                     order.setOrderId(resultSet.getLong("ORDER_ID"));
                     order.setCustomerId(customerId);
                     orders.add(order);
                 }
                 customer.setOrders(orders);
             }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (prpstmt != null)
                    prpstmt.close();
                if (resultSet != null)
                    resultSet.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return customer;
    }

    public static List<Customer> loadAll(boolean lazy) {
        Connection con = getDBConnection();
        PreparedStatement prpstmt = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet_ = null;
        List<Customer> customers = new ArrayList<Customer>();
        try {
            statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMER (customer_Id bigint primary key not null, name varchar(120), country varchar(120), date_of_birth date)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ORDER` (order_Id int primary key not null, amount decimal(10,2), vat decimal(3,1), customer_id bigint)");
            prpstmt = con.prepareStatement("select * from CUSTOMER");
            resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setName(resultSet.getString("NAME"));
                customer.setCountry(resultSet.getString("COUNTRY"));
                customer.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
                customer.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
                if (!lazy) {
                    prpstmt = con.prepareStatement("select * from `ORDER` where customer_Id = ?");
                    prpstmt.setLong(1, customer.getCustomerId());
                    resultSet_ = prpstmt.executeQuery();
                    List<Order> orders = new ArrayList<Order>();
                    while (resultSet_.next()) {
                        Order order  = new Order();
                        order.setAmount(resultSet_.getDouble("AMOUNT"));
                        order.setVAT(resultSet_.getDouble("VAT"));
                        order.setOrderId(resultSet_.getLong("ORDER_ID"));
                        order.setCustomerId(customer.getCustomerId());
                        orders.add(order);
                    }
                    customer.setOrders(orders);
                }
                 customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (prpstmt != null)
                    prpstmt.close();
                if (resultSet != null)
                    resultSet.close();
                if (resultSet_ != null)
                    resultSet_.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return customers;
    }


    public void save(Customer customer) {
        Connection connection = getDBConnection();
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMER (customer_Id bigint primary key not null, name varchar(120), country varchar(120), date_of_birth date)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ORDER` (order_Id int primary key not null, amount decimal(10,2), vat decimal(3,1), customer_id bigint)");
            preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMER (customer_id, name, country, date_of_birth) VALUES(?,?,?,?)");
            preparedStatement.setLong(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getCountry());
            Calendar cal = Calendar.getInstance();
            cal.setTime(customer.getDateOfBirth());
            String sqlDate = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
            preparedStatement.setDate(4, java.sql.Date.valueOf(sqlDate));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null)
                statement.close();
                if (preparedStatement != null)
                preparedStatement.close();
                if (connection != null)
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
