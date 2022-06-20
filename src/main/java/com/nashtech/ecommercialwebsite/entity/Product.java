package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "updated_date")
    private LocalDateTime updatedAt;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "hidden")
    private Boolean hidden = false;

    @Column(name = "ram_slot")
    private Integer ramSlot;

    @Column(name = "ram_size")
    private String ramSize;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "monitor")
    private String monitot;

    @Column(name = "storage")
    private String storage;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<ProductImage> productImages;


    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<Rating> ratings;


}
