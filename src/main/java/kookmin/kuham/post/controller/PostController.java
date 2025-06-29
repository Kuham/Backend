package kookmin.kuham.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.post.dto.request.SavePostRequest;
import kookmin.kuham.post.dto.response.getPostsResponse;
import kookmin.kuham.post.sevice.PostService;
import kookmin.kuham.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kookmin.kuham.user.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @Operation(summary = "공고 생성")
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestPart("post") SavePostRequest savePostRequest, @RequestPart("images") MultipartFile[] images, @AuthenticationPrincipal String userId) throws IOException {
        postService.addPost(savePostRequest, images,userId);
        return ResponseEntity.ok("공고 업로드 성공");
    }

    @Operation(summary = "공고 수정")
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(@Valid @RequestPart("post") SavePostRequest savePostRequest
            , @RequestPart("images") MultipartFile[] images
            , @PathVariable("postId") Long postId
            , @AuthenticationPrincipal String userId
    ) throws IOException {
        postService.updatePost(savePostRequest, images,postId,userId);
        return ResponseEntity.ok("공고 수정 성공");
    }

    @Operation(summary = "공고 삭제")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId,
                                             @AuthenticationPrincipal String userId)  {
        postService.deletePost(postId,userId);
        return ResponseEntity.ok("공고 삭제 성공");
    }

    @Operation(summary = "자기 공고 모아보기",description = "자신 공고 모아보기 api(시간 역순)")
    @GetMapping("/my-posts")
    public ResponseEntity<List<getPostsResponse>> getMyPosts(@AuthenticationPrincipal String userId){

        userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        List<getPostsResponse> myPosts = postService.getPosts(userId);
        return ResponseEntity.ok(myPosts);
    }

    @Operation(summary = "전체 공고 모아보기",description = "전체 공고 모아보기 api(시간 역순)")
    @GetMapping("/all-posts")
    public ResponseEntity<List<getPostsResponse>> getAllPosts(){
        List<getPostsResponse> allPosts = postService.getPosts(null);
        return ResponseEntity.ok(allPosts);
    }

    @Operation(summary = "공고 상세 조회", description = "공고 상세 조회 api")
    @GetMapping("/{postId}")
    public ResponseEntity<getPostsResponse> getPostDetail(@PathVariable("postId") Long postId) {
        getPostsResponse postDetail = postService.getPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }

}
