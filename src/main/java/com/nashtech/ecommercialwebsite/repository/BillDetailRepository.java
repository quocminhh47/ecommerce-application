package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.BillDetail;
import com.nashtech.ecommercialwebsite.entity.BillDetail_ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetail_ID> {
}