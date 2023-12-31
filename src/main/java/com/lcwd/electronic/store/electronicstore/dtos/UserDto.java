package com.lcwd.electronic.store.electronicstore.dtos;

import com.lcwd.electronic.store.electronicstore.validation.ImageNameValid;
import lombok.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.Column;
import javax.validation.constraints.*;
import javax.xml.stream.XMLInputFactory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @NotNull
    @Size(min = 2, max = 20, message = "name should be minimum 2 character")
    private String name;
    @Email(message = "Invalid email id")
    private String email;
    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}$",
            message = "password must be at least one uppercase letter,lowercase letter, digit, and one special character")
    private String password;
    @NotBlank(message = "write something about yourself")
    @Size(max = 1000)
    private String about;
    @Size(min = 4, max = 6, message = "Invalid gender")
    private String gender;
    @ImageNameValid
    private String imagename;

}
