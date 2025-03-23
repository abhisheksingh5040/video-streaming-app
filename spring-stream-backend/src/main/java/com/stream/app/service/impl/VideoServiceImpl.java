package com.stream.app.service.impl;

import com.stream.app.entities.Video;
import com.stream.app.repository.VideoRepository;
import com.stream.app.service.VideoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Value("${files.videos}")
    private String DIR;

    @PostConstruct
    public void init() {
        File file = new File(DIR);

        if (!file.exists()) {
            file.mkdir();
            log.info("folder created: {}", file);
        } else {
            log.info("folder already created: {}", file);
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {
        try {
            // original file name
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            // folder path: create
            String cleanFileName = StringUtils.cleanPath(filename);
            String cleanFolder = StringUtils.cleanPath(DIR);

            // folder path with filename
            Path path = Paths.get(cleanFolder, cleanFileName);
            log.info("file path : {}", path);

            // copy file to the folder
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            // video metadata
            video.setContentType(contentType);
            video.setFilePath(path.toString());

            // meta data save
            return videoRepository.save(video);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Video getById(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }

    @Override
    public Video getByTitle(String videoTitle) {
        return null;
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
