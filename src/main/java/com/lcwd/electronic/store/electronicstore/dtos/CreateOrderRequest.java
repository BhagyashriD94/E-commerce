package com.lcwd.electronic.store.electronicstore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private String userId;
    private String cardId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
