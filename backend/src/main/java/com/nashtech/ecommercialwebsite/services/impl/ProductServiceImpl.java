package com.nashtech.ecommercialwebsite.services.impl;


import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductDto;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final BrandRepository brandRepository;
    private final AuthenticationFacadeService authenticationFacadeService;
    private final UserRepository userRepository;

    @Override
    public SingleProductResponse findProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));

        SingleProductResponse singleProductResponse = mapper.map(product, SingleProductResponse.class);

        Double ratingPointsFromProduct = ratingRepository.getRatingPointsFromProduct(id);

        String currentUser = authenticationFacadeService.getCurentUsername();
        singleProductResponse.setCurrentUser(currentUser);
        if(!currentUser.equals("anonymousUser")) {
            Account account = userRepository.findAccountByUsername(currentUser)
                    .orElseThrow(() -> new ResourceNotFoundException("User "+ currentUser+ " not exist"));
            Integer userRatingPointsAboutProduct = ratingRepository.getUserRatingPointsByProduct(account.getId(), id);
            RatingResponse ratingResponse =
                    new RatingResponse(ratingPointsFromProduct, userRatingPointsAboutProduct);
            singleProductResponse.setRatingResponse(ratingResponse);
        }
        else singleProductResponse.setRatingResponse(new RatingResponse(ratingPointsFromProduct, null));

        return singleProductResponse;

    }


    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
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
                                                   String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
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
                                                  String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
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

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand.id.%s.not.found", productRequest.getBrandId())));
        product.setBrand(brand);

        product.getProductImages().forEach(image -> image.setProduct(product));

        Product updatedProduct = productRepository.save(product);

        SingleProductResponse singleProductResponse = mapper.map(updatedProduct, SingleProductResponse.class);
        singleProductResponse.setBrandId(brand.getId());

        return singleProductResponse;
    }

    @Override
    public SingleProductResponse deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));
        product.setHidden(true);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, SingleProductResponse.class);
    }

    @Override
    public ProductResponse getProductsByGender(boolean gender,
                                               int pageNo,
                                               int pageSize,
                                               String sortBy,
                                               String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findProductByGender(gender, pageable);

        return getContent(products);
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
                .currentUser(authenticationFacadeService.getCurentUsername())
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .last(products.isLast())
                .build();
    }
}