package com.slippery.lmsexample.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "Course_Id")
    @JsonIgnore
    private Course course;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Lesson> lessonsInModule;
    @ManyToOne
    private User tutor;

}
