package com.stream.app.service;

import com.stream.app.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    // save video
    Video save(Video video, MultipartFile file);

    // get video by Id
    Video getById(String videoId);

    // get video by title
    Video getByTitle(String videoTitle);

    // get all videos
    List<Video> getAllVideos();
}
