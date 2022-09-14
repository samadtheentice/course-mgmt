INSERT INTO cms_role (id, rolename, description) VALUES (1, 'SYSTEM_ADMIN_USER', 'System Admin ');
INSERT INTO cms_role (id, rolename, description) VALUES (2, 'INSTRUCTOR_USER', 'Instructor ');
INSERT INTO cms_role (id, rolename, description) VALUES (3, 'STUDENT_USER', 'Student ');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO cms_user (id, firstname, lastname, password, username, emailid, phonenumber) VALUES (1, 'Abdul', 'Samad', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'abdul.samad','samadtheentice@gmail.com','1223342323');
INSERT INTO cms_user (id, firstname, lastname, password, username, emailid, phonenumber) VALUES (2, 'Instructor', 'Instructor', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'instructor','instructor@gmail.com','2323342323');
INSERT INTO cms_user (id, firstname, lastname, password, username, emailid, phonenumber) VALUES (3, 'Student', 'Student', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'student','student@gmail.com','3445231232');


INSERT INTO cms_user_role(userid, roleid) VALUES(1,1);
INSERT INTO cms_user_role(userid, roleid) VALUES(2,2);
INSERT INTO cms_user_role(userid, roleid) VALUES(3,3);

