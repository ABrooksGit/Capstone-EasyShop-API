package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

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
    public void placeOrder(Profile profile, ShoppingCart shoppingCart) {

        Order order = new Order();
        String sql = """
                insert into orders
                (user_id, `date`, address, city, state, zip, shipping_amount)
                values(?, ?, ?, ?, ?, ?, ?);

                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
//            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setInt(1, profile.getUserId());
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getCity());
            preparedStatement.setString(5, order.getZip());
            preparedStatement.setBigDecimal(6, shoppingCart.getTotal());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
