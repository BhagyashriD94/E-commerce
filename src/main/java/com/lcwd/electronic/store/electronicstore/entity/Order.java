package com.lcwd.electronic.store.electronicstore.entity;

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
@Entity
@Table(name="orders")
public class Order {
    @Id
    private String orderId;
    @Column(name="order_status")
    private String orderStatus;
    @Column(name="payment_status")
    private String paymentStatus;
    @Column(name="payment_amount")
    private Double orderAmount;
    @Column(name="billing_address")
    private String billingAddress;
    @Column(name="billing_phone")
    private String billingPhone;
    @Column(name="billing_name")
    private String billingName;
    @Column(name="ordered_date")
    private Date orderedDate;
    @Column(name="delivered_date")
    private Date deliveredDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "order",fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems=new ArrayList<>();


}
