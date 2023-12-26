package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.electronicstore.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemsToCartRequest request);

    void removeItemFromCart(String userId,Integer cartItem);

    void clearCart(String userId);

//    CartDto getCartByUser(String userId);


}
