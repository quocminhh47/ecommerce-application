package com.nashtech.ecommercialwebsite.data.repository;


import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findBillByAccount(Account account);

    Page<Bill> findBillByStatus(Pageable pageable, int status);
}
