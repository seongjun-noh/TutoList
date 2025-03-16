package com.tutolist.service.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tutolist.api.post.dto.request.PostCreateRequest;
import com.tutolist.api.post.dto.request.PostUpdateRequest;
import com.tutolist.api.post.dto.response.PostResponse;
import com.tutolist.common.enums.UserRole;
import com.tutolist.common.error.exception.ForbiddenException;
import com.tutolist.common.error.exception.NotFoundException;
import com.tutolist.domain.file.entity.PostFileEntity;
import com.tutolist.domain.post.entity.PostEntity;
import com.tutolist.domain.post.repository.PostRepository;
import com.tutolist.domain.user.entity.UserEntity;
import com.tutolist.domain.user.repository.UserRepository;
import com.tutolist.service.file.PostFileService;
import com.tutolist.service.post.mapper.PostMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostFileService postFileService;
	private final PostMapper postMapper;

	@Transactional
	public PostResponse createPost(Long userId, PostCreateRequest request, List<MultipartFile> files) {
		log.info("Creating new post for user: {}", userId);
		
		UserEntity user = findUserById(userId);
		PostEntity newPost = createPostEntity(user, request);
		PostEntity savedPost = postRepository.save(newPost);
		
		if (files != null && !files.isEmpty()) {
			postFileService.saveFiles(savedPost, files);
		}
		
		log.info("Post created successfully with ID: {}", savedPost.getId());
		return postMapper.toDto(savedPost);
	}

	@Transactional(readOnly = true)
	public Page<PostResponse> getPosts(Pageable pageable) {
		log.info("Fetching posts with pageable: {}", pageable);
		return postRepository.findAll(pageable).map(postMapper::toDto);
	}

	@Transactional
	public PostResponse getPost(Long postId) {
		log.info("Fetching post with ID: {}", postId);
		
		PostEntity post = findPostById(postId);
		post.addViewCount();
		PostEntity updatedPost = postRepository.save(post);
		
		log.info("Post fetched successfully with ID: {}", postId);
		return postMapper.toDto(updatedPost);
	}

	@Transactional
	public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest request, List<MultipartFile> files) {
		log.info("Updating post with ID: {} for user: {}", postId, userId);
		
		PostEntity post = findPostByIdAndUserId(postId, userId);
		updatePostContent(post, request);
		PostEntity updatedPost = postRepository.save(post);
		
		handleFileUpdates(postId, request.deletedFileNames(), files);
		
		log.info("Post updated successfully with ID: {}", postId);
		return postMapper.toDto(updatedPost);
	}

	@Transactional
	public void deleteUserPost(Long userId, UserRole role, Long postId) {
		log.info("Attempting to delete post with ID: {} for user: {} with role: {}", postId, userId, role);
		
		PostEntity post = findPostById(postId);
		validatePostAccess(userId, role, post);
		
		deletePostFiles(post);
		postRepository.delete(post);
		
		log.info("Post deleted successfully with ID: {}", postId);
	}

	private UserEntity findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
	}

	private PostEntity findPostById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
	}

	private PostEntity findPostByIdAndUserId(Long postId, Long userId) {
		return postRepository.findByIdAndUserId(postId, userId)
			.orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
	}

	private PostEntity createPostEntity(UserEntity user, PostCreateRequest request) {
		return PostEntity.builder()
			.user(user)
			.title(request.title())
			.content(request.content())
			.build();
	}

	private void updatePostContent(PostEntity post, PostUpdateRequest request) {
		post.updateTitle(request.title());
		post.updateContent(request.content());
	}

	private void handleFileUpdates(Long postId, List<String> deletedFileNames, List<MultipartFile> newFiles) {
		if (deletedFileNames != null && !deletedFileNames.isEmpty()) {
			deletedFileNames.forEach(fileName -> 
				postFileService.deleteFileByPostIdAndFileName(postId, fileName));
		}

		if (newFiles != null && !newFiles.isEmpty()) {
			PostEntity post = findPostById(postId);
			postFileService.saveFiles(post, newFiles);
		}
	}

	private void validatePostAccess(Long userId, UserRole role, PostEntity post) {
		if (UserRole.ADMIN != role && post.getUser().getId() != userId) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}

	private void deletePostFiles(PostEntity post) {
		List<PostFileEntity> files = post.getFiles();
		if (files != null && !files.isEmpty()) {
			files.forEach(file -> 
				postFileService.deleteFileByPostIdAndFileName(post.getId(), file.getStoredFileName()));
		}
	}
}
