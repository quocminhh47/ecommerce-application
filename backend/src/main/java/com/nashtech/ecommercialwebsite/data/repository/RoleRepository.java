package com.nashtech.ecommercialwebsite.data.repository;


import com.nashtech.ecommercialwebsite.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRolesByRoleName(String roleName);
}
