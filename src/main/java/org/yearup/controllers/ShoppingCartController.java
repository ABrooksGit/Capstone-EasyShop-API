package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged-in users should have access to these actions
@RestController
@RequestMapping("cart")
@CrossOrigin
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;




    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }


    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged-in username

            String userName = principal.getName();
            // find database user by userId
            int userId = userDao.getIdByUsername(userName);


            // use the shoppingcartDao to get all items in the cart and return the cart

            return shoppingCartDao.getByUserId(userId);

        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/products/{productId}") //From Request Mapping, go to products then another slash to get the productId

    public ShoppingCart addToCart(@PathVariable int productId, Principal principal){

        try {

            //From principal(The authentication get the name of the current user)
            String userName = principal.getName();

            // create an int of userId to attach it to the userDao.
            int userId = userDao.getIdByUsername(userName);

            //get the shopping cart and add the items using the created userId and the PathVariable of productId
            shoppingCartDao.addToCart(userId, productId);


            return shoppingCartDao.getByUserId(userId);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }


    }





    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/products/{productId}")
    public ShoppingCart addQuantity(@PathVariable int productId,  Principal principal, @RequestBody ShoppingCartItem shoppingCartItem){

        //Gets the userName from principal
        String userName = principal.getName();

        //Attaches the userId to userDao from the userName we got from the principal.
        int userId = userDao.getIdByUsername(userName);

        //Attaches an int quantity to the shoppingCartItem and gets the quantity of that item
        int quantity = shoppingCartItem.getQuantity();

        //Passing through the add, which adds the quantity for the productId of choice
        shoppingCartDao.addQuantity(userId, productId, quantity);

        //Returns the userId
        return shoppingCartDao.getByUserId(userId);


    }






    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping
    public ShoppingCart deleteCart(Principal principal){

        try {
            String userName = principal.getName();

            int userId = userDao.getIdByUsername(userName);

            //Instead of returning the shopping cart, we delete the contents inside of it
            return shoppingCartDao.deleteFromCart(userId);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


    }





}
