package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByUsername(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE accounts  " +
            "SET enabled='TRUE' WHERE email = ?1", nativeQuery = true)
    int enableUser(String email);

    Page<Account> findAllByRole(Pageable pageable, Role role);

    //@Transactional
//    @Modifying
//    @Query( "UPDATE Account a " +
//            "SET a.enabled = :enabled , a.isNonLocked = :locked WHERE a.id = :id")
//    int changeUserAccountStatus(@Param("id") int id,
//                                 @Param("enabled") boolean isLocked,
//                                 @Param("locked") boolean isEnabled);


}
