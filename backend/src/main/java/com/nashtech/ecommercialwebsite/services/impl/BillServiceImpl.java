package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.BillRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.BillDetailReponse;
import com.nashtech.ecommercialwebsite.dto.response.BillItemResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.BillService;
import com.nashtech.ecommercialwebsite.services.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nashtech.ecommercialwebsite.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final JwtService jwtService;

    private final BillRepository billRepository;

    private final ModelMapper mapper;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final LoginStatusServiceImpl loginStatusService;


    @Override
    public BillResponse orderProducts(HttpServletRequest request, BillRequest billRequest) {
        String token = jwtService.parseJwt(request);
        String username = jwtService.getUsernameFromToken(token);

        Account user = userRepository.findAccountByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Username %s not found", username)));

        List<Product> products = productRepository.findAll();
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        Bill bill = new Bill();
        bill.setCreateDate(new Date());
        bill.setAccount(user);

        BillResponse billResponse = new BillResponse();

        billRequest.getCartDetails().forEach(item -> {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ResourceNotFoundException(
                        String.format("Product with ID: %s not found", item.getProductId()));
            }
            Product product = productMap.get(item.getProductId());

            BillDetail billDetail = new BillDetail();
            billDetail.setId(new BillDetailId(bill.getId(), item.getProductId()));
            billDetail.setProduct(product);
            billDetail.setBill(bill);
            billDetail.setQuantity(item.getProductQuantity());
            billDetail.setPrice(product.getPrice()); //price per one product

            billResponse.getCartDetails().add(mapToBillItemResponse(item, product));
            billResponse.setPriceTotal(item.getProductQuantity() * product.getPrice());

            bill.getBillDetails().add(billDetail);

        });

        billResponse.setPhone(user.getPhone());
        billResponse.setAddress(user.getAddress());
        billResponse.setFirstName(user.getFirstName());
        billResponse.setLastName(user.getLastName());
        billResponse.setEmail(user.getUsername());

        int billTotalPrice = bill.getBillDetails().stream()
                .mapToInt(s -> (s.getQuantity() * s.getPrice()))
                .sum();

        bill.setPriceTotal(billTotalPrice);
        billResponse.setPriceTotal(bill.getPriceTotal());

        //save bill & bill items
        billRepository.save(bill);
        billResponse.setBillId(bill.getId());
        return billResponse;
    }

    @Override
    public BillResponse getBillById(HttpServletRequest request, int billId) {

        String token = jwtService.parseJwt(request);
        String username = jwtService.getUsernameFromToken(token);

        Account user = userRepository.findAccountByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Username %s not found", username)));

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        BillResponse billResponse = new BillResponse();
        bill.getBillDetails().forEach(item -> {

            BillItemResponse itemResponse = BillItemResponse.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productQuantity(item.getQuantity())
                    .productPrice(item.getPrice())
                    .build();

            billResponse.getCartDetails().add(itemResponse);
        });

        billResponse.setBillId(bill.getId());
        billResponse.setPriceTotal(bill.getPriceTotal());
        billResponse.setFirstName(user.getFirstName());
        billResponse.setLastName(user.getLastName());
        billResponse.setPhone(user.getPhone());
        billResponse.setAddress(user.getAddress());
        billResponse.setEmail(user.getUsername());

        return billResponse;

    }

    @Override
    public BillResponse getSingleBillDetail(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        Account user = bill.getAccount();

        BillResponse billResponse = new BillResponse();
        bill.getBillDetails().forEach(item -> {

            BillItemResponse itemResponse = BillItemResponse.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productQuantity(item.getQuantity())
                    .productPrice(item.getPrice())
                    .build();

            billResponse.getCartDetails().add(itemResponse);
        });
        billResponse.setBillId(bill.getId());
        billResponse.setPriceTotal(bill.getPriceTotal());

        billResponse.setFirstName(user.getFirstName());
        billResponse.setLastName(user.getLastName());
        billResponse.setPhone(user.getPhone());
        billResponse.setAddress(user.getAddress());
        billResponse.setEmail(user.getUsername());

        return billResponse;
    }

    @Override
    public BillResponse changeBilStatus(int billId, String status) {
        int statusValueConverted = getBillStatus(status);

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill Id: %s not found", billId)
                ));
        bill.setStatus(statusValueConverted);
        billRepository.save(bill);

        Account user = bill.getAccount();

        BillResponse billResponse = new BillResponse();
        bill.getBillDetails().forEach(item -> {

            BillItemResponse itemResponse = BillItemResponse.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productQuantity(item.getQuantity())
                    .productPrice(item.getPrice())
                    .build();

            billResponse.getCartDetails().add(itemResponse);
        });
        billResponse.setBillId(bill.getId());
        billResponse.setPriceTotal(bill.getPriceTotal());

        billResponse.setFirstName(user.getFirstName());
        billResponse.setLastName(user.getLastName());
        billResponse.setPhone(user.getPhone());
        billResponse.setAddress(user.getAddress());
        billResponse.setEmail(user.getUsername());

        return billResponse;
    }

    @Override
    public List<BillDetailReponse> getBillByAccount(HttpServletRequest request) {
        List<BillDetailReponse> billResponseList = new ArrayList<>();

        String token = jwtService.parseJwt(request);
        String username = jwtService.getUsernameFromToken(token);

        Account user = userRepository.findAccountByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Username %s not found", username)));

        List<Bill> bills = billRepository.findBillByAccount(user);

        bills.forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(user.getUsername());
            billResponseList.add(billDetailReponse);
            //System.out.println(billDetailReponse.toString());

        });
        return billResponseList;
    }

    @Override
    public BillPaginationResponse getAllBills(int pageNo,
                                              int pageSize,
                                              String sortBy,
                                              String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Bill> bills = billRepository.findAll(pageable);

        return getContent(bills);

    }

    @Override
    public BillPaginationResponse getAllBillsByStatus(int pageNo,
                                                      int pageSize,
                                                      String sortBy,
                                                      String sortDir,
                                                      String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        int statusValueConverted = getBillStatus(status);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Bill> bills = billRepository.findBillByStatus(pageable, statusValueConverted);

        return getContent(bills);
    }


    private BillItemResponse mapToBillItemResponse(CartItemUpdateDto cartItemUpdateDto, Product product) {
        BillItemResponse billItemResponse = mapper.map(cartItemUpdateDto, BillItemResponse.class);
        billItemResponse.setProductName(product.getName());
        billItemResponse.setProductPrice(product.getPrice());
        return billItemResponse;
    }

    private BillPaginationResponse getContent(Page<Bill> bills) {
        List<Bill> billList = bills.getContent();
        List<BillDetailReponse> billContent = new ArrayList<>();

        billList.forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(bill.getAccount().getUsername());
            billContent.add(billDetailReponse);
        });

        return BillPaginationResponse.builder()
                .billContent(billContent)
                .pageNo(bills.getNumber())
                .pageSize(bills.getSize())
                .totalElements(bills.getTotalElements())
                .totalPages(bills.getTotalPages())
                .last(bills.isLast())
                .build();


    }

    private int getBillStatus(String status) {
        int statusValueConverted = 0;
        switch (status.toLowerCase()) {
            case "accepted":
                statusValueConverted = BILL_STATUS_ACCEPTED;
                break;
            case "purchased":
                statusValueConverted = BILL_STATUS_PURCHASED;
                break;
            case "canceled":
                statusValueConverted = BILL_STATUS_CANCELED;
                break;
            default:
                statusValueConverted = BILL_STATUS_UNSOLVED;
        }
        return statusValueConverted;
    }
//
//    private BillResponse convertBillToBillResponse(Bill bill, Account user) {
//        BillResponse billResponse = new BillResponse();
//        bill.getBillDetails().forEach(item -> {
//
//            BillItemResponse itemResponse = BillItemResponse.builder()
//                    .productId(item.getProduct().getId())
//                    .productName(item.getProduct().getName())
//                    .productQuantity(item.getQuantity())
//                    .productPrice(item.getPrice())
//                    .build();
//
//            billResponse.getCartDetails().add(itemResponse);
//        });
//
//        billResponse.setBillId(bill.getID());
//        billResponse.setPriceTotal(bill.getPriceTotal());
//        billResponse.setFirstName(user.getFirstName());
//        billResponse.setLastName(user.getLastName());
//        billResponse.setPhone(user.getPhone());
//        billResponse.setAddress(user.getAddress());
//        billResponse.setEmail(user.getUsername());
//
//        return billResponse;
//    }
}
