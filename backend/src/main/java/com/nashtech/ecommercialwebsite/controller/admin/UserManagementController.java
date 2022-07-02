package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;
import com.nashtech.ecommercialwebsite.services.UserService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "Customers Resources Management",
        description = "Permit to access / change customer's account status")
@RestController
@RequestMapping("/admin/api/users")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all customers' accounts", description = "This return all the customers' account in pagination")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customers' accounts resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public ResponseEntity<UserAccountResponse> getAllUsers(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir )
    {

        return new ResponseEntity<>(userService.getAllUserAccounts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Detail account information",
            description = "This return all detail information of the specified customer account")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customer's account resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public ResponseEntity<UserAccountDto> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getAccountById(id), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change account information", description = "Change the status of customer's account")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully changed"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customer's account resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public ResponseEntity<UserAccountDto> changeAccountStatus(@Valid @RequestBody UserAccountDto accountDto) {
        return new ResponseEntity<>(userService.changeUserAccountStatus(accountDto), HttpStatus.OK);
    }

}