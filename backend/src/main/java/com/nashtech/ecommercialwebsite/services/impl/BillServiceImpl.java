package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.BillItemRepository;
import com.nashtech.ecommercialwebsite.data.repository.BillRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.*;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.BillService;
import com.nashtech.ecommercialwebsite.services.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final JwtService jwtService;

    private final BillRepository billRepository;

    private  final BillItemRepository billItemRepository;

    private final ModelMapper mapper;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;


  @Override
  public BillResponse orderProducts(HttpServletRequest request, BillRequest billRequest) {
      String token = jwtService.parseJwt(request);
      String username = jwtService.getUsernameFromToken(token);

      Account user = userRepository.findAccountByUsername(username)
              .orElseThrow(() -> new ResourceNotFoundException(
                      String.format("Username %s not found", username)));

      Bill bill = new Bill();
      bill.setCreateDate(new Date());
      bill.setAccount(user);

      BillResponse billResponse = new BillResponse();
      billResponse.setPhone(user.getPhone());
      billResponse.setAddress(user.getAddress());

      billRequest.getCartDetails().forEach(item -> {
          Product product = productRepository.findById(item.getProductId())
                  .orElseThrow(
                          () -> new ResourceNotFoundException("Product not found"));

          BillDetail billDetail = new BillDetail();
          billDetail.setId( new BillDetailId(bill.getID(), item.getProductId()));
          billDetail.setProduct(product);
          billDetail.setBill(bill);
          billDetail.setQuantity(item.getProductQuantity());
          billDetail.setPrice(product.getPrice()); //price per one product

          billResponse.getCartDetails().add(mapToBillItemResponse(item, product));
          billResponse.setPriceTotal(item.getProductQuantity() * product.getPrice());

          bill.getBillDetails().add(billDetail);

      });

      int billTotalPrice = bill.getBillDetails().stream()
              .mapToInt(s -> (s.getQuantity() * s.getPrice()))
              .sum();

      bill.setPriceTotal(billTotalPrice);
      billResponse.setPriceTotal(bill.getPriceTotal());

      //save bill & bill items
      billRepository.save(bill);
      return billResponse;
  }



    private BillItemResponse mapToBillItemResponse(CartItemUpdateDto cartItemUpdateDto, Product product) {
        BillItemResponse billItemResponse = mapper.map(cartItemUpdateDto, BillItemResponse.class);
        billItemResponse.setProductName(product.getName());
        billItemResponse.setProductPrice(product.getPrice());
        return billItemResponse;
    }
}
