package com.slippery.lmsexample.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Module> modulesInCourse =new ArrayList<>();
    @NotNull
    private Long price;
    @OneToMany
    private List<User> enrolledLearners;
    @ManyToOne
    private User instructorDetails;

}
