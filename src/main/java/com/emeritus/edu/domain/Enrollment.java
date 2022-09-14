package com.emeritus.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
@Entity
@Table(name = "cms_enrollment")
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseid")
    private Course course;

    @Column(name = "enrolldate")
    private Instant enrollDate;
    
    @OneToMany(mappedBy = "enrollment")
    @JsonIgnore
    private Set<AssignmentSubmission> assignmentEnroll = new HashSet<>();
}