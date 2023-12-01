package com.lcwd.electronic.store.electronicstore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productId;
    private String title;
    private String description;
    private double price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;


}
