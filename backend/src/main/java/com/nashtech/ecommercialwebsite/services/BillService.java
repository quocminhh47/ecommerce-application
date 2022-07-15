package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.response.BillDetailReponse;
import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BillService {

    BillResponse orderProducts(HttpServletRequest request, BillRequest billRequest);

    BillResponse getBillById(HttpServletRequest request, int billId);

    BillResponse getSingleBillDetail(int billId);

    BillResponse changeBilStatus(int billId, String status) ;

    List<BillDetailReponse> getBillByAccount(HttpServletRequest request);

    BillPaginationResponse getAllBills(int pageNo,
                                       int pageSize,
                                       String sortBy,
                                       String sortDir);

    BillPaginationResponse getAllBillsByStatus(int pageNo,
                                               int pageSize,
                                               String sortBy,
                                               String sortDir,
                                               String status);
}
