package com.tutolist.service.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tutolist.api.file.dto.response.FileResponse;
import com.tutolist.common.error.exception.BadRequestException;
import com.tutolist.common.error.exception.InternalServerErrorException;
import com.tutolist.common.error.exception.NotFoundException;
import com.tutolist.common.util.FileUtil;
import com.tutolist.domain.file.entity.PostFileEntity;
import com.tutolist.domain.file.repository.PostFileRepository;
import com.tutolist.domain.post.entity.PostEntity;
import com.tutolist.service.file.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostFileService {
	private final PostFileRepository postFileRepository;
	private final FileMapper fileMapper;

	@Value("${file.upload-dir}")
	private String uploadDir;

	private static final int MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

	private static final String[] BLACKLISTED_MIME_TYPES = {
		"application/x-msdownload", // exe, dll 등 실행 파일
		"application/x-sh",         // 쉘 스크립트
		"application/x-bat",        // 배치 파일
		"application/javascript",   // 스크립트 (특정 상황에서는 위험할 수 있음)
		"text/javascript",          // 스크립트
		"application/x-php",        // PHP 파일
		"application/php",          // PHP 파일
		"text/php",                 // PHP 파일
		"application/x-msdos-program" // DOS 프로그램
	};

	@Transactional
	public PostFileEntity saveFile(PostEntity post, MultipartFile file) throws IOException {
		log.info("Saving file for post ID: {}", post.getId());
		
		validateFile(file);
		String storedFileName = FileUtil.createStoredFileName(file.getOriginalFilename());
		String filePath = uploadDir + "/" + storedFileName;
		
		FileUtil.saveFileToLocal(file, uploadDir, storedFileName);
		
		PostFileEntity fileEntity = createFileEntity(post, file, storedFileName, filePath);
		PostFileEntity savedFile = postFileRepository.save(fileEntity);
		
		log.info("File saved successfully: {}", storedFileName);
		return savedFile;
	}

	@Transactional
	public void saveFiles(PostEntity post, List<MultipartFile> files) {
		if (files == null || files.isEmpty()) {
			return;
		}
		
		log.info("Saving {} files for post ID: {}", files.size(), post.getId());
		List<PostFileEntity> savedFiles = new ArrayList<>();

		try {
			for (MultipartFile file : files) {
				if (file.isEmpty()) {
					continue;
				}
				savedFiles.add(saveFile(post, file));
			}
			postFileRepository.saveAll(savedFiles);
			log.info("All files saved successfully for post ID: {}", post.getId());
		} catch (IOException e) {
			handleFileSaveError(savedFiles);
			throw new InternalServerErrorException("파일 저장에 실패했습니다.");
		}
	}

	@Transactional(readOnly = true)
	public List<FileResponse> getFilesByPostId(Long postId) {
		log.info("Fetching files for post ID: {}", postId);
		List<PostFileEntity> files = postFileRepository.findByPostId(postId);
		return files.stream().map(fileMapper::toDto).toList();
	}

	@Transactional(readOnly = true)
	public FileResponse getFilesByPostIdAndStoredFileName(Long postId, String storedFileName) {
		log.info("Fetching file: {} for post ID: {}", storedFileName, postId);
		PostFileEntity file = findFileByPostIdAndStoredFileName(postId, storedFileName);
		return fileMapper.toDto(file);
	}

	@Transactional
	public void deleteFileByPostIdAndFileName(Long postId, String fileName) {
		log.info("Deleting file: {} for post ID: {}", fileName, postId);
		postFileRepository.findByPostIdAndStoredFileName(postId, fileName)
			.ifPresent(this::deleteFile);
	}

	private void validateFile(MultipartFile file) {
		String contentType = file.getContentType();
		if (FileUtil.isValidContentType(contentType, BLACKLISTED_MIME_TYPES)) {
			throw new BadRequestException("허용되지 않는 파일 형식입니다.");
		}

		if (file.getSize() > MAX_FILE_SIZE) {
			throw new BadRequestException("파일 크기가 제한을 초과했습니다.");
		}
	}

	private PostFileEntity createFileEntity(PostEntity post, MultipartFile file, 
			String storedFileName, String filePath) {
		return PostFileEntity.builder()
			.originalFileName(file.getOriginalFilename())
			.storedFileName(storedFileName)
			.fileSize(file.getSize())
			.fileType(file.getContentType())
			.filePath(filePath)
			.post(post)
			.build();
	}

	private void handleFileSaveError(List<PostFileEntity> savedFiles) {
		savedFiles.forEach(file -> {
			try {
				FileUtil.deleteLocalFile(file.getFilePath());
			} catch (Exception e) {
				log.error("Failed to delete file: {}", file.getFilePath(), e);
			}
		});
		log.error("Failed to save files", new InternalServerErrorException("파일 저장에 실패했습니다."));
	}

	private PostFileEntity findFileByPostIdAndStoredFileName(Long postId, String storedFileName) {
		return postFileRepository.findByPostIdAndStoredFileName(postId, storedFileName)
			.orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));
	}

	private void deleteFile(PostFileEntity file) {
		postFileRepository.delete(file);
		// TODO: 임시 폴더로 이동 후 트랜잭션이 실패 시 복구, 성공 시 삭제
		FileUtil.deleteLocalFile(file.getFilePath());
	}
}

