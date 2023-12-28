package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.entity.OrderItem;
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
public class OrderDto {
    private String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private Double orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate=new Date();
    private Date deliveredDate;
    private UserDto user;
    private List<OrderItem> orderItems=new ArrayList<>();

}
