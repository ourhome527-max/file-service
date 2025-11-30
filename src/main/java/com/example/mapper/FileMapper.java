package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.FileMeta;

@Mapper
public interface FileMapper {
	int insertFile(FileMeta fileMeta);

	FileMeta findFileById(int articleId);

	List<FileMeta> findFilesByArticleId(int articleId);
}