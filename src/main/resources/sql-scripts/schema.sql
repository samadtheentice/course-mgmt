CREATE TABLE cms_role (
  id bigint NOT NULL AUTO_INCREMENT,
  rolename varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE cms_user (
  id bigint NOT NULL AUTO_INCREMENT,
  firstname varchar(255) NOT NULL,
  lastname varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  emailid varchar(255) DEFAULT NULL,
  phonenumber bigint DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE cms_user_role (
  userid bigint NOT NULL,
  roleid bigint NOT NULL,
  CONSTRAINT cms_role_userid_fk FOREIGN KEY (userid) REFERENCES cms_user (id),
  CONSTRAINT cms_role_roleid_fk FOREIGN KEY (roleid) REFERENCES cms_role (id)
);


CREATE TABLE cms_course (
  id bigint NOT NULL AUTO_INCREMENT,
  userid bigint NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  duration int NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT cms_course_userid_fk FOREIGN KEY (userid) REFERENCES cms_user (id)
);

CREATE TABLE cms_enrollment (
  id bigint NOT NULL AUTO_INCREMENT,
  userid bigint NOT NULL,
  courseid bigint NOT NULL,
  enrolldate DATETIME NULL,
  PRIMARY KEY (id),
  CONSTRAINT cms_enroll_userid_fk FOREIGN KEY (userid) REFERENCES cms_user (id),
  CONSTRAINT cms_enroll_courseid_fk FOREIGN KEY (courseid) REFERENCES cms_course (id)
);


CREATE TABLE cms_course_content (
  id bigint NOT NULL AUTO_INCREMENT,
  courseid bigint NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NULL,
  duration int NULL,
  url varchar(255) NULL,
  contenttext TEXT NULL,
  PRIMARY KEY (id),
  CONSTRAINT cms_course_content_courseid_fk FOREIGN KEY (courseid) REFERENCES cms_course (id)
);

CREATE TABLE cms_assignment (
  id bigint NOT NULL AUTO_INCREMENT,
  contentid bigint NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NULL,
  duration varchar(255) NULL,
  url varchar(255) NULL,
  assignmenttext TEXT NULL,
  PRIMARY KEY (id),
  CONSTRAINT cms_assignment_contentid_fk FOREIGN KEY (contentid) REFERENCES cms_course_content (id)
);
 

CREATE TABLE cms_assignment_submission (
  id bigint NOT NULL AUTO_INCREMENT,
  enrollid bigint NOT NULL,
  assignmentid bigint NOT NULL,
  submissiontext TEXT NULL,
  submissiondate DATETIME NULL,
  PRIMARY KEY (id),
  CONSTRAINT cms_enroll_assignment_enrollid_fk FOREIGN KEY (enrollid) REFERENCES cms_enrollment (id),
  CONSTRAINT cms_enroll_assignment_assignmentid_fk FOREIGN KEY (assignmentid) REFERENCES cms_assignment (id)
);







