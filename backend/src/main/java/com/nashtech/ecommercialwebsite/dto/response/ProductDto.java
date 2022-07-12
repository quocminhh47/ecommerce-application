package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;

    private String name;

    private String brandName;

    private Long price;

    private int quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String thumbnail;

}
