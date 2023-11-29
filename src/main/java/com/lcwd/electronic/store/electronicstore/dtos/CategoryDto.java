package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.validation.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @NotBlank(message="write something in title")
    @Size(max=100,message = "title containing maximum 100 character")
    private String title;
    @NotBlank
    @Size(max=1000,message = "write a description in max 1000 charater")
    private String description;
    @ImageNameValid
    private String coverImage;


}
