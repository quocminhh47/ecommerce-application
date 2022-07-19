package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.CommentRequest;
import com.nashtech.ecommercialwebsite.dto.response.CommentResponse;
import com.nashtech.ecommercialwebsite.dto.response.ListCommentResponse;
import com.nashtech.ecommercialwebsite.services.CommentService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "Comment about product feature",
        description = "This include comment about product, delete comment, show coment list by product")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/user/api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get al comments by product", description = "Provide all comments of the specified product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieve"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - url or product id provided was not found"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
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
            @PathVariable("productId") int productId)
    {
        return commentService.getAllCommentsByProduct(pageNo, pageSize, sortBy, sortDir, productId);
    }

    @PostMapping("/product/{productId}")
    @Operation(summary = "Create comment about product", description = "Help customer to comment about a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url or product resource was not found"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse commentProduct(@Valid @RequestBody CommentRequest commentRequest,
                                          @PathVariable("productId") int productId) {
        return commentService.comment(commentRequest,  productId);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete comment", description = "Provide ability of deleting a comment ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully deleted"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url was not found"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server")
    })
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse deleteComment(@PathVariable("commentId") int id) {
        return commentService.deleteComment(id);
    }


}
