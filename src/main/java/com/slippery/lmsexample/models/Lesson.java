package com.slippery.lmsexample.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String slug;
    @Lob
    private String content;
    @ManyToOne
    private CourseModule courseModule;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Quiz quiz;

}
