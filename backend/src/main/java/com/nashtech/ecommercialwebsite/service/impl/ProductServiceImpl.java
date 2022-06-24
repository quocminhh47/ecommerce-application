package com.nashtech.ecommercialwebsite.service.impl;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.dto.request.ProductDto;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findByID(String id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending() ;

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);

        //get content for page object
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream().map(product -> maptoDTO(product)  ).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    //convert product entity to ProductDto
    private ProductDto maptoDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand().getName(),
                product.getCpu(),
                product.getMonitor(),
                product.getRamSize(),
                product.getPrice()
        );
    }
}