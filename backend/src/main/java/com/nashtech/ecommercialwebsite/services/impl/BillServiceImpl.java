package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.BillRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.*;
import com.nashtech.ecommercialwebsite.exceptions.BadRequestException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.BillService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final BillRepository billRepository;

    private final ModelMapper mapper;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final AuthenticationFacadeService authenticationFacade;


    @Override
    public BillResponse orderProducts(BillRequest billRequest) {
        String currentPrincipalName = authenticationFacade.getCurentUsername();
        Account user = userRepository.findAccountByUsername(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Username %s not found", currentPrincipalName)));

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
    public BillResponse getBillById(int billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        return convertBillToBillResponse(bill);
    }

    @Override
    public BillResponse getSingleBillDetail(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        return convertBillToBillResponse(bill);

    }

    @Override
    public BillResponse changeBilStatus(int billId, String status) {
        int statusValueConverted = getBillStatus(status);

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill Id: %s not found", billId)
                ));
        bill.setStatus(statusValueConverted);
        Bill updatedBill = billRepository.save(bill);

        return convertBillToBillResponse(updatedBill);
    }

    @Override
    public List<BillDetailReponse> getBillByAccount() {
        List<BillDetailReponse> billResponseList = new ArrayList<>();

        String currentPrincipalName = authenticationFacade.getCurentUsername();
        Account user = userRepository.findAccountByUsername(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Username %s not found", currentPrincipalName)));

        List<Bill> bills = billRepository.findBillByAccount(user);

        bills.forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(user.getUsername());
            billResponseList.add(billDetailReponse);

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

    @Override
    public BillReportResponse getSaleReportByDateRange(String dateStart, String dateEnd) {
        LocalDate start = LocalDate.parse(dateStart);
        LocalDate end = LocalDate.parse(dateEnd);
        if (start.isAfter(end)) {
            throw new BadRequestException("Date start is must before date end!!");
        }
        Long sale = billRepository.getSaleByRangeOfDate(dateStart, dateEnd);
        if (sale == null) {
            return new BillReportResponse(null, null);
        }
        List<BillDetailReponse> billResponseList = new ArrayList<>();
        billRepository.getBillsByDate(dateStart, dateEnd).forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(bill.getAccount().getUsername());
            billResponseList.add(billDetailReponse);
        });

        return new BillReportResponse(sale, billResponseList);
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
            case "unsolved":
                statusValueConverted = BILL_STATUS_UNSOLVED;
                break;

        }
        return statusValueConverted;
    }

    private BillResponse convertBillToBillResponse(Bill bill) {
        Account user = bill.getAccount();

        BillResponse billResponse = BillResponse.builder()
                .billId(bill.getId())
                .cartDetails(new ArrayList<>())
                .priceTotal(bill.getPriceTotal())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .email(user.getUsername())
                .build();

        bill.getBillDetails().forEach(item -> {
            BillItemResponse itemResponse = BillItemResponse.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productQuantity(item.getQuantity())
                    .productPrice(item.getPrice())
                    .build();

            billResponse.getCartDetails().add(itemResponse);
        });

        return billResponse;
    }

}
