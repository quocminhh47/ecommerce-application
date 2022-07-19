package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.response.BillDetailReponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import com.nashtech.ecommercialwebsite.services.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Customer bill resource",
        description = "Provide show bills history of customer, show bill detail also")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/customer/api/order")
public class BillController {

    private final BillService billService;

    @PostMapping()
    @Operation(summary = "Create customer bill", description = "Export bill from the order of customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The create bill url was not found"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
    @ResponseStatus(HttpStatus.OK)
    public BillResponse purchaseBill(@RequestBody BillRequest billRequest) {
        return billService.orderProducts( billRequest);
    }

    @GetMapping("/bill/{id}")
    @Operation(summary = "Get bill detail", description = "Provide detail information include product, customer info and price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The retrieve bill url was not found"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
    @ResponseStatus(HttpStatus.OK)
    public BillResponse getBillDetailById(@PathVariable("id") int id) {
        return billService.getBillById(id);
    }

    @GetMapping("/bill/all")
    @Operation(summary = "Get bill history of customer", description = "Provide all bill history which were ordered by account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieve"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The create bill url was not found"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<BillDetailReponse> getBillDetailByAccount() {
        return billService.getBillByAccount();
    }




}
