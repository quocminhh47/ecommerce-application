package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter @Setter
@RequiredArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    Set<Product> products;

    public Brand(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
