package com.cms.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
@Entity
@Table(name = "cms_course")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy="course", cascade = CascadeType.MERGE)
    private Set<CourseContent> courseContents;
    
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
}