package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.CommentRequest;
import com.nashtech.ecommercialwebsite.dto.response.CommentResponse;
import com.nashtech.ecommercialwebsite.dto.response.ListCommentResponse;
import com.nashtech.ecommercialwebsite.services.CommentService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/user/api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ListCommentResponse getAllCommentsByProduct(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = "cmtTime", required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = "desc", required = false)
                    String sortDir,
            @PathVariable("productId") int productId,
            HttpServletRequest request)
    {
        return commentService.getAllCommentsByProduct(pageNo, pageSize, sortBy, sortDir, productId, request);
    }

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse commentProduct(@Valid @RequestBody CommentRequest commentRequest,
                                          HttpServletRequest request,
                                          @PathVariable("productId") int productId) {
        return commentService.comment(commentRequest, request, productId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse deleteComment(@PathVariable("commentId") int id,
                                         HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }


}
