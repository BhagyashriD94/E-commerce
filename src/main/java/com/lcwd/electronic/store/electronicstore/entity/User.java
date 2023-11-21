package com.lcwd.electronic.store.electronicstore.entity;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "about_user")
    private String about;
    @Column(name = "user_gender")
    private String gender;
    @Column(name = "user_image_name")
    private String imagename;


}
