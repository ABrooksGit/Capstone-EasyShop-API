package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void placeOrder(int orderId, int userId, String address, String city, String zip, BigDecimal shippingAmount) {

        Order order = new Order();
        String sql = """
                insert into orders
                (order_id, user_id, `date`, address, city, state, zip, shipping_amount)
                values(?, ?, ?, ?, ?, ?, ?, ?);

                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setInt(2, userId);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(4, order.getAddress());
            preparedStatement.setString(5, order.getCity());
            preparedStatement.setString(6, order.getZip());
            preparedStatement.setBigDecimal(7, order.getShippingAmount());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
