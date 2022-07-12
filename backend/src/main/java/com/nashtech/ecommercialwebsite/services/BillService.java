package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BillService {

    BillResponse orderProducts(HttpServletRequest request, BillRequest billRequest);
}
