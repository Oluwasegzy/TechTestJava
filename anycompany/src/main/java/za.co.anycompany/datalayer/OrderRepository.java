package za.co.anycompany.datalayer;

import za.co.anycompany.model.Order;

import java.sql.*;

public class OrderRepository extends BaseRepository{


    public void save(Order order) {
        Connection connection = getDBConnection();
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMER (customer_Id bigint primary key not null, name varchar(120), country varchar(120), date_of_birth date)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ORDER` (order_Id int primary key not null, amount decimal(10,2), vat decimal(3,1), customer_id bigint)");
            preparedStatement = connection.prepareStatement("INSERT INTO `ORDER` (order_Id, amount, vat, customer_id) VALUES(?,?,?,?)");
            preparedStatement.setLong(1, order.getOrderId());
            preparedStatement.setDouble(2, order.getAmount());
            preparedStatement.setDouble(3, order.getVAT());
            preparedStatement.setDouble(4, order.getCustomerId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                statement.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
