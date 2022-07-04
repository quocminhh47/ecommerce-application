package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldReturnTheRightBrandWithGivenName() {
        //given
        String brandName = "TEST";
        Brand expectedBrand = new Brand(brandName, "This is thumbnail","This is test description");

        //when
        underTest.save(expectedBrand);

        Optional<Brand> brand = underTest.findBrandByName(brandName);

        //then
        assertThat(brand).isPresent();
        assertThat(brand.get()).isEqualTo(expectedBrand);
    }

    @Test
    void itShouldCheckWhenTheGivenNameDoseNotExist() {

        //given
        String brandName = "FakeName";

        //when
        Optional<Brand> brand = underTest.findBrandByName(brandName);

        //then
        assertThat(brand.isEmpty()).isTrue();
    }
}