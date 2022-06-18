package com.nashtech.ecommercialwebsite.repository;


import com.nashtech.ecommercialwebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer > {

    Optional<Role> findRolesByRoleName(String roleName);
}
