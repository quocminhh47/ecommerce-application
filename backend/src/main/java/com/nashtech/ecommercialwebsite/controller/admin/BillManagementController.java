package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillReportResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import com.nashtech.ecommercialwebsite.services.BillService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    @Operation(summary = "Get all bills ", description = "Provide detail information include product, customer info and price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
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
                    value = "sortDir", defaultValue = "desc", required = false)
                    String sortDir) {

        return billService.getAllBills(pageNo, pageSize, sortBy, sortDir );
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all bill by status", description = "Provide list all of bills which were filtered by bill status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
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
                    value = "sortDir", defaultValue = "desc", required = false)
                    String sortDir,
            @PathVariable("status") String status
    ) {
        return billService.getAllBillsByStatus(pageNo, pageSize, sortBy, sortDir, status);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "Get bill detail", description = "Provide detail information include product, customer info and price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public BillResponse getBillByBillId( @PathVariable("id") int id
    ) {
        return billService.getSingleBillDetail(id);
    }


    @PutMapping("/change/status")
    @Operation(summary = "Change order status", description = "Cahnge the status of an order( unsolved, accepted, purchased, canceled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully changed"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public BillResponse changeBillStatus(@RequestParam("bill") int billId,
            @RequestParam("status") String status) {
        return billService.changeBilStatus(billId, status);
    }

    @GetMapping("/report")
    @Operation(summary = "Get total sale and bills by date range", description = "Provide the report of total sale and list bills which were made in the date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public BillReportResponse getSaleReport(@Param("start") String dateStart,
                                            @Param("end") String dateEnd) {
        return billService.getSaleReportByDateRange(dateStart, dateEnd);
    }
}
