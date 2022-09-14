package com.emeritus.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

import java.util.Set;


@Entity
@Table(name = "cms_course_content")
@Getter
@Setter
public class CourseContent {
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
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "coursecontenttext")
    private String courseContentText;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="courseid",
            referencedColumnName = "id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy="courseContent", cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private Set<Assignment> assignments;
}