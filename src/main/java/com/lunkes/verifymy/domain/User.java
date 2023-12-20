package com.lunkes.verifymy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String _id;
    private String nome;
    private String email;
    private String password;
    private boolean administrador;

    public String toBody(){
        StringBuilder body = new StringBuilder();
        body.append("{\n");
        if (_id != null) body.append("\"_id\": \"" + _id + "\",\n");
        body.append("\"nome\": \"" + nome + "\",\n");
        body.append("\"email\": \"" + email + "\",\n");
        body.append("\"password\": \"" + password + "\",\n");
        body.append("\"administrador\": \"" + administrador + "\"");
        body.append("}");
        return body.toString();
    }
}
