package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository underTest;

//    @Test
//    void itShouldCheckIfBrandExistByName() {
//        //give
//        String brandName = "TEST_BRAND";
//        Brand brand = new Brand(brandName, "thumbnail", "description");
//        underTest.save(brand);
//
//        //when
//        Optional<Brand> existBrand = underTest.findBrandByName(brandName);
//
//        //then
//        assertThat(existBrand).isPresent();
//        //assertEquals(brand, existBrand.get());
//    }
}