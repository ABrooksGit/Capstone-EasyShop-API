package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;


import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Order placeOrder(Order order){
        // do the sql insert, get back the ID, set the ID of the order object and return that.
        String sql = """
                insert into orders
                (user_id, `date`, address, city, state, zip, shipping_amount)
                values(?, ?, ?, ?, ?, ?, ?)
                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            preparedStatement.setInt(1, order.getUser_Id());
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getCity());
            preparedStatement.setString(5,order.getState());
            preparedStatement.setString(6, order.getZip());
            preparedStatement.setBigDecimal(7, order.getShippingAmount());

            int rows = preparedStatement.executeUpdate();

            try(ResultSet keys = preparedStatement.getGeneratedKeys()){

                if(keys.next()){
                    order.setOrderId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return order;
    }
}
