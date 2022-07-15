package com.nashtech.ecommercialwebsite.data.entity;

import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
/*@NamedNativeQuery(name = "Rating.getUserRatingByProduct",
                  query = "select (select AVG(rating_points) from ratings r where r.product_id = :productId) as productRatingPoints , " +
                            "rating_points as userRatingPoints " +
                            "from ratings " +
                            "where product_id = :productId and user_id = :userId",
                  resultSetMapping = "Mapping.RatingResponse")
@SqlResultSetMapping(
        name = "Mapping.RatingResponse",
        classes = @ConstructorResult(
                targetClass = RatingResponse.class,
                columns = {
                        @ColumnResult(name = "productRatingPoints", type = Double.class),
                        @ColumnResult(name = "userRatingPoints", type = Integer.class)
                }))*/
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


}
