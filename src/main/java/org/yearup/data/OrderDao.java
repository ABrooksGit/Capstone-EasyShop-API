package org.yearup.data;

import java.math.BigDecimal;

public interface OrderDao {

    void placeOrder(int orderId, int userId, String address, String city, String zip, BigDecimal shippingAmount);

}
