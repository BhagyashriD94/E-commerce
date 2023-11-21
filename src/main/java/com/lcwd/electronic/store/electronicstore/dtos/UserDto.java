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
    @Size(min=2,max=20,message = "name should be minimum 2 character")
    private String name;
    @Email(message="Invalid email id")
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n",message = "Invalid entered")
    private String email;
    @NotBlank(message="password is required")
    private String password;
    @NotBlank(message="write something about yourself")
    @Size(max=1000)
    private String about;
    @Size(min=4,max=6,message="Invalid gender")
    private String gender;
    @ImageNameValid
    private String imagename;

}
