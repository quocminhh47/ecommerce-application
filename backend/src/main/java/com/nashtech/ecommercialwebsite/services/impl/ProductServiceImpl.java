package com.nashtech.ecommercialwebsite.services.impl;


import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.entity.ProductImage;
import com.nashtech.ecommercialwebsite.data.repository.*;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductDto;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.JwtService;
import com.nashtech.ecommercialwebsite.services.LoginStatusService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final JwtService jwtService;
    private final JwtUtils jwtUtils;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final ProductImagesRepository imagesRepository;
    private final BrandRepository brandRepository;
    private final LoginStatusService loginStatusService;

    private static final String DEFAULT_IMAGE_URL = AppConstants.DEFAULT_URL;

    @Override
    public SingleProductResponse findProductById(int id, HttpServletRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));

        SingleProductResponse singleProductResponse = mapper.map(product, SingleProductResponse.class);
        List<ProductImage> images = imagesRepository.findProductImagesByProduct(product);
        singleProductResponse.setProductImages(images);

        Double ratingPointsFromProduct = ratingRepository.getRatingPointsFromProduct(id);

        String token = jwtService.parseJwt(request);
        //token is valid
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtService.getUsernameFromToken(token);

            Account account = userRepository.findAccountByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Username %s not found ", username)));

            singleProductResponse.setIsUserLogged(true);

            singleProductResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

            RatingResponse ratingResponse = new RatingResponse(
                    ratingPointsFromProduct,
                    ratingRepository.getUserRatingPointsByProduct(account.getId(), id));

            singleProductResponse.setRatingResponse(ratingResponse);

            return singleProductResponse;

        }
        //anonymous access
        singleProductResponse.setIsUserLogged(false);
        RatingResponse ratingResponse =
                new RatingResponse(ratingPointsFromProduct, null);
        singleProductResponse.setRatingResponse(ratingResponse);
        return singleProductResponse;

    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection,
                                          HttpServletRequest request) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        ProductResponse productResponse = getContent(products);

        productResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return productResponse;
    }

    @Override
    public ProductResponse getAllAvailableProducts(boolean hidden,
                                                   int pageNo,
                                                   int pageSize,
                                                   String sortBy,
                                                   String sortDirection,
                                                   HttpServletRequest request) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByHidden(false, pageable);

        ProductResponse productResponse = getContent(products);
        productResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return productResponse;

    }

    @Override
    public ProductResponse getProductsByBrandName(String brandName,
                                                  int pageNo,
                                                  int pageSize,
                                                  String sortBy,
                                                  String sortDirection,
                                                  HttpServletRequest request) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByBrand_Name(brandName, pageable);

        ProductResponse productResponse = getContent(products);
        productResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return productResponse;
    }

    @Override
    public SingleProductResponse saveProduct(ProductRequest productRequest, HttpServletRequest request) {
        if (productRequest.getThumbnail() == null) productRequest.setThumbnail(DEFAULT_IMAGE_URL);
        Product product = mapToEntity(productRequest);//product now include produc info + brand + list image
        product.setCreatedAt(LocalDateTime.now());

        product.getProductImages().forEach(image -> image.setProduct(product));

        Product savedProduct = productRepository.save(product);
        SingleProductResponse response = mapper.map(savedProduct, SingleProductResponse.class);
        response.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return response;
    }

    @Override
    public SingleProductResponse updateProduct(int id, ProductUpdateRequest productRequest,
                                               HttpServletRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));

        mapper.map(productRequest, product);
        product.setUpdatedAt(LocalDateTime.now());
        product.setId(id);

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand.id.%s.not.found", productRequest.getBrandId())));
        product.setBrand(brand);

        product.getProductImages().forEach(image -> image.setProduct(product));

        Product updatedProduct = productRepository.save(product);

        SingleProductResponse singleProductResponse = mapper.map(updatedProduct, SingleProductResponse.class);
        singleProductResponse.setBrandId(brand.getId());
        singleProductResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return singleProductResponse;
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));
        product.setHidden(true);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductsByGender(boolean gender,
                                               int pageNo,
                                               int pageSize,
                                               String sortBy,
                                               String sortDirection,
                                               HttpServletRequest request) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByGender(gender, pageable);

        ProductResponse productResponse = getContent(products);
        productResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return productResponse;
    }


    //convert product entity to ProductDto
    private ProductDto maptoDTO(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product mapToEntity(ProductRequest productRequest) {
        Product productEntity = mapper.map(productRequest, Product.class);
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand not found with id: %s", productRequest.getBrandId())));
        productEntity.setBrand(brand);
        return productEntity;
    }


    private ProductResponse getContent(Page<Product> products) {
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream()
                .map(this::maptoDTO)
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .productContent(content)
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .last(products.isLast())
                .build();
    }
}