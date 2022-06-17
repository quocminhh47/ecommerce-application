package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    private String ID;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String urlImage;

    @Column(name = "photo2")
    private String urlImage2;

    @Column(name = "photo3")
    private String urlImage3;

    @Column(name = "photo4")
    private String urlImage4;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "ram_slot")
    private Integer ramSlot;

    @Column(name = "ram_info")
    private String ramInfo;

    @Column(name = "chip_info")
    private String chipInfo;

    @Column(name = "monitor_info")
    private String monitorInfo;

    @Column(name = "storage_info")
    private String storageInfo;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetails;

}
