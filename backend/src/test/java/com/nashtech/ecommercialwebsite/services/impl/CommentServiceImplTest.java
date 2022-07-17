package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Comment;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.CommentRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.CommentRequest;
import com.nashtech.ecommercialwebsite.dto.response.CommentResponse;
import com.nashtech.ecommercialwebsite.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    JwtService jwtService;
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper mapper;
    @InjectMocks
    CommentServiceImpl commentServiceImpl;
    Product product;
    HttpServletRequest request;
    Account account;
    CommentResponse expectedCommentRes;
    CommentRequest commentRequest;
    String username = "username";
    String token = "token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = mock(Product.class);
        request = mock(HttpServletRequest.class);
        account = mock(Account.class);
        expectedCommentRes = mock(CommentResponse.class);
        commentRequest = new CommentRequest("hello");


    }

    @Disabled
    @Test
    void givenCommentRequestAndHttpRequest_whenComment_thenReturnCommentResponse() {
        //given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(jwtService.parseJwt(request)).thenReturn(token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(userRepository.findAccountByUsername(username)).thenReturn(Optional.of(account));
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
//        verify(capturedComment)
        ArgumentCaptor<LocalDateTime> dateTimeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        Comment comment = mock(Comment.class);
//        verify(comment).setCmtTime(dateTimeCaptor.capture());
//        verify(comment).setAccount(account);
        when(Comment.builder()
                .message(commentRequest.getMessage())
                .cmtTime(dateTimeCaptor.capture())
                .product(product)
                .account(account)
                .build()).thenReturn(comment);

//        verify(commentRepository).save(capturedComment.capture());
//        Comment capturedCommentValue = capturedComment.getValue();

        verify(commentRepository).save(commentCaptor.capture());
        Comment savedComment = mock(Comment.class);
        when(commentRepository.save(commentCaptor.capture())).thenReturn(savedComment);
        when(mapper.map(savedComment, CommentResponse.class)).thenReturn(expectedCommentRes);

        //when
        CommentResponse actualCommentResponse = commentServiceImpl.comment(commentRequest, request, 1);
        //then
        assertThat(dateTimeCaptor.getValue()).isEqualTo(actualCommentResponse.getCmtTime());
        verify(expectedCommentRes).setUserName(username);
        assertThat(actualCommentResponse).isEqualTo(expectedCommentRes);
    }

    @Test
    void deleteComment() {
    }
}