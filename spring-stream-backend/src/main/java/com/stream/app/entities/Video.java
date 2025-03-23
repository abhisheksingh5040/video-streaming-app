package com.stream.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_table")
public class Video {

    @Id
    private String videoId;
    private String title;
    private String description;
    private String contentType;
    private String filePath;

    @ManyToOne
    private Course course;

}
