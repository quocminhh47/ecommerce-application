package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.BillDetail;
import com.nashtech.ecommercialwebsite.data.entity.BillDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillDetail, BillDetailId> {
}