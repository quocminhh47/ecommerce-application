package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Cart;
import com.nashtech.ecommercialwebsite.data.entity.CartDetail;
import com.nashtech.ecommercialwebsite.data.entity.CartDetailId;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.CartItemsRepository;
import com.nashtech.ecommercialwebsite.data.repository.CartRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartItemDto;
import com.nashtech.ecommercialwebsite.dto.response.CartItemsResponse;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;
import com.nashtech.ecommercialwebsite.exceptions.InternalServerException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.CartItemService;
import com.nashtech.ecommercialwebsite.services.CartService;
import com.nashtech.ecommercialwebsite.services.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final JwtService jwtService;

    private final CartService cartService;

    private final CartItemsRepository cartItemsRepo;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    private final AuthenticationFacadeService authenticationFacadeService;


    @Override
    public CartResponse getAllCartItemsOfUser() {
        String username = authenticationFacadeService.getCurentUsername();
        Cart cart = cartService.findCartByUsername(username);
        List<CartItemsResponse> cartItemsResponses = cartItemsRepo.findAllByCart_Id(cart.getId())
                .stream()
                .map(this::mapToCartResponse)
                .collect(Collectors.toList());

        int totalPrice = cartItemsResponses.stream()
                .mapToInt(s -> (s.getCartDetailQuantity() * s.getProductPrice()))
                .sum();

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getId());
        cartResponse.setTotalPrice(totalPrice);
        cartItemsResponses.stream()
                .map(item -> cartResponse.getCartDetails().add(item))
                .collect(Collectors.toList());
        return cartResponse;
    }


    @Override
    public CartItemDto addProductToCart(int productId) {
        String username = authenticationFacadeService.getCurentUsername();

        Cart cart = cartService.findCartByUsername(username);

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found"));

        Optional<CartDetail> item = cartItemsRepo.findCartDetailsByProductAndCart(product, cart);

        //if exist -> add +1 quantity to exist item
        if (item.isPresent()) {
            CartDetail cartItem = item.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            CartDetail savedItem = cartItemsRepo.save(cartItem);
            return mapToCartItemDto(savedItem);
        }
        //if new -> create new
        CartDetail cartItem = new CartDetail();
        cartItem.setId(new CartDetailId(productId, cart.getId()));
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(1);
        CartDetail savedItem = cartItemsRepo.save(cartItem);
        return mapToCartItemDto(savedItem);
    }

    @Override
    public CartItemDto removeProductFromCart(int productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        String username = authenticationFacadeService.getCurentUsername();

        Cart cart = cartService.findCartByUsername(username);
        int cartId = cart.getId();
        int rowEffected = cartItemsRepo.deleteFromCartDetailByID(productId, cartId);
        if(rowEffected < 1) throw new InternalServerException("Server error, try again!");
        else {
            System.out.println("rs: " +rowEffected);
            return CartItemDto.builder()
                    .cartId(cartId)
                    .productId(productId)
                    .actived(false)
                    .quantity(0)
                    .build();
        }
    }

    @Override
    public CartResponse updateCartItems(CartUpdateRequest cartUpdateRequest) {
        String username = authenticationFacadeService.getCurentUsername();

        Cart cart = cartService.findCartByUsername(username);
        List<CartDetail> cartDetails =  cart.getCartDetails();
        cartDetails.clear();
        //update every item in cart after changing quantity...
        List<Product> products = productRepository.findAll();
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        cartUpdateRequest.getCartDetails().forEach(item -> {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ResourceNotFoundException(
                        String.format("Product with ID: %s not found", item.getProductId()));
            }

            CartDetailId cartItemId = new CartDetailId(item.getProductId(), cart.getId());

            CartDetail cartItem = new CartDetail();
            cartItem.setId(cartItemId);
            cartItem.setActived(true);
            cartItem.setQuantity(item.getProductQuantity()); //update new quantity from request

            Product product = productMap.get(item.getProductId());
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            //add item with updated to cart again
            cart.getCartDetails().add(cartItem);
            System.out.println(cartItem.getQuantity());
        });

        Cart savedCart = cartRepository.save(cart);
        //map
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(savedCart.getId());
        savedCart.getCartDetails().forEach(item -> {
            cartResponse.getCartDetails().add(mapToCartResponse(item));
        });

        return cartResponse;
    }

    private CartItemDto mapToCartItemDto(CartDetail cartDetail) {
        return mapper.map(cartDetail, CartItemDto.class);
    }

    private CartItemsResponse mapToCartResponse(CartDetail cartDetail) {
        return CartItemsResponse.builder()
                //.cartId(cartDetail.getId().getCartID())
                .productId(cartDetail.getProduct().getId())
                .productName(cartDetail.getProduct().getName())
                .productPrice(cartDetail.getProduct().getPrice())
                .cartDetailQuantity(cartDetail.getQuantity())
                .productThumbnail(cartDetail.getProduct().getThumbnail())
                .build();
    }


}
