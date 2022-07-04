package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import com.nashtech.ecommercialwebsite.services.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/customer/api/order")
public class BillController {

    private final BillService billService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public BillResponse purchaseBill(@RequestBody BillRequest billRequest,
                                     HttpServletRequest request) {
        return billService.orderProducts(request, billRequest);
    }
}
