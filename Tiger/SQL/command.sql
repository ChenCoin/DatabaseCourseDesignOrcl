drop table GradeInfo;
drop table CourseInfo;
drop table TeacherInfo;
drop table StudentInfo;

create table StudentInfo(
	StudentID int NOT NULL,
	name varchar(64) NOT NULL,
	age int,
	sex varchar2(8) check(sex in('male','female')),
	class varchar(64),
	time timestamp,
	PRIMARY KEY (StudentID)
);

create table TeacherInfo(
	TeacherID varchar(64) NOT NULL,
	name varchar(64) NOT NULL,
	age int,
	sex varchar2(8) check(sex in('male','female')),
	time timestamp,
	PRIMARY KEY (TeacherID)
);

create table CourseInfo(
	CourseID varchar(64) NOT NULL,
	TeacherID varchar(64) NOT NULL,
	term varchar(64) NOT NULL,
	name varchar(64) NOT NULL,
	grade int,
	time timestamp,
	PRIMARY KEY (CourseID,TeacherID,term),
	FOREIGN KEY (TeacherID) REFERENCES TeacherInfo(TeacherID)
);

create table GradeInfo(
	StudentID int NOT NULL,
	CourseID varchar(64) NOT NULL,
	term varchar(64) NOT NULL,
	state varchar2(20) check(state in('Preselection','Selected')),
	grade int,
	time timestamp,
	PRIMARY KEY (StudentID,CourseID,term)
);