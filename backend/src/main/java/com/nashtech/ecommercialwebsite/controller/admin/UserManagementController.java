package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;
import com.nashtech.ecommercialwebsite.dto.request.UserRequest;
import com.nashtech.ecommercialwebsite.dto.response.TokenResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;
import com.nashtech.ecommercialwebsite.services.RegistrationService;
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
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "User Resources Management",
        description = "Permit to access / change customer's account status")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/api/users")
public class UserManagementController {

    private final UserService userService;
    private static final String ADMIN_ROLE = "ADMIN";
    private final RegistrationService registrationService;

    public UserManagementController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping()
    @Operation(summary = "Get all customers' accounts", description = "This return all the customers' account in pagination")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customers' accounts resources was not found")
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
                    String sortDir)
    {

        return new ResponseEntity<>(userService.getAllUserAccounts(
                pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detail account information",
            description = "This return all detail information of the specified customer account")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customer's account resources was not found")
    })
    public ResponseEntity<UserAccountDto> getUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getAccountById(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Change account information", description = "Change the status of customer's account")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully changed"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The customer's account resources was not found")
    })
    public ResponseEntity<UserAccountDto> changeAccountStatus(@Valid @RequestBody UserRequest userRequest,
                                                              @PathVariable("userId") int userId) {

        return new ResponseEntity<>(
                userService.changeUserAccountStatus(userRequest, userId), HttpStatus.OK);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register feature for admin staff",
            description = "This create accounts for customers and send token to customer's email for verification")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "OK - Successfully created account"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public TokenResponse register(@Valid @RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest, ADMIN_ROLE);
    }

}
