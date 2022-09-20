package com.cms.edu.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 */
@Entity
@Table(name = "cms_assignment_submission")
@Getter
@Setter
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "enrollid")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "assignmentid")
    private Assignment assignment;
    
    @Column(name = "submissiontext")
    private String submissiontext;
    
    @Column(name = "submissiondate")
    private Instant submissionDate;

}