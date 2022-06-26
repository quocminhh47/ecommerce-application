package com.nashtech.ecommercialwebsite.data.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating_points")
    private Integer ratingPoints;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", ratingPoints=" + ratingPoints +
                ", product=" + product +
                ", account=" + account +
                '}';
    }
}
