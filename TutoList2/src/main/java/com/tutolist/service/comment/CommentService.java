package com.tutolist.service.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutolist.api.comment.dto.request.CommentCreateRequest;
import com.tutolist.api.comment.dto.request.CommentUpdateRequest;
import com.tutolist.api.comment.dto.response.CommentResponse;
import com.tutolist.common.enums.UserRole;
import com.tutolist.common.error.exception.ForbiddenException;
import com.tutolist.common.error.exception.NotFoundException;
import com.tutolist.domain.comment.entity.CommentEntity;
import com.tutolist.domain.comment.repository.CommentRepository;
import com.tutolist.domain.post.entity.PostEntity;
import com.tutolist.domain.post.repository.PostRepository;
import com.tutolist.domain.user.entity.UserEntity;
import com.tutolist.domain.user.repository.UserRepository;
import com.tutolist.service.comment.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;

	@Transactional
	public CommentResponse createComment(Long userId, Long postId, CommentCreateRequest request) {
		log.info("Creating new comment for user: {} on post: {}", userId, postId);
		
		UserEntity user = findUserById(userId);
		PostEntity post = findPostById(postId);
		CommentEntity parentComment = findParentCommentIfExists(request.parentCommentId(), postId);
		
		CommentEntity newComment = createCommentEntity(user, post, parentComment, request);
		CommentEntity savedComment = commentRepository.save(newComment);
		
		log.info("Comment created successfully with ID: {}", savedComment.getId());
		return commentMapper.toDto(savedComment);
	}

	@Transactional(readOnly = true)
	public Page<CommentResponse> getPostComments(Long postId, Pageable pageable) {
		log.info("Fetching comments for post: {} with pageable: {}", postId, pageable);
		return commentRepository.findAllByPostId(postId, pageable).map(commentMapper::toDto);
	}

	@Transactional
	public CommentResponse updateComment(Long userId, Long postId, Long commentId, CommentUpdateRequest request) {
		log.info("Updating comment with ID: {} for user: {} on post: {}", commentId, userId, postId);
		
		CommentEntity comment = findCommentByIdAndPostIdAndUserId(commentId, postId, userId);
		comment.updateContent(request.content());
		CommentEntity updatedComment = commentRepository.save(comment);
		
		log.info("Comment updated successfully with ID: {}", commentId);
		return commentMapper.toDto(updatedComment);
	}

	@Transactional
	public void deleteComment(Long userId, UserRole role, Long postId, Long commentId) {
		log.info("Attempting to delete comment with ID: {} for user: {} with role: {} on post: {}", 
			commentId, userId, role, postId);
		
		CommentEntity comment = findCommentByIdAndPostId(commentId, postId);
		validateCommentAccess(userId, role, comment);
		
		commentRepository.delete(comment);
		log.info("Comment deleted successfully with ID: {}", commentId);
	}

	private UserEntity findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
	}

	private PostEntity findPostById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
	}

	private CommentEntity findParentCommentIfExists(Long parentCommentId, Long postId) {
		if (parentCommentId == null) {
			return null;
		}
		
		return commentRepository.findByIdAndPostId(parentCommentId, postId)
			.orElseThrow(() -> new NotFoundException("부모 댓글을 찾을 수 없습니다."));
	}

	private CommentEntity findCommentByIdAndPostId(Long commentId, Long postId) {
		return commentRepository.findByIdAndPostId(commentId, postId)
			.orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
	}

	private CommentEntity findCommentByIdAndPostIdAndUserId(Long commentId, Long postId, Long userId) {
		return commentRepository.findByIdAndPostIdAndUserId(commentId, postId, userId)
			.orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
	}

	private CommentEntity createCommentEntity(UserEntity user, PostEntity post, 
			CommentEntity parentComment, CommentCreateRequest request) {
		return CommentEntity.builder()
			.content(request.content())
			.user(user)
			.post(post)
			.parentComment(parentComment)
			.build();
	}

	private void validateCommentAccess(Long userId, UserRole role, CommentEntity comment) {
		if (UserRole.ADMIN != role && comment.getUser().getId() != userId) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}
}
