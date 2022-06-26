package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter @Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Brand {

    private static final String THUMBNAIL_LINK = "https://aceo.vn/images/logo-google.png";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Setter(AccessLevel.NONE)
    private  Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail = THUMBNAIL_LINK;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Set<Product> products;

}
