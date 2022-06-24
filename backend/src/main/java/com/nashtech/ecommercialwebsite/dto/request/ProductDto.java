package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String brandName;
    private String cpu;
    private String monitor;
    private String ramSize;
    private Long price;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brandName='" + brandName + '\'' +
                ", cpu='" + cpu + '\'' +
                ", monitor='" + monitor + '\'' +
                ", ramSize='" + ramSize + '\'' +
                ", price=" + price +
                '}';
    }
}
