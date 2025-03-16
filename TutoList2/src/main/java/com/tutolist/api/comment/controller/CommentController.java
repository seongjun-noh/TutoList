package com.tutolist.api.comment.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutolist.api.comment.dto.request.CommentCreateRequest;
import com.tutolist.api.comment.dto.request.CommentUpdateRequest;
import com.tutolist.api.comment.dto.response.CommentResponse;
import com.tutolist.common.dto.ApiResponse;
import com.tutolist.common.enums.UserRole;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.service.comment.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {
	private final CommentService commentService;

	@Operation(summary = "댓글 작성", description = "게시글에 새로운 댓글을 작성합니다.")
	@PostMapping("/posts/{postId}/comments/create")
	public ResponseEntity<ApiResponse<CommentResponse>> createComment(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(name = "postId") Long postId,
			@Valid @RequestBody CommentCreateRequest request) {
		
		Long userId = principalDetails.getId();
		log.info("Creating comment for post {} by user: {}", postId, userId);
		
		CommentResponse comment = commentService.createComment(userId, postId, request);
		return ResponseEntity.ok(ApiResponse.success("댓글이 작성되었습니다.", comment));
	}

	@Operation(summary = "댓글 목록 조회", description = "게시글의 댓글 목록을 페이지네이션하여 조회합니다.")
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<ApiResponse<PagedModel<CommentResponse>>> getComments(
			@PathVariable(name = "postId") Long postId,
			Pageable pageable) {
		
		log.info("Fetching comments for post {} with pageable: {}", postId, pageable);
		Page<CommentResponse> comments = commentService.getPostComments(postId, pageable);
		return ResponseEntity.ok(ApiResponse.success("댓글 목록을 조회했습니다.", new PagedModel<>(comments)));
	}

	@Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.")
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "commentId") Long commentId,
			@Valid @RequestBody CommentUpdateRequest request) {
		
		Long userId = principalDetails.getId();
		log.info("Updating comment {} for post {} by user: {}", commentId, postId, userId);
		
		CommentResponse comment = commentService.updateComment(userId, postId, commentId, request);
		return ResponseEntity.ok(ApiResponse.success("댓글이 수정되었습니다.", comment));
	}

	@Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<ApiResponse<Void>> deleteComment(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "commentId") Long commentId) {
		
		Long userId = principalDetails.getId();
		UserRole role = principalDetails.getRole();
		log.info("Deleting comment {} for post {} by user: {} with role: {}", commentId, postId, userId, role);
		
		commentService.deleteComment(userId, role, postId, commentId);
		return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다.", null));
	}
}
