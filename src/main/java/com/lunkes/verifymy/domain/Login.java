package com.lunkes.verifymy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private String email;
    private String password;

    public String toBody(){
        StringBuilder body = new StringBuilder();
        body.append("{\n");
        body.append("\"email\": \"" + email + "\",\n");
        body.append("\"password\": \"" + password + "\",\n");
        body.append("}");
        return body.toString();
    }
}
