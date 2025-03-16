package com.tutolist.api.post.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutolist.api.post.dto.request.PostCreateRequest;
import com.tutolist.api.post.dto.request.PostUpdateRequest;
import com.tutolist.api.post.dto.response.PostResponse;
import com.tutolist.common.dto.ApiResponse;
import com.tutolist.common.enums.UserRole;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.service.post.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {
	private final PostService postService;

	@Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
	@PostMapping(value = "/create", consumes = { "multipart/form-data" })
	public ResponseEntity<ApiResponse<PostResponse>> createPost(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@Valid @RequestPart("post") PostCreateRequest postData,
			@RequestPart(name = "files", required = false) List<MultipartFile> files) {
		
		Long userId = principalDetails.getId();
		log.info("Creating new post by user: {}", userId);
		
		PostResponse post = postService.createPost(userId, postData, files);
		return ResponseEntity.ok(ApiResponse.success("게시글이 작성되었습니다.", post));
	}

	@Operation(summary = "게시글 목록 조회", description = "게시글 목록을 페이지네이션하여 조회합니다.")
	@GetMapping()
	public ResponseEntity<ApiResponse<PagedModel<PostResponse>>> getPosts(Pageable pageable) {
		log.info("Fetching posts with pageable: {}", pageable);
		Page<PostResponse> posts = postService.getPosts(pageable);
		return ResponseEntity.ok(ApiResponse.success("게시글 목록을 조회했습니다.", new PagedModel<>(posts)));
	}

	@Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보를 조회합니다.")
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable(name = "postId") Long postId) {
		log.info("Fetching post with id: {}", postId);
		PostResponse post = postService.getPost(postId);
		return ResponseEntity.ok(ApiResponse.success("게시글을 조회했습니다.", post));
	}

	@Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
	@PutMapping(value = "/{postId}/update", consumes = { "multipart/form-data" })
	public ResponseEntity<ApiResponse<PostResponse>> updatePost(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(name = "postId") Long postId,
			@Valid @RequestPart("postData") PostUpdateRequest postData,
			@RequestPart(name = "files", required = false) List<MultipartFile> files) {
		
		Long userId = principalDetails.getId();
		log.info("Updating post {} by user: {}", postId, userId);
		
		PostResponse post = postService.updatePost(userId, postId, postData, files);
		return ResponseEntity.ok(ApiResponse.success("게시글이 수정되었습니다.", post));
	}

	@Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse<Void>> deletePost(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(name = "postId") Long postId) {
		
		Long userId = principalDetails.getId();
		UserRole userRole = principalDetails.getRole();
		log.info("Deleting post {} by user: {} with role: {}", postId, userId, userRole);
		
		postService.deleteUserPost(userId, userRole, postId);
		return ResponseEntity.ok(ApiResponse.success("게시글이 삭제되었습니다.", null));
	}
}
