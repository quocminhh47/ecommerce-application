package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldReturnTheRightRoleByGivenRoleName() {
        //given
        String roleName = "PM";
        Role expectedRole = new Role(roleName, "This role is Project manager");

        //when
        underTest.save(expectedRole);
        Optional<Role> role = underTest.findRolesByRoleName(roleName);

        //then
        assertThat(role.isPresent()).isTrue();
        assertThat(role.get()).isEqualTo(expectedRole);
    }

    @Test
    void itShouldCheckIfTheRoleNameDoesNotExists() {
        //given
        String roleName = "FakeName";

        //when
        Optional<Role> role = underTest.findRolesByRoleName(roleName);

        //then
        assertThat(role.isPresent()).isFalse();
    }
}