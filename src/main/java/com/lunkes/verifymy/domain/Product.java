package com.lunkes.verifymy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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

}