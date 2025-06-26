package org.yearup.data;

import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

import java.math.BigDecimal;

public interface OrderDao {

    void placeOrder(Profile profile, ShoppingCart shoppingCart);

}
