package com.lcwd.electronic.store.electronicstore.repository;

import com.lcwd.electronic.store.electronicstore.entity.Order;
import com.lcwd.electronic.store.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);
}
