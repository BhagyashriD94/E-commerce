package com.lcwd.electronic.store.electronicstore.entity;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cart_item")
public class CartItem {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer cartItemId;
     @OneToOne
     @JoinColumn(name="product_id")
     private Product product;

     private Integer quantity;
     private Integer totalPrice;
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="cart_id")
     private Cart cart;

}
