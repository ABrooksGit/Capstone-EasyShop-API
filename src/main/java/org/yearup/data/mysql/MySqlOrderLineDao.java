package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderLineDao;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;

@Component
public class MySqlOrderLineDao extends MySqlDaoBase implements OrderLineDao {


    public MySqlOrderLineDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void orderDetails(int userId) {
        OrderLineItem orderLineItem = new OrderLineItem();

        String sql = """
                
                
                
                
                """;
    }
}
