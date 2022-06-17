package com.nashtech.ecommercialwebsite.repository;


import com.nashtech.ecommercialwebsite.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
