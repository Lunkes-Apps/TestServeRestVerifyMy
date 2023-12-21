package com.lunkes.verifymy.domain.shopcar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopCar {

    private String _id;
    private List<ItemShopCar> produtos;
    private int precoTotal;
    private int quantidadeTotal;
    private String idUsuario;

}
