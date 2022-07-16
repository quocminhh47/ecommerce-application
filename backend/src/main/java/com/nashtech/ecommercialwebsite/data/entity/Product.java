package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "discount")
    private Float discount;

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

    @Column(name = "hidden")
    private Boolean hidden = false;

    @Column(name = "guarantee")
    private Integer guaranteeTime;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "water_proof")
    private Boolean isWaterProof;

    @Column(name = "size")
    private Float size;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product",  cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<CartDetail> cartDetails;


    @JsonIgnore
    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    List<ProductImage> productImages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Rating> ratings;

}
