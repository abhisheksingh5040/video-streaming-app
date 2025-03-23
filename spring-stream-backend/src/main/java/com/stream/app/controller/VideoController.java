package com.stream.app.controller;

import com.stream.app.entities.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/")
    public ResponseEntity<?> create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description) {
        Video video = Video.builder()
                .videoId(UUID.randomUUID().toString())
                .description(description)
                .title(title)
                .build();
        Video saveVideos = videoService.save(video, file);

        if (saveVideos != null) {
            return ResponseEntity.ok(saveVideos);
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomMessage.builder()
                        .message("Video not uploaded")
                        .success(Boolean.FALSE)
                        .build());
    }

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> fetchVideos(@PathVariable String videoId) {
        Video video = videoService.getById(videoId);
        String filePath = video.getFilePath();
        String contentType = video.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
