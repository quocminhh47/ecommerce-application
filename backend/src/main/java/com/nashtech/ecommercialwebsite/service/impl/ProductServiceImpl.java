package com.nashtech.ecommercialwebsite.service.impl;


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
import com.nashtech.ecommercialwebsite.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        Optional<Product> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            try {
                SingleProductResponse singleProductResponse = mapper.map(productEntity, SingleProductResponse.class);
                List<ProductImage> images = imagesRepository.findProductImagesByProduct(productEntity.get());
                singleProductResponse.setProductImages(images);
                return singleProductResponse;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        //TODO: return custom exception hanlder
        else throw new IllegalStateException(String.format("Product with id: %s is not found", id));
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
        try {
            //save product
            Product savedProduct = productRepository.save(product);
            SingleProductResponse response = mapper.map(savedProduct, SingleProductResponse.class);
            //save all images of  product
            productRequest.getImages().forEach(image -> {
                ProductImage productImage = mapper.map(image, ProductImage.class);
                productImage.setProduct(savedProduct);
                //save image to DB
                imagesRepository.save(productImage);
                //add image to response
                response.getProductImages().add(productImage);
            });
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error while save product / product image");
        }
    }

    @Override
    public SingleProductResponse updateProduct(int id, ProductUpdateRequest productRequest) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isPresent()) {
            Product product = existProduct.get();
            mapper.map(productRequest, product);
            product.setUpdatedAt(LocalDateTime.now());
            product.setId(id);
            Optional<Brand> optionalBrand = brandRepository.findById(productRequest.getBrandId());
            if(optionalBrand.isEmpty()) throw new IllegalStateException(String.format(
                    "Brand id: %s not found",
                    productRequest.getBrandId()));
            product.setBrand(optionalBrand.get());
            //update product:
            Product updatedProduct = productRepository.save(product);
            SingleProductResponse response = mapper.map(updatedProduct, SingleProductResponse.class);
            //save all images of product
            productRequest.getImages().forEach(image -> {
                ProductImage productImage = mapper.map(image, ProductImage.class);
                productImage.setProduct(updatedProduct);
                //save to DB
                imagesRepository.save(productImage);
                //add to SingleProductResponse
                response.getProductImages().add(productImage);
            });
            return response;
        } else throw new IllegalStateException(String.format("Product with ID: %s not found", id));
    }

    @Override
    public void deleteProduct(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            optionalProduct.get().setHidden(true);
            productRepository.save(optionalProduct.get());
        } else throw new IllegalStateException(String.format("Product with ID: %s not found", id));
    }


    //convert product entity to ProductDto
    private ProductDto maptoDTO(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product mapToEntity(ProductRequest productRequest) {
        Product productEntity = mapper.map(productRequest, Product.class);
        Optional<Brand> optionalBrand = brandRepository.findById(productRequest.getBrandId());
        if(optionalBrand.isEmpty()) throw new IllegalStateException(String.format(
                "Brand id: %s not found",
                productRequest.getBrandId()));
        productEntity.setBrand(optionalBrand.get());
        return productEntity;
    }

    private ProductResponse getContent(Page<Product> products) {
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream()
                .map(product -> maptoDTO(product))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
        return productResponse;
    }
}