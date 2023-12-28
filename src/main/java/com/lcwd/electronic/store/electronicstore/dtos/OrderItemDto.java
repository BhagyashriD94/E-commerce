package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.entity.Order;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Integer orderItemId;
    private Integer quantity;
    private Double totalPrice;
    private ProductDto product;
}
