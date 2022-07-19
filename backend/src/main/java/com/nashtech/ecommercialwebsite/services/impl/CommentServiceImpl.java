package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Comment;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.CommentRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.CommentRequest;
import com.nashtech.ecommercialwebsite.dto.response.CommentResponse;
import com.nashtech.ecommercialwebsite.dto.response.ListCommentResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.exceptions.UnauthorizedException;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.CommentService;
import com.nashtech.ecommercialwebsite.services.JwtService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    private final ProductRepository productRepo;

    private final JwtService jwtService;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepo;

    private ModelMapper mapper;

    private final AuthenticationFacadeService authenticationFacadeService;

    @Override
    public CommentResponse comment(CommentRequest commentRequest, int productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with Id %s not found", productId))
                );

        String username = authenticationFacadeService.getCurentUsername();
        Account account = userRepo.findAccountByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account %s not found", username)));

        Comment comment = Comment.builder()
                .message(commentRequest.getMessage())
                .cmtTime(LocalDateTime.now())
                .product(product)
                .account(account)
                .build();
        Comment savedComment = commentRepo.save(comment);

        CommentResponse commentResponse = mapper.map(savedComment, CommentResponse.class);
        commentResponse.setUserName(account.getUsername());

        return commentResponse;
    }

    @Override
    public ListCommentResponse getAllCommentsByProduct(int pageNo,
                                                       int pageSize,
                                                       String sortBy,
                                                       String sortDirection,
                                                       int productId) {
        String username = authenticationFacadeService.getCurentUsername();

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("PRoduct with Id %s not found", productId))
                );
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> comments = commentRepo.findCommentsByProduct(pageable, product);
        return getContent(comments, username);
    }

    @Override
    public CommentResponse deleteComment(int commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Comment with ID: %s not found", commentId)));

        commentRepo.delete(comment);
       return mapper.map(comment, CommentResponse.class);
    }

    private CommentResponse mapToDto(Comment comment) {
        CommentResponse commentResponse = mapper.map(comment, CommentResponse.class);
        commentResponse.setUserName(comment.getAccount().getUsername());
        return commentResponse;
    }

    private ListCommentResponse getContent(Page<Comment> comments, String username) {
        List<Comment> commentList = comments.getContent();
        List<CommentResponse> content = commentList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ListCommentResponse.builder()
                .commentContent(content)
                .pageNo(comments.getNumber())
                .pageSize(comments.getSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .last(comments.isLast())
                .usernameAccess(username)
                .build();
    }
}
