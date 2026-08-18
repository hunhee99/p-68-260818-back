package com.choi.p67260811.domain.post.post.controller;


import com.choi.p67260811.domain.post.post.dto.PostDto;
import com.choi.p67260811.domain.post.post.entity.Post;
import com.choi.p67260811.domain.post.post.service.PostService;
import com.choi.p67260811.global.dto.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "ApiV1CommentController", description = "게시물 API")
public class ApiV1PostController {

    private final PostService postService;

    @GetMapping()
    @Operation(summary = "다건 조회")
    public List<PostDto> list() {
        List<Post> postList = postService.findAll();

        List<PostDto> postDtoList = postList.stream()
                .map(PostDto::new)
                .toList();

        return postDtoList;
    }

    record PostWriteReqBody(
            @Size(min = 2, max = 10, message = "제목은 2글자 이상 10글자 이하로 작성")
            @NotBlank(message = "제목을 입력해주세요.")
            String title,

            @Size(min = 2, max = 10, message = "내용은 2글자 이상 10글자 이하로 작성")
            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ){}


    @PostMapping()
    @Transactional
    public RsData<PostDto> write(
            @Valid @RequestBody PostWriteReqBody reqBody
            ){
        Post post = this.postService.write(reqBody.title, reqBody.content);
        return  new RsData<>(
                "201-1",
                "%d번 글이 성공적으로 등록되었습니다".formatted(post.getId()),
                new PostDto(post)
                );
    }

    record PostModifyReqBody(
            @Size(min = 2, max = 10, message = "제목은 2글자 이상 10글자 이하로 작성")
            @NotBlank(message = "제목을 입력해주세요.")
            String title,

            @Size(min = 2, max = 10, message = "내용은 2글자 이상 10글자 이하로 작성")
            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ){}


    @PatchMapping("/{id}")
    @Transactional
    public RsData<Void> modify(
            @Valid @RequestBody PostModifyReqBody reqBody,
            @PathVariable int id
    ) {
        Post post = postService.findById(id).get();
        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 게시물이 수정되었습니다.".formatted(id)
        );
    }

    @Operation(summary = "단건 조회")
    @GetMapping("/{id}")
    public PostDto detail(@PathVariable int id) {

        Post post = postService.findById(id).get();

        return new PostDto(post);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "글 삭제")
    public RsData<PostDto> delete(@PathVariable int id) {
        Post post = postService.findById(id).get();
        postService.delete(id);

        return new RsData<>(
                "200-1",
                "%d번 게시물이 삭제되었습니다.".formatted(id)
        );
    }


}