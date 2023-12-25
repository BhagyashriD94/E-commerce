package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.entity.Cart;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Integer cartItemId;
    private ProductDto product;
    private Integer quantity;
    private Integer totalPrice;
}
