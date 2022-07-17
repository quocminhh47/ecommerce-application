package com.nashtech.ecommercialwebsite.data.repository;


import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findBillByAccount(Account account);

    Page<Bill> findBillByStatus(Pageable pageable, int status);

    @Query(value = "select sum(price_total) from bills " +
            "where status = 2  and create_date between ?1 and ?2", nativeQuery = true)
    Long getSaleByRangeOfDate(String dateStart, String dateEnd);

    @Query(value = "select * from bills where status = 2  and create_date between ?1 and ?2", nativeQuery = true)
    List<Bill> getBillsByDate(String dateStart, String dateEnd);
}
