package org.yearup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.*;
import org.yearup.models.*;

import java.security.Principal;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {

    private UserDao userDao;
    private ProductDao productDao;
    private ShoppingCartDao shoppingCartDao;
    private ProfileDao profileDao;
    private OrderDao orderDao;
    private OrderLineDao orderLineDao;



    @Autowired
    public OrdersController(UserDao userDao, ProductDao productDao, ShoppingCartDao shoppingCartDao, ProfileDao profileDao, OrderDao orderDao, OrderLineDao orderLineDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.shoppingCartDao = shoppingCartDao;
        this.profileDao = profileDao;
        this.orderDao = orderDao;
        this.orderLineDao = orderLineDao;

    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Order placeOrder(Order order, Principal principal){

        List<OrderLineItem> lineItems = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

        //get the user Id
        String userName = principal.getName();
        int userId = userDao.getIdByUsername(userName);
        //create a new order record in the orders table.

        OrderLineItem orderLineItem = new OrderLineItem();
        order.setUser_Id(userId);
        order.setDate(LocalDateTime.now());
        order.setAddress(profileDao.getProfile(userId).getAddress());
        order.setCity(profileDao.getProfile(userId).getCity());
        order.setState(profileDao.getProfile(userId).getState());
        order.setZip(profileDao.getProfile(userId).getZip());
        order.setShippingAmount(shoppingCartDao.getByUserId(userId).getTotal());
        orderDao.placeOrder(order);


        // get back the order id
        int orderId = order.getOrderId();

        //get the users shopping cart entries
        Map<Integer, ShoppingCartItem> length = shoppingCartDao.getByUserId(userId).getItems();

        //loop through each shopping cart entry
        //for each one, add a record to the OrderLineItem table using the order id of the above order, and the
        //product id of the shopping cart row we are looping through
        length.values().forEach(shoppingCartItem -> {
            orderLineItem.setOrderId(orderId);
            orderLineItem.setProductId(shoppingCartItem.getProductId());
            orderLineItem.setSalesPrice(shoppingCartItem.getLineTotal());
            orderLineItem.setQuantity(shoppingCartItem.getQuantity());
            orderLineItem.setDiscount(shoppingCartItem.getDiscountPercent());
            orderLineItem.setQuantity(length.size());
            orderLineDao.createOrderLine(orderLineItem);
            lineItems.add(orderLineItem);



        });


        order.setLineItems(lineItems);
        //at this point we should have the order and the order items recorded...
        //only one thing left to do
        //clear the cart.
        shoppingCartDao.deleteFromCart(userId);
        return order;
    }




}
