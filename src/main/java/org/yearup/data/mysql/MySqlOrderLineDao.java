package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderLineDao;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlOrderLineDao extends MySqlDaoBase implements OrderLineDao {


    public MySqlOrderLineDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void createOrderLine(OrderLineItem orderLineItem){
        //take the values from orderLineItem, which should already have the orderid, and add this to the orderlineitem table

        String sql = """
            INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the prepared statement
            preparedStatement.setInt(1, orderLineItem.getOrderId());
            preparedStatement.setInt(2, orderLineItem.getProductId());
            preparedStatement.setBigDecimal(3, orderLineItem.getSalesPrice());
            preparedStatement.setInt(4, orderLineItem.getQuantity());
            preparedStatement.setBigDecimal(5, orderLineItem.getDiscount());

            // Execute the update to insert the record into the table
            int rows = preparedStatement.executeUpdate();


            if (rows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        orderLineItem.setOrderLineItemId(keys.getInt(1));

                    }
                }


        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
