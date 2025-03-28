package com.slippery.lmsexample.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String role;
    private Long points =0L;
    @OneToMany
    @JsonIgnore
    private List<Course> courseList;
    @OneToMany
    @JsonIgnore
    private List<Course> enrolledCourses;

    private LocalDateTime enrolledOn =LocalDateTime.now();
    private String cohort;
    @OneToMany
    private List<CourseModule> userCourseModules;
    private String Location;
    private String githubAccount;
    private String linkedinAccount;
    @Lob
    private String userDescription;
    private String mobileNumber;
    @Lob
    private byte[] profileImage;
}
