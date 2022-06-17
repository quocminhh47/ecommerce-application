package com.nashtech.ecommercialwebsite.controller;

import com.nashtech.ecommercialwebsite.entity.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class UserController {

    //test api
    @GetMapping("/test")
    public ResponseEntity<Brand> getBrand(){
        Brand brand = new Brand(1, "ASUS", "ASUS Company");
        return ResponseEntity.ok(brand);
    }
}
