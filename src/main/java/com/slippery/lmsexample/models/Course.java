package com.slippery.lmsexample.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @NotNull
    private String title;
    @NotNull
    @Lob
    private String description;
    @OneToMany
    private List<CourseModule> modulesInCourse =new ArrayList<>();
    @NotNull
    private Long price;
    @OneToMany
    private List<User> enrolledLearners;
    @ManyToOne
    private User instructorDetails;
    private LocalDateTime createdOn =LocalDateTime.now();
    private LocalDateTime updatedOn =null;

}
