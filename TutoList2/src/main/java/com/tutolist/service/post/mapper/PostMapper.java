package com.tutolist.service.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.tutolist.api.post.dto.response.PostResponse;
import com.tutolist.domain.post.entity.PostEntity;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PostMapper {

	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "userName", source = "user.name")
	@Mapping(target = "hasFiles", expression = "java(post.getFiles() != null && !post.getFiles().isEmpty() && post.getFiles().size() > 0)")
	PostResponse toDto(PostEntity post);
}

