package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.CommentRequest;
import com.nashtech.ecommercialwebsite.dto.response.CommentResponse;
import com.nashtech.ecommercialwebsite.dto.response.ListCommentResponse;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    CommentResponse comment(CommentRequest commentRequest, int productId);

    ListCommentResponse getAllCommentsByProduct(int pageNo,
                                                int pageSize,
                                                String sortBy,
                                                String sortDir,
                                                int productId);

    CommentResponse deleteComment(int commentId);

}
