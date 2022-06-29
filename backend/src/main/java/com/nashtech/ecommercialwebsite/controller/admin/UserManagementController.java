package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;
import com.nashtech.ecommercialwebsite.services.UserService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/api/users")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<UserAccountDto> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getAccountById(id), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccountDto> changeAccountStatus(@Valid @RequestBody UserAccountDto accountDto) {
        return new ResponseEntity<>(userService.changeUserAccountStatus(accountDto), HttpStatus.OK);
    }

}
