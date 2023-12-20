package com.lunkes.verifymy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdd {

    private String nome;
    private String email;
    private String password;
    private boolean administrador;
}
