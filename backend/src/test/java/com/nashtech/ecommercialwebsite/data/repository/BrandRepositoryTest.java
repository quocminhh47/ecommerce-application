package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    BrandRepository brandRepository;

    private Brand brand;

    @BeforeEach
    void setup() {
        Brand brand = Brand.builder()
                .name("DELL")
                .thumbnail("Thumbail")
                .description("description")
                .build();
    }

    @AfterEach
    void tearDown() {
        brandRepository.deleteAll();
    }

    @DisplayName("Junit test for get brand by nbrand name")
    @Test
    void givenBrandName_whenFindByName_thenReturnBrandObject() {
        //given
        Brand actualBrand = brandRepository.save(brand);

        //when
        Brand expectedBrand = brandRepository.findBrandByName(brand.getName()).get();

        //then
        assertThat(expectedBrand).isNotNull();
        assertThat(expectedBrand).isEqualTo(actualBrand);
    }
}