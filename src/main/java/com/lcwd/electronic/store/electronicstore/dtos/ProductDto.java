package com.lcwd.electronic.store.electronicstore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String productId;
    @NotBlank
    @Size(min=2,max=10,message = "title must contain minimum 2 character")
    private String title;
    @Size(max=10000,message="description is required")
    private String description;
    @NotNull
    private double price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;


}
