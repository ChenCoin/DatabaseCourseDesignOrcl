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
public class CourseItem {
    private final SimpleStringProperty courseID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty teacherID;
    private final SimpleStringProperty term;
    private final SimpleStringProperty grade;

    public CourseItem(String a, String b, String c, String d, String e) {
	this.courseID = new SimpleStringProperty(a);
	this.name = new SimpleStringProperty(b);
	this.teacherID = new SimpleStringProperty(c);
	this.term = new SimpleStringProperty(d);
	this.grade = new SimpleStringProperty(e);
    }

    /**
     * @return the studentID
     */
    public String getCourseID() {
	return courseID.get();
    }

    /**
     * @param studentID the studentID to set
     */
    public void setCourseID(String studentID) {
	this.courseID.set(studentID);
    }

    /**
     * @return the studentName
     */
    public String getName() {
	return name.get();
    }

    /**
     * @param studentName the studentName to set
     */
    public void setName(String studentName) {
	this.name.set(studentName);
    }

    /**
     * @return the age
     */
    public String getTeacherID() {
	return teacherID.get();
    }

    /**
     * @param age the age to set
     */
    public void setTeacherID(String age) {
	this.teacherID.set(age);
    }

    /**
     * @return the sex
     */
    public String getTerm() {
	return term.get();
    }

    /**
     * @param sex the sex to set
     */
    public void setTerm(String sex) {
	this.term.set(sex);
    }

    /**
     * @return the class2
     */
    public String getGrade() {
	return grade.get();
    }

    /**
     * @param class2 the class2 to set
     */
    public void setGrade(String class2) {
	this.grade.set(class2);
    }
}
