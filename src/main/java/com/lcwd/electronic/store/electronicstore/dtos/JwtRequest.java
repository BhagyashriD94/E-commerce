package com.lcwd.electronic.store.electronicstore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {

    private String email;
    private String password;
}
