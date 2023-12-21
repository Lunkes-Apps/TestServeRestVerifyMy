package com.lunkes.verifymy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id;
    private String nome;
    private int preco;
    private String descricao;
    private int quantidade;

    public String toBody(){
        StringBuilder body = new StringBuilder();
        body.append("{\n");
        if (_id != null) body.append("\"_id\": \"" + _id + "\",\n");
        body.append("\"nome\": \"" + nome + "\",\n");
        body.append("\"preco\": \"" + preco + "\",\n");
        body.append("\"descricao\": \"" + descricao + "\",\n");
        body.append("\"quantidade\": \"" + quantidade + "\"");
        body.append("}");
        return body.toString();
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}