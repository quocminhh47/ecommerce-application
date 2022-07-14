package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.Bill;
import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.BillDetailReponse;
import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BillService {

    BillResponse orderProducts(HttpServletRequest request, BillRequest billRequest);

    BillResponse getBillById(HttpServletRequest request, int billId);

    List<BillDetailReponse> getBillByAccount(HttpServletRequest request);

    BillPaginationResponse getAllBills(int pageNo,
                                       int pageSize,
                                       String sortBy,
                                       String sortDir);
}
