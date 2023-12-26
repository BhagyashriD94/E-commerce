package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.electronicstore.dtos.CartDto;
import com.lcwd.electronic.store.electronicstore.entity.Cart;
import com.lcwd.electronic.store.electronicstore.entity.CartItem;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import com.lcwd.electronic.store.electronicstore.entity.User;
import com.lcwd.electronic.store.electronicstore.exception.BadApiRequest;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.repository.CartItemRepository;
import com.lcwd.electronic.store.electronicstore.repository.CartRepository;
import com.lcwd.electronic.store.electronicstore.repository.ProductRepository;
import com.lcwd.electronic.store.electronicstore.repository.UserRepository;
import com.lcwd.electronic.store.electronicstore.service.CartService;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemsToCartRequest request) {
        String productId = request.getProductId();
        Integer quantity = request.getQuantity();
         if(quantity<=0){
             throw new BadApiRequest("Resource not valid");
        }
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        Cart cart = null;
        try{
          cart= cartRepository.findByUser(user).get();
        }catch (NoSuchElementException e){
         cart=new Cart();
         cart.setCartId(UUID.randomUUID().toString());
         cart.setCreatedAt(new Date());
        }
        AtomicReference<Boolean> updated=new AtomicReference<>(false);
        List<CartItem> items=cart.getItem();
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
             item.setQuantity(quantity);
             item.setTotalPrice(quantity*product.getPrice());
            updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
        if(!updated.get()) {
            CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getPrice()).cart(cart).product(product).build();
            cart.getItem().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedcart = cartRepository.save(cart);
        return modelMapper.map(updatedcart,CartDto.class);
    }
//    @Override
//    public void removeItemFromCart(String userId, Integer cartItem) {
//        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
//        cartItemRepository.delete(cartItem1);
//    }
//    @Override
//    public void clearCart(String userId) {
//        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
//        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
//        cart.getItem().clear();
//        cartRepository.save(cart);
//    }
//    @Override
//    public CartDto getCartByUser(String userId) {
//        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
//        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
//        return this.modelMapper.map(cart,CartDto.class);
//    }
}
