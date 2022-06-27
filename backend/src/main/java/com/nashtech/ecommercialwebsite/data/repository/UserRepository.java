package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String email);
    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.enabled = TRUE WHERE a.username = ?1") //username = email
    int enableUser(String email);

    Page<Account> findAllByRole(Pageable pageable, Role role);

    @Query( "UPDATE Account a " +
            "SET a.enabled = ?2 , a.locked = ?3 WHERE a.id = ?1")
    int changeUserAccountStatus(long id, boolean isEnabled, boolean isLocked);


}
