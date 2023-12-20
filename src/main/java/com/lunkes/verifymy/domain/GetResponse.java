package com.lunkes.verifymy.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetResponse {
   private String quantidade;
   private List<User> usuarios;
}
