package com.lcwd.electronic.store.electronicstore.repository;

import com.lcwd.electronic.store.electronicstore.entity.Cart;
import com.lcwd.electronic.store.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

   Optional<Cart> findByUser(User user);
}
