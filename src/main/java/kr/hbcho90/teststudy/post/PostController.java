package kr.hbcho90.teststudy.post;

import kr.hbcho90.teststudy.post.dto.PostDto;
import kr.hbcho90.teststudy.post.dto.PostListResponse;
import kr.hbcho90.teststudy.post.dto.PostRequest;
import kr.hbcho90.teststudy.post.dto.PostResponse;
import kr.hbcho90.teststudy.user.UserService;
import kr.hbcho90.teststudy.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts")
    public ResponseEntity<PostListResponse> getPostList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<PostDto> result = postService.getPostList(page, size);
        PostListResponse response = PostListResponse.builder()
                .total((int) result.getTotalElements())
                .page(result.getNumber() + 1)
                .size(result.getSize())
                .code("0000")
                .message("SUCCESS")
                .posts(result.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable(name = "postId") String postId) {
        PostDto postInfo = postService.getPostInfo(postId);
        PostResponse response = PostResponse.builder()
                .code("0000")
                .message("SUCCESS")
                .post(postInfo)
                .build();
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/posts")
//    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
////        UserDto userInfo = userService.getUserInfo(request.userId());
//        PostDto postInfo = postService.registerPost(PostDto.builder()
//                        .title(request.title())
//                        .content(request.content())
////                        .user(userInfo)
//                        .createdAt(LocalDateTime.now())
//                        .modifiedAt(LocalDateTime.now())
//                .build())
//                ;
//        PostResponse response = PostResponse.builder()
//                .code("0000")
//                .message("SUCCESS")
//                .post(postInfo)
//                .build();
//        return ResponseEntity.ok(response);
//    }
}
