package com.lcwd.electronic.store.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="categories")
public class Category {
    @Id
     private String categoryId;
    @Column(name="category_title")
     private String title;
    @Column(name="category_description")
     private String description;
    @Column(name="category_cover_image")
     private String coverImage;

}
