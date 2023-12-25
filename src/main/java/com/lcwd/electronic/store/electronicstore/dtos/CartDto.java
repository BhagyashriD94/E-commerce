package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.entity.CartItem;
import com.lcwd.electronic.store.electronicstore.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartDto {

    private String cartId;
    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> item=new ArrayList<>();



}
