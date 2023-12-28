package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.electronicstore.dtos.OrderDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.entity.*;
import com.lcwd.electronic.store.electronicstore.exception.BadApiRequest;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.helper.Helper;
import com.lcwd.electronic.store.electronicstore.repository.CartRepository;
import com.lcwd.electronic.store.electronicstore.repository.OrderRepository;
import com.lcwd.electronic.store.electronicstore.repository.UserRepository;
import com.lcwd.electronic.store.electronicstore.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
            String userId=orderDto.getUserId();
            String cardId=orderDto.getCardId();
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        Cart cart = this.cartRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        List<CartItem> cartitem = cart.getItem();
        if (cartitem.size()<=0){
            throw new BadApiRequest("Invalid number of item in cart");
        }
        Order order = Order.builder().orderId(UUID.randomUUID().toString())
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .user(user)
                .build();
        AtomicReference<Double> orderAmount=new AtomicReference<>(0.0);
        List<OrderItem> orderItem=cartitem.stream().map(cartItem -> {
            OrderItem orderItem1=OrderItem.builder().quantity(cartItem.getQuantity()).product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDescountprice())
                    .order(order).build();
            orderAmount.set(orderAmount.get()+orderItem1.getTotalPrice());
            return orderItem1;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItem);
        order.setOrderAmount(orderAmount.get());
        cart.getItem().clear();
        cartRepository.save(cart);
        Order savedorder = orderRepository.save(order);
        return this.modelMapper.map(savedorder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDto;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort=(sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable p=PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(p);
        return Helper.getPageableResponse(page,OrderDto.class);
    }
}
