package com.lunkes.verifymy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetResponseProdutos {
   private String quantidade;
   private List<Product> produtos;
}
