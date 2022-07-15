package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import com.nashtech.ecommercialwebsite.services.BillService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "Bills Resources Management",
        description = "Permit to access / change all the bills ordered")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/api/bills")
public class BillManagementController {

    private final BillService billService;

    public BillManagementController(BillService billService) {
        this.billService = billService;
    }


    @GetMapping("/all")
    public BillPaginationResponse getAllBillsInPagination(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = "id", required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {

        return billService.getAllBills(pageNo, pageSize, sortBy, sortDir );
    }

    @GetMapping("/{status}")
    public BillPaginationResponse getBillsByStatus(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = "id", required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir,
            @PathVariable("status") String status
    ) {
        return billService.getAllBillsByStatus(pageNo, pageSize, sortBy, sortDir, status);
    }

    @GetMapping("/detail/{id}")
    public BillResponse getBillByBillId( @PathVariable("id") int id
    ) {
        return billService.getSingleBillDetail(id);
    }


    @PutMapping("/change/status")
    public BillResponse changeBillStatus(@RequestParam("bill") int billId,
            @RequestParam("status") String status) {
        return billService.changeBilStatus(billId, status);
    }
}
