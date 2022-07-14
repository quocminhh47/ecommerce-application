package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setup() {
        role = Role.builder()
                .roleName("PM")
                .description("Project manager")
                .build();
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        ;
    }

    @DisplayName("Junit test for get role by role name")
    @Test
    void gienRoleName_whenFindByName_thenReturnRoleObject() {
        //given
        Role actualRole = roleRepository.save(role);

        //when
        Role expectedRole = roleRepository.findRolesByRoleName(role.getRoleName()).get();

        //then
        assertThat(expectedRole).isNotNull();
        assertThat(expectedRole).isEqualTo(actualRole);

    }
}