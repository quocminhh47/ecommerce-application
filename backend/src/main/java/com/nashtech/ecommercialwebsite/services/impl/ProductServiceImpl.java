package com.nashtech.ecommercialwebsite.services.impl;


import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.entity.ProductImage;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductImagesRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductDto;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
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

    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final ProductImagesRepository imagesRepository;
    private final BrandRepository brandRepository;

    @Override
    public SingleProductResponse findProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));
        SingleProductResponse singleProductResponse = mapper.map(product, SingleProductResponse.class);
        List<ProductImage> images = imagesRepository.findProductImagesByProduct(product);
        singleProductResponse.setProductImages(images);
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
        Product product = mapToEntity(productRequest);
        product.setCreatedAt(LocalDateTime.now());

        product.getProductImages().forEach(image -> image.setProduct(product));
        product.getProductImages().forEach(System.out::println);

        Product savedProduct = productRepository.save(product);
        SingleProductResponse response = mapper.map(savedProduct, SingleProductResponse.class);

        return response;
    }

    @Override
    public SingleProductResponse updateProduct(int id, ProductUpdateRequest productRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", id)));

        mapper.map(productRequest, product);
        product.setUpdatedAt(LocalDateTime.now());
        product.setId(id);

        product.getProductImages().forEach(System.out::println);

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