package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.electronicstore.dtos.OrderDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger= LoggerFactory.getLogger(OrderController.class);

    /**
     * @auther Bhagyashri
     * @param request
     * @apiNote create api to save order data into database
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        logger.info("Entering the request to save order data");
        OrderDto order = orderService.createOrder(request);
        logger.info("completed the request to save order data");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    /**
     * @auther Bhagyashri
     * @param orderId
     * @apiNote create api to order data into database
     * @return
     */
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeorder(@PathVariable String orderId) {
        logger.info("Entering the request to remove order data with orderId:{}",orderId);
        orderService.removeOrder(orderId);
        ApiResponse apiResponse = ApiResponse.builder().message("order remove sucessfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request to remove order data with orderId:{}",orderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    /**
     * @auther Bhagyashri
     * @param userId
     * @apiNote create api to retrived order data into database into userId
     * @return
     */
    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId) {
        logger.info("Entering the request to retrived order data by user with userId:{}",userId);
        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        logger.info("completed the request to retrived order data by user with userId:{}",userId);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
    }
    /**
     * @auther Bhagyashri
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @apiNote create api to retrived all order data from database
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<PageableResponse<OrderDto>> getOrderByUser(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("Entering the request to retrived all order data by user");
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request to retrived all order data by user");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
