package kookmin.kuham.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.post.dto.request.SavePostRequest;
import kookmin.kuham.post.sevice.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "공고 생성")
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestPart("post") SavePostRequest savePostRequest, @RequestPart("images") MultipartFile[] images) throws IOException {
        postService.addPost(savePostRequest, images);
        return ResponseEntity.ok("공고 업로드 성공");
    }

    @Operation(summary = "공고 수정")
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(@Valid @RequestPart("post") SavePostRequest savePostRequest
            , @RequestPart("images") MultipartFile[] images
            , @PathVariable("postId") Long postId) throws IOException {
        postService.updatePost(savePostRequest, images,postId);
        return ResponseEntity.ok("공고 수정 성공");
    }

}
