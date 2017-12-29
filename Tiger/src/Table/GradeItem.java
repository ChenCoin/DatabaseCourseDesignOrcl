/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Table;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author coin
 */
public class GradeItem {

    private final SimpleStringProperty studentID;
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty time;
    private final SimpleStringProperty course;
    private final SimpleStringProperty teacher;
    private final SimpleStringProperty grade;

    public GradeItem(String studentID, String studentName, String time, String course, String teacher, String grade) {
	this.studentID = new SimpleStringProperty(studentID);
	this.studentName = new SimpleStringProperty(studentName);
	this.time = new SimpleStringProperty(time);
	this.course = new SimpleStringProperty(course);
	this.teacher = new SimpleStringProperty(teacher);
	this.grade = new SimpleStringProperty(grade);
    }

    /**
     * @return the studentID
     */
    public String getStudentID() {
	return studentID.get();
    }

    /**
     * @param studentID the studentID to set
     */
    public void setStudentID(String studentID) {
	this.studentID.set(studentID);
    }

    /**
     * @return the studentName
     */
    public String getStudentName() {
	return studentName.get();
    }

    /**
     * @param studentName the studentName to set
     */
    public void setStudentName(String studentName) {
	this.studentName.set(studentName);
    }

    /**
     * @return the time
     */
    public String getTime() {
	return time.get();
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
	this.time.set(time);
    }

    /**
     * @return the course
     */
    public String getCourse() {
	return course.get();
    }

    /**
     * @param course the course to set
     */
    public void setCourse(String course) {
	this.course.set(course);
    }

    /**
     * @return the teacher
     */
    public String getTeacher() {
	return teacher.get();
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(String teacher) {
	this.teacher.set(teacher);
    }

    /**
     * @return the grade
     */
    public String getGrade() {
	return grade.get();
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
	this.grade.set(grade);
    }

}
