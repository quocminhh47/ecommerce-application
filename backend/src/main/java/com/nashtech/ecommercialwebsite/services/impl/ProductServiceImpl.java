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
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.services.RatingService;
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
        if( token != null && jwtUtils.validateJwtToken(token) ) {
            String username = jwtService.getUsernameFromToken(token);

            Account account = userRepository.findAccountByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Username %s not found ", username)));

            singleProductResponse.setIsUserLogged(true);

            RatingResponse ratingResponse = new RatingResponse(
                    ratingPointsFromProduct,
                    ratingRepository.getUserRatingPointsByProduct(account.getId(), id));

            singleProductResponse.setRatingResponse(ratingResponse);

            return singleProductResponse;

        }
        //anonymous access
        singleProductResponse.setIsUserLogged(false);
        RatingResponse ratingResponse =
                new RatingResponse(ratingPointsFromProduct,null);
        singleProductResponse.setRatingResponse(ratingResponse);
        return singleProductResponse;

    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return getContent(products);
    }

    @Override
    public ProductResponse getAllAvailableProducts(boolean hidden,
                                                   int pageNo,
                                                   int pageSize,
                                                   String sortBy,
                                                   String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByHidden(false, pageable);
        return getContent(products);
    }

    @Override
    public ProductResponse getProductsByBrandName(String brandName,
                                                  int pageNo,
                                                  int pageSize,
                                                  String sortBy,
                                                  String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByBrand_Name(brandName, pageable);
        return getContent(products);
    }

    @Override
    public SingleProductResponse saveProduct(ProductRequest productRequest) {
        Product product = mapToEntity(productRequest);//product now include produc info + brand + list image
        product.setCreatedAt(LocalDateTime.now());

        product.getProductImages().forEach(image -> image.setProduct(product));

        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, SingleProductResponse.class);
    }

    @Override
    public SingleProductResponse updateProduct(int id, ProductUpdateRequest productRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));

        mapper.map(productRequest, product);
        product.setUpdatedAt(LocalDateTime.now());
        product.setId(id);

        product.getProductImages().forEach(image -> image.setProduct(product));

        Product updatedProduct = productRepository.save(product);

        return mapper.map(updatedProduct, SingleProductResponse.class);
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));
        product.setHidden(true);
        productRepository.save(product);
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