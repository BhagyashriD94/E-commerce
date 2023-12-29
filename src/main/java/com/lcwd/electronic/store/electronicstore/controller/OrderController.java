package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.electronicstore.dtos.OrderDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest orderDto){
        OrderDto order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeorder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        ApiResponse apiResponse = ApiResponse.builder().message("order remove sucessfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId){
        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }
    @GetMapping("/orders")
    public ResponseEntity<PageableResponse<OrderDto>> getOrderByUser(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
                                                                     @RequestParam(value ="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
                                                                     @RequestParam(value ="sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
                                                                     @RequestParam(value ="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir)
    {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
