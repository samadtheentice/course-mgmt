package com.emeritus.edu.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Entity
@Table(name="cms_role")
@Getter
@Setter
public class Role {
    private static final long serialVersionUID = 1L;
    
    public Role(){
    	
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="rolename")
    private String roleName;

    @Column(name="description")
    private String description;   
    
	@ManyToMany(fetch = FetchType.LAZY,
	cascade = {
		CascadeType.MERGE
	},
	mappedBy = "roles")
	@JsonIgnore
	private Set<User> users = new HashSet<>();
	    	  
    	  
}