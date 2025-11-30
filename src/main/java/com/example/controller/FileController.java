package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.FileMeta;
import com.example.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Slf4j
public class FileController {
	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<FileMeta> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("articleId") int articleId) {
		log.info("requesT: {}", articleId);
		try {
			FileMeta meta = fileService.uploadFile(file, articleId);
			return ResponseEntity.ok(meta);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{fileId}")
	public ResponseEntity<FileMeta> getFileMeta(@PathVariable int fileId) {
		FileMeta meta = fileService.getFile(fileId);
		if (meta == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(meta);
	}
}
