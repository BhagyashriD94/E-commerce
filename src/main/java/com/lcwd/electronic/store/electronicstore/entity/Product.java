package com.lcwd.electronic.store.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    private String productId;
    @Column(name="product_title")
    private String title;
    @Column(name="product_description")
    private String description;
    @Column(name="product_price")
    private double price;
    @Column(name="product_quantity")
    private Integer quantity;
    @Column(name="product_added_date")
    private Date addedDate;
    private boolean live;
    private boolean stock;
    @Column(name="descount_price")
    private double descountprice;
    @Column(name="product_image")
    private String productImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;



}
