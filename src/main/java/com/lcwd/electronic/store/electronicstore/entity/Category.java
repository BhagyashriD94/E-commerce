package com.lcwd.electronic.store.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();


}
