package com.emeritus.edu.domain;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
@Entity
@Table(name = "cms_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;
    
    @Column(name = "emailid")
    private String emailId;
    
    @Column(name = "phonenumber")
    private String phoneNumber;
    
    public User() {
	}
    

    @ManyToMany(fetch = FetchType.EAGER,
    	      cascade = {
    	          CascadeType.MERGE
    	      })
    @JoinTable(name = "cms_user_role", joinColumns
            = @JoinColumn(name = "userid",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleid",
                    referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();
}