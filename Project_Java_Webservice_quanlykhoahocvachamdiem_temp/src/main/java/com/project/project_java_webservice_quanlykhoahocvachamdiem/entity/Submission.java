package com.project.project_java_webservice_quanlykhoahocvachamdiem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "submissions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private String githubLink;
    private String reportUrl;
    private Double score;
    private String feedback;

    @Builder.Default
    private String status = "PENDING";
}