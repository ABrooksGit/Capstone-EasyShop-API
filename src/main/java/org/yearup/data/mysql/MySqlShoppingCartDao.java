package org.yearup.data.mysql;


import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {


    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public ShoppingCart getByUserId(int userId) {

        ShoppingCart shoppingCart = new ShoppingCart();
        String sql = """
                Select sc.product_id, p.name, p.price, sc.quantity
                from shopping_cart sc
                join products p on sc.product_id = p.product_id
                where sc.user_id = ?
                
                
                
                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1,userId);


            try(ResultSet results = preparedStatement.executeQuery()){

                while(results.next()){
                    int productId = results.getInt(1);
                    String productName = results.getString(2);
                    BigDecimal price = results.getBigDecimal(3);
                    int quantity = results.getInt(4);


                    Product product = new Product();
                    product.setProductId(productId);
                    product.setName(productName);
                    product.setPrice(price);

                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setProduct(product);
                    shoppingCartItem.setQuantity(quantity);


                    shoppingCart.add(shoppingCartItem);


                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        return shoppingCart;


    }

    @Override
    public void addToCart() {

    }

    @Override
    public void deleteFromCart() {

    }
}
