package com.project.project_java_webservice_quanlykhoahocvachamdiem.service;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.CreateCourseRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.CourseResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Course;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseResponse create(CreateCourseRequest request) {
        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        courseRepository.save(course);
        return toResponse(course);
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found: " + id));
        return toResponse(course);
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id))
            throw new RuntimeException("Course not found: " + id);
        courseRepository.deleteById(id);
    }

    private CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .build();
    }
}