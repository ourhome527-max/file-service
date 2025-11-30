package com.example.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.FileMeta;
import com.example.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
	private final FileMapper fileMapper;

	private final String UPLOAD_DIR = "/upload/files/";

	public FileMeta uploadFile(MultipartFile multipartFile, int articleId) throws IOException {
		String savedName = UUID.randomUUID().toString();
		String extension = "";

		if (multipartFile.getOriginalFilename().contains(".")) {
			extension = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			savedName = savedName + extension;
		}

		File directory = new File(UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(UPLOAD_DIR + savedName);
		multipartFile.transferTo(file);

		FileMeta meta = new FileMeta();
		meta.setArticleId(articleId);
		meta.setOriginalName(multipartFile.getOriginalFilename());
		meta.setSavedName(savedName);
		meta.setPath(UPLOAD_DIR + savedName);
		meta.setSize(multipartFile.getSize());
		meta.setContentType(multipartFile.getContentType());

		fileMapper.insertFile(meta);

		return meta;
	}

	public FileMeta getFile(int articleId) {
		return fileMapper.findFileById(articleId);
	}
}
