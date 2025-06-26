package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

import java.math.BigDecimal;

public interface OrderDao {

    Order placeOrder(Order order);

}
