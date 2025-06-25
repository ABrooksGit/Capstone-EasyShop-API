package org.yearup.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.*;
import org.yearup.models.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {

    private UserDao userDao;
    private ProductDao productDao;
    private ShoppingCartDao shoppingCartDao;
    private ProfileDao profileDao;
    private Profile profile;
    private OrderDao orderDao;
    private ShoppingCart shoppingCart;




    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Order placeOrder(Principal principal){

        //get the user Id
        String userName = principal.getName();
        int userId = userDao.getIdByUsername(userName);
        //create a new order record in the orders table.
        Order order = new Order();
        order.setUser_Id(userId);
        order.setDate(LocalDate.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        // get back the order id
        order.setOrderId(order.getOrderId());



        //get the users shopping cart entries
        shoppingCartDao.getByUserId(userId);

        //loop through each shopping cart entry
        Map<Integer, ShoppingCartItem> shoppingCartItemMap = shoppingCart.getItems();

        //for each one, add a record to the OrderLineItem table using the order id of the above order, and the
        //product id of the shopping cart row we are looping through
        shoppingCartItemMap.values().forEach(shoppingCartItem -> {
            OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setOrderLineItemId(order.getOrderId());
            orderLineItem.setProductId(shoppingCartItem.getProductId());
            orderLineItem.setSalesPrice(shoppingCartItem.getLineTotal());
            orderLineItem.setQuantity(shoppingCartItem.getQuantity());
            orderLineItem.setDiscount(shoppingCartItem.getDiscountPercent());
            orderDao.placeOrder(userId, order.getOrderId(), order.getAddress(), order.getCity(), order.getState(),order.getShippingAmount());
        });



        //at this point we should have the order and the order items recorded...

        //only one thing left to do

        //clear the cart.


        return order;
    }








}
