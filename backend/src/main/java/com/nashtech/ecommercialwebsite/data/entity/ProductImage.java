package com.nashtech.ecommercialwebsite.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
@NoArgsConstructor
@Getter @Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @Getter(AccessLevel.NONE)
    private Product product;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", product=" + product +
                '}';
    }

    public ProductImage(String image, String description, Product product) {
        this.image = image;
        this.description = description;
        this.product = product;
    }
}
