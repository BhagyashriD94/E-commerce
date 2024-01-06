package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.electronicstore.dtos.CartDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    Logger logger= LoggerFactory.getLogger(CartController.class);

    /**
     * @auther Bhagyashri
     * @param userId
     * @param request
     * @apiNote create api to addItem to cart
     * @return
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemsToCartRequest request) {
        logger.info("Entering the request to add Iteam into cart with userId:{}",userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        logger.info("completed the request to add Iteam into cart with userId:{}",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }
    /**
     * @auther Bhagyshri
     * @param userId
     * @param cartItemId
     * @apiNote create api to removeItem from cart
     * @return
     */
    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer cartItemId) {
        logger.info("Entering the request to remove Iteam into cart with userId:{}",userId);
        cartService.removeItemFromCart(userId, cartItemId);
        ApiResponse apiResponse = ApiResponse.builder().message("cartItem is removed successfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request to remove Iteam into cart with userId:{}",userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    /**
     * @auther Bhagyashri
     * @param userId
     * @apiNote  create api to clear all cart
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        logger.info("Entering the request to clear cart with userId:{}",userId);
        cartService.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().message("cart is empty now").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request to clear cart with userId:{}",userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    /**
     * @auther Bhagyashri
     * @param userId
     * @apiNote create api to retrived cart data by userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId) {
        logger.info("Entering the request to retrived cart with userId:{}",userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        logger.info("completed the request to retrived cart with userId:{}",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
