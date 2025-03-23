package com.stream.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
//import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_table")
public class Course {

    @Id
    private String id;
    private String title;

    @OneToMany(mappedBy = "course")
    private List<Video> videos = new ArrayList<>();
}
