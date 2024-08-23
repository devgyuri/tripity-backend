package me.gyuri.tripity.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.comment.dto.AddCommentRequest;
import me.gyuri.tripity.domain.comment.dto.AddCommentResponse;
import me.gyuri.tripity.domain.comment.entity.Comment;
import me.gyuri.tripity.domain.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comments")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody AddCommentRequest request, Principal principal) {
        Comment savedComment = commentService.addComment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddCommentResponse(savedComment));
    }
}
